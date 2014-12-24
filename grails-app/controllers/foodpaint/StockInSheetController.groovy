package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class StockInSheetController {

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

    def index = {

        def list = StockInSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {
        
        def i18nType = grailsApplication.config.grails.i18nType

        def stockInSheet=StockInSheet.get(params.id)

        if(stockInSheet){   

            render (contentType: 'application/json') {
                [success: true,data:stockInSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
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

        def i18nType = grailsApplication.config.grails.i18nType

        def stockInSheet= StockInSheet.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != stockInSheet.typeName || params.name != stockInSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        ////單身建立後不允許變更工作站
        if(stockInSheet.stockInSheetDets && params.workstation.id.toLong() != stockInSheet.workstation.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.stockInSheet.stockInSheetDets.exists.workstation.not.allowed.change", args: [stockInSheet])]
            }
            return
        }
        stockInSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(stockInSheet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def stockInSheet = StockInSheet.get(params.id)

        def result
        try {
            result = domainService.delete(stockInSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [stockInSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def reportTitle = message(code: "${i18nType}.stockInSheet.report.title.label")
        
        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();
        sortField.setName('sequence');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        //設定額外傳入參數
        def parameters=[:]
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
            data.batch=stockInSheetDet.batch
            data.warehouse=stockInSheetDet.warehouse
            data.warehouseLocation=stockInSheetDet.warehouseLocation
            data.manufactureOrder=stockInSheetDet.manufactureOrder
            data.qty=stockInSheetDet.qty
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'StockInSheet.jasper', reportTitle, parameters, reportData)
        
        render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')
    }
}
