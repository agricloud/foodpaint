package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class PurchaseReturnSheetController{

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

    def index = {

        def list = PurchaseReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def purchaseReturnSheet=PurchaseReturnSheet.get(params.id)

        if(purchaseReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
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

        def i18nType = grailsApplication.config.grails.i18nType

        def purchaseReturnSheet= PurchaseReturnSheet.get(params.id)
        //單別、單號一旦建立不允許變更
        if(params.typeName != purchaseReturnSheet.typeName || params.name != purchaseReturnSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }
        //單身建立後不允許變更供應商
        if(purchaseReturnSheet.purchaseReturnSheetDets && params.supplier.id.toLong() != purchaseReturnSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.purchaseReturnSheet.purchaseReturnSheetDets.exists.supplier.not.allowed.change", args: [purchaseReturnSheet])]
            }
            return
        }
        purchaseReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(purchaseReturnSheet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def purchaseReturnSheet = PurchaseReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(purchaseReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [purchaseReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def reportTitle = message(code: "${i18nType}.purchaseReturnSheet.report.title.label")
        
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
 
        def reportFile = jasperReportService.printPdf(params, 'PurchaseReturnSheet.jasper', reportTitle, parameters, reportData)
        
        render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')
    }
}