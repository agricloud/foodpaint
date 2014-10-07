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

class SaleSheetController {

    def grailsApplication
    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = SaleSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def indexByCustomer = {

        def customer = Customer.get(params.customer.id)
        def list = SaleSheet.findAllByCustomer(customer)
        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }
    }
    
    def show = {
        
        def i18nType = grailsApplication.config.grails.i18nType

        def saleSheet=SaleSheet.get(params.id)

        if(saleSheet){   

            render (contentType: 'application/json') {
                [success: true,data:saleSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }

    def create = {

        def saleSheet=new SaleSheet() 

        render (contentType: 'application/json') {
            [success: true,data:saleSheet]
        }
    }

    def save = {
        def saleSheet=new SaleSheet(params)

        render (contentType: 'application/json') {
            domainService.save(saleSheet)
        }
    }


    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleSheet= SaleSheet.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != saleSheet.typeName || params.name != saleSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        //單身建立後不允許變更客戶
        if(saleSheet.saleSheetDets && params.customer.id.toLong() != saleSheet.customer.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.saleSheet.saleSheetDets.exists.customer.not.allowed.change", args: [saleSheet])]
            }
            return
        }
        
        saleSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(saleSheet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def saleSheet = SaleSheet.get(params.id)

        def result
        try {
            result = domainService.delete(saleSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [saleSheet, e])
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

        def reportTitle = message(code: "${i18nType}.saleSheet.report.title.label")
        
        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('sequence');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        //設定額外傳入參數
        def parameters=[:]
        parameters["site.title"]=site?.title
        parameters["report.title"]=reportTitle
        parameters["REPORT_TIME_ZONE"]=dateService.getTimeZone()
        parameters["SORT_FIELDS"]=sortList
        //設定準備傳入的資料
        def reportData=[]
        def saleSheet = SaleSheet.get(params.id)
        saleSheet.saleSheetDets.each{ saleSheetDet ->
            def data=[:]
            data.dateCreated=saleSheet.dateCreated
            data.lastUpdated=saleSheet.lastUpdated
            data.typeName=saleSheet.typeName
            data.name=saleSheet.name
            data.customer=saleSheet.customer
            data.shippingAddress=saleSheet.shippingAddress
            data.sequence=saleSheetDet.sequence
            data.item=saleSheetDet.item
            data.warehouse=saleSheetDet.warehouse
            data.warehouseLocation=saleSheetDet.warehouseLocation
            data.batch=saleSheetDet.batch
            data.qty=saleSheetDet.qty
            data.customerOrderDet=saleSheetDet.customerOrderDet
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'SaleSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
