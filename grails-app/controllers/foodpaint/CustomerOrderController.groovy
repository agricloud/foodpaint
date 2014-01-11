package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class CustomerOrderController {

    def domainService

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
        println 'i am here'
        println "params == ${params}"

        def  customerOrder= CustomerOrder.get(params.id)
        customerOrder.properties = params
        render (contentType: 'application/json') {
            domainService.save(customerOrder)
        }         
    }



    def delete = {
        
        def  customerOrder = CustomerOrder.get(params.id)

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
}
