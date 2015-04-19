package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class CertificationController {

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

    def index = {

        def list = Certification.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }
    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def certification=Certification.get(params.id)

        if(certification){   

            render (contentType: 'application/json') {
                [success: true,data:certification]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }

    def create = {

        def certification=new Certification() 

        render (contentType: 'application/json') {
            [success: true,data:certification]
        }
    }

    def save = {
        def certification=new Certification(params)

        render (contentType: 'application/json') {
            domainService.save(certification)
        }
    }


    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def certification= Certification.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != certification.typeName || params.name != certification.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        certification.properties = params
        render (contentType: 'application/json') {
            domainService.save(certification)
        }         
    }






    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def certification= Certification.get(params.id)

        def result
        try {
            result = domainService.delete(certification)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [certification, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
//   
    // def print(){

    //     def i18nType = grailsApplication.config.grails.i18nType
    //     def reportTitle = message(code: "${i18nType}.saleReturnSheet.report.title.label")
        
    //     //報表依指定欄位排序
    //     List<JRSortField> sortList = new ArrayList<JRSortField>();
    //     JRDesignSortField sortField = new JRDesignSortField();
    //     sortField.setName('sequence');
    //     sortField.setOrder(SortOrderEnum.ASCENDING);
    //     sortField.setType(SortFieldTypeEnum.FIELD);
    //     sortList.add(sortField);
    //     //設定額外傳入參數
    //     def parameters=[:]
    //     parameters["SORT_FIELDS"]=sortList
    //     //設定準備傳入的資料
    //     def reportData=[]
    //     def saleReturnSheet = SaleReturnSheet.get(params.id)
    //     saleReturnSheet.saleReturnSheetDets.each{ saleReturnSheetDet ->
    //         def data=[:]
    //         data.dateCreated=saleReturnSheet.dateCreated
    //         data.lastUpdated=saleReturnSheet.lastUpdated
    //         data.typeName=saleReturnSheet.typeName
    //         data.name=saleReturnSheet.name
    //         data.customer=saleReturnSheet.customer
    //         data.pickUpAddress=saleReturnSheet.pickUpAddress
    //         data.sequence=saleReturnSheetDet.sequence
    //         data.item=saleReturnSheetDet.item
    //         data.warehouse=saleReturnSheetDet.warehouse
    //         data.warehouseLocation=saleReturnSheetDet.warehouseLocation
    //         data.batch=saleReturnSheetDet.batch
    //         data.qty=saleReturnSheetDet.qty
    //         data.saleSheetDet=saleReturnSheetDet.saleSheetDet
    //         data.customerOrderDet=saleReturnSheetDet.customerOrderDet
    //         reportData << data
    //     }

    //     def reportFile = jasperReportService.printPdf(params, 'SaleReturnSheet.jasper', reportTitle, parameters, reportData)
        
    //     render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf') 
    // }
}
