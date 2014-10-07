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

class SaleReturnSheetController {

    def grailsApplication
    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = SaleReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleReturnSheet=SaleReturnSheet.get(params.id)

        if(saleReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:saleReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }

    def create = {

        def saleReturnSheet=new SaleReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:saleReturnSheet]
        }
    }

    def save = {
        def saleReturnSheet=new SaleReturnSheet(params)

        render (contentType: 'application/json') {
            domainService.save(saleReturnSheet)
        }
    }


    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleReturnSheet= SaleReturnSheet.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != saleReturnSheet.typeName || params.name != saleReturnSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }
        
        if(saleReturnSheet.saleReturnSheetDets && params.customer.id.toLong() != saleReturnSheet.customer.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.saleReturnSheet.saleReturnSheetDets.exists.customer.not.allowed.change", args: [saleReturnSheet])]
            }
            return
        }

        saleReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(saleReturnSheet)
        }         
    }

    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def saleReturnSheet = SaleReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(saleReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [saleReturnSheet, e])
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

        def reportTitle = message(code: "${i18nType}.saleReturnSheet.report.title.label")
        
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
        def saleReturnSheet = SaleReturnSheet.get(params.id)
        saleReturnSheet.saleReturnSheetDets.each{ saleReturnSheetDet ->
            def data=[:]
            data.dateCreated=saleReturnSheet.dateCreated
            data.lastUpdated=saleReturnSheet.lastUpdated
            data.typeName=saleReturnSheet.typeName
            data.name=saleReturnSheet.name
            data.customer=saleReturnSheet.customer
            data.pickUpAddress=saleReturnSheet.pickUpAddress
            data.sequence=saleReturnSheetDet.sequence
            data.item=saleReturnSheetDet.item
            data.warehouse=saleReturnSheetDet.warehouse
            data.warehouseLocation=saleReturnSheetDet.warehouseLocation
            data.batch=saleReturnSheetDet.batch
            data.qty=saleReturnSheetDet.qty
            data.saleSheetDet=saleReturnSheetDet.saleSheetDet
            data.customerOrderDet=saleReturnSheetDet.customerOrderDet
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'SaleReturnSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
