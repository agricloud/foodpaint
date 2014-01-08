package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ManufactureOrderController {

    def domainService
    def batchService


    def index = {

        def list = ManufactureOrder.createCriteria().list(params,params.criteria)

        render (contentType: 'application/json') {
            [manufactureOrderInstanceList: list, manufactureOrderInstanceTotal: list.totalCount]
        }


        
    }
    def show = {

        def manufactureOrder=ManufactureOrder.get(params.id);


        if(manufactureOrder){   

            render (contentType: 'application/json') {
                [success: true,data:manufactureOrder]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }
    def create = {

        def manufactureOrder=new ManufactureOrder()        
        render (contentType: 'application/json') {
            [success: true,data:manufactureOrder]
        }
    }

    def save = {
        def manufactureOrder=new ManufactureOrder(params)
        def batch = batchService.findOrCreateBatchInstanceByJson(params, manufactureOrder)
 
        manufactureOrder.batch = (Batch) batch

        render (contentType: 'application/json') {
            domainService.save(manufactureOrder)
        }
    }


    def update = {

        log.info params.effectStartDate
        def  manufactureOrder = ManufactureOrder.get(params.id)
        manufactureOrder.properties = params
        render (contentType: 'application/json') {
            domainService.save(manufactureOrder)
        }         
    }


    def delete = {
        
        def  manufactureOrder = ManufactureOrder.get(params.id)

        def result
        try {
            
            result = domainService.delete(manufactureOrder)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [manufactureOrder, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
