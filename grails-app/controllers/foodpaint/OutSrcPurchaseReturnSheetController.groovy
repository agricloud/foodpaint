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

class OutSrcPurchaseReturnSheetController{

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = OutSrcPurchaseReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [outSrcPurchaseReturnSheetInstanceList: list, outSrcPurchaseReturnSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def outSrcPurchaseReturnSheet=OutSrcPurchaseReturnSheet.get(params.id)

        if(outSrcPurchaseReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def outSrcPurchaseReturnSheet=new OutSrcPurchaseReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:outSrcPurchaseReturnSheet]
        }
    }

    def save = {
        def outSrcPurchaseReturnSheet=new OutSrcPurchaseReturnSheet(params)

        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseReturnSheet)
        }
    }


    def update = {

        def outSrcPurchaseReturnSheet= OutSrcPurchaseReturnSheet.get(params.id)
        if(outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets && params.supplier.id != outSrcPurchaseReturnSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets.exists.supplier.not.allowed.change', args: [outSrcPurchaseReturnSheet])]
            }
            return
        }
        outSrcPurchaseReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseReturnSheet)
        }         
    }



    def delete = {
        
        def outSrcPurchaseReturnSheet = OutSrcPurchaseReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(outSrcPurchaseReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [outSrcPurchaseReturnSheet, e])
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

        def reportTitle = message(code: 'outSrcPurchaseReturnSheet.report.title.label')
        
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
        def outSrcPurchaseReturnSheet = OutSrcPurchaseReturnSheet.get(params.id)
        outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets.each{ outSrcPurchaseReturnSheetDet ->
            def data=[:]
            data.dateCreated=outSrcPurchaseReturnSheet.dateCreated
            data.lastUpdated=outSrcPurchaseReturnSheet.lastUpdated
            data.typeName=outSrcPurchaseReturnSheet.typeName
            data.name=outSrcPurchaseReturnSheet.name
            data.supplier=outSrcPurchaseReturnSheet.supplier
            data.sequence=outSrcPurchaseReturnSheetDet.sequence
            data.item=outSrcPurchaseReturnSheetDet.item
            data.warehouse=outSrcPurchaseReturnSheetDet.warehouse
            data.warehouseLocation=outSrcPurchaseReturnSheetDet.warehouseLocation
            data.batch=outSrcPurchaseReturnSheetDet.batch
            data.qty=outSrcPurchaseReturnSheetDet.qty
            data.outSrcPurchaseSheetDet=outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet
            data.manufactureOrder=outSrcPurchaseReturnSheetDet.manufactureOrder
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'OutSrcPurchaseReturnSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
