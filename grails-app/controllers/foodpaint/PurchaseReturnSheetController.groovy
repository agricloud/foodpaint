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

class PurchaseReturnSheetController{

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = PurchaseReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [purchaseReturnSheetInstanceList: list, purchaseReturnSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def purchaseReturnSheet=PurchaseReturnSheet.get(params.id)

        if(purchaseReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def purchaseReturnSheet=new PurchaseReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:purchaseReturnSheet]
        }
    }

    def save = {
        def purchaseReturnSheet=new PurchaseReturnSheet(params)

        render (contentType: 'application/json') {
            domainService.save(purchaseReturnSheet)
        }
    }


    def update = {

        def purchaseReturnSheet= PurchaseReturnSheet.get(params.id)
        if(purchaseReturnSheet.purchaseReturnSheetDets && params.supplier.id != purchaseReturnSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheet.purchaseReturnSheetDets.exists.supplier.not.allowed.change', args: [purchaseReturnSheet])]
            }
            return
        }
        purchaseReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(purchaseReturnSheet)
        }         
    }



    def delete = {
        
        def purchaseReturnSheet = PurchaseReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(purchaseReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [purchaseReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

        def print(){
        def site
        if(params.site.id && params.site.id!="null")
            site = Site.get(params.site.id)

        def reportTitle = message(code: 'purchaseReturnSheet.report.title.label')
        
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
        def purchaseReturnSheet = PurchaseReturnSheet.get(params.id)
        purchaseReturnSheet.purchaseReturnSheetDets.each{ purchaseReturnSheetDet ->
            def data=[:]
            data.dateCreated=purchaseReturnSheet.dateCreated
            data.lastUpdated=purchaseReturnSheet.lastUpdated
            data.typeName=purchaseReturnSheet.typeName
            data.name=purchaseReturnSheet.name
            data.supplier=purchaseReturnSheet.supplier
            data.sequence=purchaseReturnSheetDet.sequence
            data.item=purchaseReturnSheetDet.item
            data.warehouse=purchaseReturnSheetDet.warehouse
            data.warehouseLocation=purchaseReturnSheetDet.warehouseLocation
            data.batch=purchaseReturnSheetDet.batch
            data.qty=purchaseReturnSheetDet.qty
            data.purchaseSheetDet=purchaseReturnSheetDet.purchaseSheetDet
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'PurchaseReturnSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}