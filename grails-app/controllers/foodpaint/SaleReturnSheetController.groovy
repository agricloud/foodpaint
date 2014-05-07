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

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = SaleReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [saleReturnSheetInstanceList: list, saleReturnSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def saleReturnSheet=SaleReturnSheet.get(params.id)

        if(saleReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:saleReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
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

        def saleReturnSheet= SaleReturnSheet.get(params.id)
        saleReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(saleReturnSheet)
        }         
    }

    def delete = {
        
        def saleReturnSheet = SaleReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(saleReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [saleReturnSheet, e])
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

        def reportTitle = message(code: 'saleReturnSheet.report.title.label')
        
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
