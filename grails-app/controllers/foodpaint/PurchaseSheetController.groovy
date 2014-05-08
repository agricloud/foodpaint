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

class PurchaseSheetController {

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = PurchaseSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {
        def purchaseSheet=PurchaseSheet.get(params.id)

        if(purchaseSheet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def purchaseSheet=new PurchaseSheet() 

        render (contentType: 'application/json') {
            [success: true,data:purchaseSheet]
        }
    }

    def save = {
        def purchaseSheet=new PurchaseSheet(params)

        render (contentType: 'application/json') {
            domainService.save(purchaseSheet)
        }
    }


    def update = {
        def purchaseSheet= PurchaseSheet.get(params.id)
        if(purchaseSheet.purchaseSheetDets && params.supplier.id != purchaseSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseSheet.purchaseSheetDets.exists.supplier.not.allowed.change', args: [purchaseSheet])]
            }
            return
        }
        purchaseSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(purchaseSheet)
        }         
    }



    def delete = {
        
        def purchaseSheet = PurchaseSheet.get(params.id)

        def result
        try {
            result = domainService.delete(purchaseSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [purchaseSheet, e])
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

        def reportTitle = message(code: 'purchaseSheet.report.title.label')
        
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
        def purchaseSheet = PurchaseSheet.get(params.id)
        purchaseSheet.purchaseSheetDets.each{ purchaseSheetDet ->
            def data=[:]
            data.dateCreated=purchaseSheet.dateCreated
            data.lastUpdated=purchaseSheet.lastUpdated
            data.typeName=purchaseSheet.typeName
            data.name=purchaseSheet.name
            data.supplier=purchaseSheet.supplier
            data.sequence=purchaseSheetDet.sequence
            data.item=purchaseSheetDet.item
            data.warehouse=purchaseSheetDet.warehouse
            data.warehouseLocation=purchaseSheetDet.warehouseLocation
            data.batch=purchaseSheetDet.batch
            data.qty=purchaseSheetDet.qty
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'PurchaseSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
