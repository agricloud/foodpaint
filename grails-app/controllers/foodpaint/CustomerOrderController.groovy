package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class CustomerOrderController {

    def domainService
    def batchService


    def index(Integer max) {



        def list = CustomerOrder.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [customerOrderInstanceList: list, customerOrderInstanceTotal: list.totalCount]
        }


        
    }
    def show(Long id){

        def customerOrder=CustomerOrder.findById(id);


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
    def create(){

        def mcustomerOrder=new CustomerOrder()        
        render (contentType: 'application/json') {
            [success: true,data:customerOrder]
        }
    }

    def save(){
        def customerOrderInstance=new CustomerOrder(params)

        render (contentType: 'application/json') {
            domainService.save(customerOrderInstance)
        }
    }


    def update(){
        def  customerOrderInstance = CustomerOrder.findById(params.id)
        customerOrderInstance.properties = params
        render (contentType: 'application/json') {
            domainService.save(customerOrderInstance)
        }         
    }



    def delete(){
        
        def  customerOrderInstance = CustomerOrder.findById(params.id)

        def result
        try {
            
            result = domainService.delete(customerOrderInstance)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [customerOrderInstance, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
