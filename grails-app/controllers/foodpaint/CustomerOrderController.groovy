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

class CustomerOrderController {

    def domainService
    def jasperService
    def springSecurityService
    def dateService

    def index = {

        def list = CustomerOrder.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [customerOrderInstanceList: list, customerOrderInstanceTotal: list.totalCount]
        }


        
    }
    def show = {
        def customerOrder=CustomerOrder.get(params.id)

        if(customerOrder){   

            render (contentType: 'application/json') {
                [success: true,data:customerOrder]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
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

        def customerOrder= CustomerOrder.get(params.id)
        if(customerOrder.customerOrderDets && params.customer.id != customerOrder.customer.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'customerOrder.customerOrderDets.exists.customer.not.allowed.change', args: [customerOrder])]
            }
            return
        }
        customerOrder.properties = params
        render (contentType: 'application/json') {
            domainService.save(customerOrder)
        }         
    }



    def delete = {
        
        def customerOrder = CustomerOrder.get(params.id)

        def result
        try {
            result = domainService.delete(customerOrder)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [customerOrder, e])
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

        def reportTitle = message(code: 'customerOrder.report.title.label')
        
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
        def customerOrder = CustomerOrder.get(params.id)
        customerOrder.customerOrderDets.each{ customerOrderDet ->
            def data=[:]
            data.dateCreated=customerOrder.dateCreated
            data.lastUpdated=customerOrder.lastUpdated
            data.typeName=customerOrder.typeName
            data.name=customerOrder.name
            data.customer=customerOrder.customer
            data.sequence=customerOrderDet.sequence
            data.item=customerOrderDet.item
            data.qty=customerOrderDet.qty
            reportData << data
        }

        def reportDef = new JasperReportDef(name:'CustomerOrder.jasper',parameters:parameters,reportData:reportData,fileFormat:JasperExportFormat.PDF_FORMAT)

        def fileName=dateService.getStrDate('yyyy-MM-dd HHmmss')+" "+reportTitle+".pdf"
        
        FileUtils.writeByteArrayToFile(new File("web-app/reportFiles/"+fileName), jasperService.generateReport(reportDef).toByteArray())

        render (contentType: 'application/json') {
            [fileName:fileName]
        }   
    }
}
