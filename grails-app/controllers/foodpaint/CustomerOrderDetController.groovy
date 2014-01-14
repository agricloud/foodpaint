package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class CustomerOrderDetController {

    def domainService

    def index = {

        def customerOrder = CustomerOrder.get(params.customerOrder.id)

        if(customerOrder){
            
            def customerOrderDet = CustomerOrderDet.findAllByCustomerOrder(customerOrder)

            render (contentType: 'application/json') {
               [success: true, data:customerOrderDet, total: customerOrderDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def customerOrderDet=CustomerOrderDet.get(params.id);


        if(customerOrderDet){   

            render (contentType: 'application/json') {
                [success: true,data:customerOrderDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.customerOrder.id){

            def customerOrderDet= new CustomerOrderDet(params)

            customerOrderDet.typeName = customerOrderDet.customerOrder.typeName
            customerOrderDet.name = customerOrderDet.customerOrder.name

            if(customerOrderDet.customerOrder.customerOrderDets)
                customerOrderDet.sequence = customerOrderDet.customerOrder.customerOrderDets*.sequence.max()+1
            else customerOrderDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:customerOrderDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'customerOrderDet.message.create.failed')]
            }            
        }

    }

    def save = {

        def customerOrderDet=new CustomerOrderDet(params)

        render (contentType: 'application/json') {
            domainService.save(customerOrderDet)
        }
    }


    def update = {

        def  customerOrderDet = CustomerOrderDet.get(params.id)
        customerOrderDet.properties = params
        render (contentType: 'application/json') {
            domainService.save(customerOrderDet)
        }         
    }



    def delete = {

        def  customerOrderDet = CustomerOrderDet.get(params.id)

        def result
        try {
            
            result = domainService.delete(customerOrderDet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [customerOrderDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
