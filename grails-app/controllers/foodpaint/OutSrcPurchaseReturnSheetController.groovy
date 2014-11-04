package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class OutSrcPurchaseReturnSheetController{

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

    def index = {

        def list = OutSrcPurchaseReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseReturnSheet=OutSrcPurchaseReturnSheet.get(params.id)

        if(outSrcPurchaseReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
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

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseReturnSheet= OutSrcPurchaseReturnSheet.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != outSrcPurchaseReturnSheet.typeName || params.name != outSrcPurchaseReturnSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        if(outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets && params.supplier.id.toLong() != outSrcPurchaseReturnSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets.exists.supplier.not.allowed.change", args: [outSrcPurchaseReturnSheet])]
            }
            return
        }
        outSrcPurchaseReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseReturnSheet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def outSrcPurchaseReturnSheet = OutSrcPurchaseReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(outSrcPurchaseReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [outSrcPurchaseReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def reportTitle = message(code: "${i18nType}.outSrcPurchaseReturnSheet.report.title.label")
        
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

        def reportFile = jasperReportService.printPdf(params, 'OutSrcPurchaseReturnSheet.jasper', reportTitle, parameters, reportData)
        
        render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')
    }
}
