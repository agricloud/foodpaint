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

        def manufactureOrder=new ManufactureOrder(params)        
        render (contentType: 'application/json') {
            [success: true,data:manufactureOrder]
        }
    }

    def save = {
        def manufactureOrder=new ManufactureOrder(params)
        
        def result = batchService.createBatchInstanceByJson(params, manufactureOrder)
        if(!result.success){
            render (contentType: 'application/json') {
                result
            }
        }
        else{
            manufactureOrder.batch = (Batch) result.batch

            render (contentType: 'application/json') {
                domainService.save(manufactureOrder)
            }
        }
    }


    def update = {

        def  manufactureOrder = ManufactureOrder.get(params.id)

        //批號不變直接允許儲存，批號變更則需先新增批號。
        if(manufactureOrder.batch?.name == params.batch?.name){
            manufactureOrder.properties = params
            render (contentType: 'application/json') {
                domainService.save(manufactureOrder)
            }
        }
        else{
            def result = batchService.createBatchInstanceByJson(params, manufactureOrder) 
            
            if(!result.success){
                render (contentType: 'application/json') {
                    result
                }
            }
            else{
                manufactureOrder.properties = params
                manufactureOrder.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(manufactureOrder)
                }
            }
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
