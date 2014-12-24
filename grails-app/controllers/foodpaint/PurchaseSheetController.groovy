package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class PurchaseSheetController {

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

    def index = {

        def list = PurchaseSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def indexBySupplier = {

        def supplier = Supplier.get(params.supplier.id)
        def list = PurchaseSheet.findAllBySupplier(supplier)
        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }
        
    }

    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def purchaseSheet=PurchaseSheet.get(params.id)

        if(purchaseSheet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
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

        def i18nType = grailsApplication.config.grails.i18nType

        def purchaseSheet= PurchaseSheet.get(params.id)
        
        //單別、單號一旦建立不允許變更
        if(params.typeName != purchaseSheet.typeName || params.name != purchaseSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }
        //單身建立後不允許變更供應商
        if(purchaseSheet.purchaseSheetDets && params.supplier.id.toLong() != purchaseSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.purchaseSheet.purchaseSheetDets.exists.supplier.not.allowed.change", args: [purchaseSheet])]
            }
            return
        }
        purchaseSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(purchaseSheet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def purchaseSheet = PurchaseSheet.get(params.id)

        def result
        try {
            result = domainService.delete(purchaseSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [purchaseSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def reportTitle = message(code: "${i18nType}.purchaseSheet.report.title.label")
        
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

        def reportFile = jasperReportService.printPdf(params, 'PurchaseSheet.jasper', reportTitle, parameters, reportData)
        
        render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')  
    }
}
