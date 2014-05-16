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

class OutSrcPurchaseSheetController {

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = OutSrcPurchaseSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def indexBySupplier = {

        def supplier = Supplier.get(params.supplier.id)
        def list = OutSrcPurchaseSheet.findAllBySupplier(supplier)
        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }
        
    }

    def show = {
        def outSrcPurchaseSheet=OutSrcPurchaseSheet.get(params.id)

        if(outSrcPurchaseSheet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def outSrcPurchaseSheet=new OutSrcPurchaseSheet() 

        render (contentType: 'application/json') {
            [success: true,data:outSrcPurchaseSheet]
        }
    }

    def save = {
        def outSrcPurchaseSheet=new OutSrcPurchaseSheet(params)

        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseSheet)
        }
    }


    def update = {

        def outSrcPurchaseSheet= OutSrcPurchaseSheet.get(params.id)
        if(outSrcPurchaseSheet.outSrcPurchaseSheetDets && params.supplier.id.toLong() != outSrcPurchaseSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseSheet.outSrcPurchaseSheetDets.exists.supplier.not.allowed.change', args: [outSrcPurchaseSheet])]
            }
            return
        }
        outSrcPurchaseSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseSheet)
        }         
    }



    def delete = {
        
        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.id)

        def result
        try {
            result = domainService.delete(outSrcPurchaseSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [outSrcPurchaseSheet, e])
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

        def reportTitle = message(code: 'outSrcPurchaseSheet.report.title.label')
        
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
        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.id)
        outSrcPurchaseSheet.outSrcPurchaseSheetDets.each{ outSrcPurchaseSheetDet ->
            def data=[:]
            data.dateCreated=outSrcPurchaseSheet.dateCreated
            data.lastUpdated=outSrcPurchaseSheet.lastUpdated
            data.typeName=outSrcPurchaseSheet.typeName
            data.name=outSrcPurchaseSheet.name
            data.supplier=outSrcPurchaseSheet.supplier
            data.sequence=outSrcPurchaseSheetDet.sequence
            data.item=outSrcPurchaseSheetDet.item
            data.warehouse=outSrcPurchaseSheetDet.warehouse
            data.warehouseLocation=outSrcPurchaseSheetDet.warehouseLocation
            data.batch=outSrcPurchaseSheetDet.batch
            data.qty=outSrcPurchaseSheetDet.qty
            data.manufactureOrder=outSrcPurchaseSheetDet.manufactureOrder
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'OutSrcPurchaseSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
