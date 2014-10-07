package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class CustomerOrderDetController {

    def grailsApplication
    def domainService

    def index = {

        def i18nType = grailsApplication.config.grails.i18nType

        def customerOrder = CustomerOrder.get(params.customerOrder.id)

        if(customerOrder){
            
            def customerOrderDet = CustomerOrderDet.findAllByCustomerOrder(customerOrder)

            render (contentType: 'application/json') {
               [success: true, data:customerOrderDet, total: customerOrderDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }
        }
        
    }


    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def customerOrderDet=CustomerOrderDet.get(params.id);


        if(customerOrderDet){   

            render (contentType: 'application/json') {
                [success: true,data:customerOrderDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }


    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

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
                [success: false,message:message(code: "${i18nType}.customerOrderDet.message.create.failed")]
            }            
        }

    }

    def save = {

        def i18nType = grailsApplication.config.grails.i18nType

        def customerOrderDet=new CustomerOrderDet(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(customerOrderDet.typeName != customerOrderDet.customerOrder.typeName || customerOrderDet.name != customerOrderDet.customerOrder.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sheet.typeName.name.not.equal")]
            }
            return
        }

        if(customerOrderDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [customerOrderDet])]
            }
            return
        }
        render (contentType: 'application/json') {
            domainService.save(customerOrderDet)
        }
    }


    def update = {

        def i18nType = grailsApplication.config.grails.i18nType

        def customerOrderDet = CustomerOrderDet.get(params.id)

        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != customerOrderDet.typeName || params.name != customerOrderDet.name|| params.sequence.toLong() != customerOrderDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sequence.not.allowed.change")]
            }
            return
        }

        if(params.qty.toDouble()<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [customerOrderDet])]
            }
            return
        }
        customerOrderDet.properties = params
        render (contentType: 'application/json') {
            domainService.save(customerOrderDet)
        }         
    }



    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType

        def customerOrderDet = CustomerOrderDet.get(params.id)

        def result
        try {
            
            result = domainService.delete(customerOrderDet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [customerOrderDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
