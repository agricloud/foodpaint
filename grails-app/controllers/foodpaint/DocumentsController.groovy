package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class DocumentsController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
    def domainService

    def enumService

    def index = {

        def list = Documents.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def show = {

        log.debug "${controllerName}-${actionName}"

        def i18nType = grailsApplication.config.grails.i18nType

        def documents=Documents.get(params.id);

        if(documents){   

            render (contentType: 'application/json') {
                [success: true, data:documents]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false, message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }
 
    def create = {

        def documents=new Documents()
        render (contentType: 'application/json') {
            [success: true,data:documents]
        }
    }

    def save = {
        log.debug "${controllerName}-${actionName}"
        def documents=new Documents(params)
        
        render (contentType: 'application/json') {
            domainService.save(documents)
        }
    }

    def update = {

        def documents = Documents.get(params.id)
        documents.properties=params
        render (contentType: 'application/json') {
            domainService.save(documents)
        }
    }

    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def documents = Documents.get(params.id)
        def result
        try {
            
            result = domainService.delete(documents)
        
        }catch(DataIntegrityViolationException e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [documents, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
    
     def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def documentsTitle = message(code: "${i18nType}.documents.documents.title.label")
        
        //報表依指定欄位排序
        List<JRSortField> sortList = new ArrayList<JRSortField>();
        JRDesignSortField sortField = new JRDesignSortField();

        sortField.setName('dateCreated');
        sortField.setOrder(SortOrderEnum.ASCENDING);
        sortField.setType(SortFieldTypeEnum.FIELD);
        sortList.add(sortField);
        //設定額外傳入參數
        def parameters=[:]
        parameters["SORT_FIELDS"]=sortList

        //設定準備傳入的資料
        def reportData=[]
        def purchaseSheet = PurchaseSheet.get(params.id)

              //迴圈設計  日期 排序資料
      

        purchaseSheet.purchaseSheetDets.each{ purchaseSheetDet ->
            def data=[:]
            data.dateCreated=purchaseSheet.dateCreated
            data.lastUpdated=purchaseSheet.lastUpdated
            data.typeName=purchaseSheet.typeName
            data.name=purchaseSheet.name
            data.supplier=purchaseSheet.supplier
            data.sequence=purchaseSheetDet.sequence
            data.item=purchaseSheetDet.item
            data.batch=purchaseSheetDet.batch
            data.qty=purchaseSheetDet.qty
            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'FertilizerPurchaseRecordSheet.jasper', documentTitle, parameters, reportData)
        
        render (file:documentFile, fileNmae:'${documentTitle}.pdf',contentType:'application/pdf')  
    }







}
