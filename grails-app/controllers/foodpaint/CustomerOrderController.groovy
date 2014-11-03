package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

//ireport sorting list
import net.sf.jasperreports.engine.JRSortField
import net.sf.jasperreports.engine.design.JRDesignSortField
import net.sf.jasperreports.engine.type.SortOrderEnum
import net.sf.jasperreports.engine.type.SortFieldTypeEnum

class CustomerOrderController {

    def grailsApplication
    def domainService
    def dateService
    def jasperReportService

    def index = {

        def list = CustomerOrder.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
    }

    def indexByCustomer = {

        def customer = Customer.get(params.customer.id)
        def list = CustomerOrder.findAllByCustomer(customer)
        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }
    }

    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def customerOrder=CustomerOrder.get(params.id)

        if(customerOrder){   

            render (contentType: 'application/json') {
                [success: true,data:customerOrder]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }

    def create = {

        def customerOrder=new CustomerOrder() 

        render (contentType: 'application/json') {
            [success: true,data:customerOrder]
        }
    }

    def save = {
        def customerOrder=new CustomerOrder(params)

        render (contentType: 'application/json') {
            domainService.save(customerOrder)
        }
    }


    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def customerOrder= CustomerOrder.get(params.id)

        //單別、單號一旦建立不允許變更
        if(params.typeName != customerOrder.typeName || params.name != customerOrder.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.typeName.name.not.allowed.change")]
            }
            return
        }

        //單身建立後不允許變更客戶
        if(customerOrder.customerOrderDets && params.customer.id.toLong() != customerOrder.customer.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.customerOrder.customerOrderDets.exists.customer.not.allowed.change", args: [customerOrder])]
            }
            return
        }
        
        customerOrder.properties = params
        render (contentType: 'application/json') {
            domainService.save(customerOrder)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def customerOrder = CustomerOrder.get(params.id)

        def result
        try {
            result = domainService.delete(customerOrder)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [customerOrder, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

    def print(){

        def i18nType = grailsApplication.config.grails.i18nType
        def reportTitle = message(code: "${i18nType}.customerOrder.report.title.label")
        
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
        def customerOrder = CustomerOrder.get(params.id)
        customerOrder.customerOrderDets.each{ customerOrderDet ->
            def data=[:]
            data.dateCreated=customerOrder.dateCreated
            data.lastUpdated=customerOrder.lastUpdated
            data.typeName=customerOrder.typeName
            data.name=customerOrder.name
            data.customer=customerOrder.customer
            data.shippingAddress=customerOrder.shippingAddress
            data.dueDate=customerOrder.dueDate
            data.sequence=customerOrderDet.sequence
            data.item=customerOrderDet.item
            data.qty=customerOrderDet.qty

            reportData << data
        }

        def reportFile = jasperReportService.printPdf(params, 'CustomerOrder.jasper', reportTitle, parameters, reportData)
        
        render (file:reportFile, fileNmae:'${reportTitle}.pdf',contentType:'application/pdf')
    }
}
