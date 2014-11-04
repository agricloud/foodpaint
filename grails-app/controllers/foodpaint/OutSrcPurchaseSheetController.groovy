package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class OutSrcPurchaseSheetController {

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

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

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseSheet=OutSrcPurchaseSheet.get(params.id)

        if(outSrcPurchaseSheet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
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

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseSheet= OutSrcPurchaseSheet.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != outSrcPurchaseSheet.typeName || params.name != outSrcPurchaseSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        if(outSrcPurchaseSheet.outSrcPurchaseSheetDets && params.supplier.id.toLong() != outSrcPurchaseSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.outSrcPurchaseSheet.outSrcPurchaseSheetDets.exists.supplier.not.allowed.change", args: [outSrcPurchaseSheet])]
            }
            return
        }
        outSrcPurchaseSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseSheet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.id)

        def result
        try {
            result = domainService.delete(outSrcPurchaseSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [outSrcPurchaseSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def reportTitle = message(code: "${i18nType}.outSrcPurchaseSheet.report.title.label")
        
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
 
        def reportFile = jasperReportService.printPdf(params, 'OutSrcPurchaseSheet.jasper', reportTitle, parameters, reportData)
        
        render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')
    }
}
