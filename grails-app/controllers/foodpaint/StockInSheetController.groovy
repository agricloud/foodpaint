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

class StockInSheetController {

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = StockInSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {
        def stockInSheet=StockInSheet.get(params.id)

        if(stockInSheet){   

            render (contentType: 'application/json') {
                [success: true,data:stockInSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def stockInSheet=new StockInSheet() 

        render (contentType: 'application/json') {
            [success: true,data:stockInSheet]
        }
    }

    def save = {
        def stockInSheet=new StockInSheet(params)

        render (contentType: 'application/json') {
            domainService.save(stockInSheet)
        }
    }


    def update = {

        def stockInSheet= StockInSheet.get(params.id)
        if(stockInSheet.stockInSheetDets && params.workstation.id != stockInSheet.workstation.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'stockInSheet.stockInSheetDets.exists.workstation.not.allowed.change', args: [stockInSheet])]
            }
            return
        }
        stockInSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(stockInSheet)
        }         
    }



    def delete = {
        
        def stockInSheet = StockInSheet.get(params.id)

        def result
        try {
            result = domainService.delete(stockInSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [stockInSheet, e])
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

        def reportTitle = message(code: 'stockInSheet.report.title.label')
        
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
        def stockInSheet = StockInSheet.get(params.id)
        stockInSheet.stockInSheetDets.each{ stockInSheetDet ->
            def data=[:]
            data.dateCreated=stockInSheet.dateCreated
            data.lastUpdated=stockInSheet.lastUpdated
            data.typeName=stockInSheet.typeName
            data.name=stockInSheet.name
            data.workstation=stockInSheet.workstation
            data.sequence=stockInSheetDet.sequence
            data.item=stockInSheetDet.item
            data.warehouse=stockInSheetDet.warehouse
            data.warehouseLocation=stockInSheetDet.warehouseLocation
            data.manufactureOrder=stockInSheetDet.manufactureOrder
            data.qty=stockInSheetDet.qty
            reportData << data
        }
        // def data=StockInSheet.get(1).properties

        def reportDef = new JasperReportDef(name:'StockInSheet.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
