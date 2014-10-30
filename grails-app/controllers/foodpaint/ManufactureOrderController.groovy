package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
//generate irepoet
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.apache.commons.io.FileUtils
//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class ManufactureOrderController {

    def grailsApplication
    def domainService
    def batchService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = ManufactureOrder.createCriteria().list(params,params.criteria)

        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByWorkstationOrSupplier = {
        def site = Site.get(params.site.id)
        def workstation = Workstation.get(params.workstation.id)
        def supplier = Supplier.get(params.supplier.id)
        def list = ManufactureOrder.findAllByWorkstationAndSupplierAndSite(workstation,supplier,site)

        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }
    }

    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def manufactureOrder=ManufactureOrder.get(params.id);


        if(manufactureOrder){   

            render (contentType: 'application/json') {
                [success: true,data:manufactureOrder]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }
    def create = {

        def manufactureOrder=new ManufactureOrder(params)        
        render (contentType: 'application/json') {
            [success: true,data:manufactureOrder]
        }
    }

    def save = {

        def i18nType = grailsApplication.config.grails.i18nType

        def manufactureOrder=new ManufactureOrder(params)

        if((manufactureOrder.workstation && manufactureOrder.supplier)||(!manufactureOrder.workstation && !manufactureOrder.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.manufactureOrder.workstation.supplier.should.exist.one", args: [manufactureOrder])]
            }
            return
        }
        if(manufactureOrder.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [manufactureOrder])]
            }
            return
        }

        def result = batchService.createBatchInstanceByJson(params, manufactureOrder)
        if(!result.success){
            render (contentType: 'application/json') {
                result
            }
        }
        else{
            manufactureOrder.batch = (Batch) result.batch

            render (contentType: 'application/json') {
                domainService.save(manufactureOrder)
            }
        }
    }


    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def manufactureOrder = new ManufactureOrder(params)

        if((manufactureOrder.workstation && manufactureOrder.supplier)||(!manufactureOrder.workstation && !manufactureOrder.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.manufactureOrder.workstation.supplier.should.exist.one", args: [manufactureOrder])]
            }
            return
        }
        if(manufactureOrder.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [manufactureOrder])]
            }
            return
        }

        manufactureOrder = ManufactureOrder.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != manufactureOrder.typeName || params.name != manufactureOrder.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        def result = batchService.findOrCreateBatchInstanceByJson(params, manufactureOrder) 
        
        if(!result.success){
            render (contentType: 'application/json') {
                result
            }
        }
        else{
            manufactureOrder.properties = params

            manufactureOrder.batch = (Batch) result.batch
            render (contentType: 'application/json') {
                domainService.save(manufactureOrder)
            }
        }
    }


    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def manufactureOrder = ManufactureOrder.get(params.id)

        def result
        try {
            
            result = domainService.delete(manufactureOrder)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [manufactureOrder, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        
        def site
        if(params.site.id && params.site.id!="null")
            site = Site.get(params.site.id)

        def reportTitle = message(code: "${i18nType}.manufactureOrder.report.title.label")
        
        //設定額外傳入參數
        def parameters=[:]
        parameters["site.title"]=site?.title
        parameters["report.title"]=reportTitle
        parameters["REPORT_TIME_ZONE"]=dateService.getTimeZone()
        parameters["IMAGE_DIR"]=servletContext.getResource("/images/")
        //設定準備傳入的資料
        def reportData=[]
        def manufactureOrder = ManufactureOrder.get(params.id)
        def data=manufactureOrder.properties

        reportData << data

        def reportDef = new JasperReportDef(name:'ManufactureOrder.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
