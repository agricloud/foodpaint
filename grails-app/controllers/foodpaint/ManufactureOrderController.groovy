package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class ManufactureOrderController {

    def domainService
    def batchService


    def index(Integer max) {



        def list = ManufactureOrder.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [manufactureOrderInstanceList: list, manufactureOrderInstanceTotal: list.totalCount]
        }


        
    }
    def show(Long id){

        def manufactureOrder=ManufactureOrder.findById(id);


        if(manufactureOrder){   
            def manufactureOrderJson =  JSON.parse((manufactureOrder as JSON).toString())
            manufactureOrderJson["item.id"] = manufactureOrder.item.id
            manufactureOrderJson["item.name"] = manufactureOrder.item.name
            manufactureOrderJson["item.title"] = manufactureOrder.item.title
            manufactureOrderJson["item.spec"] = manufactureOrder.item.spec
            manufactureOrderJson["item.unit"] = manufactureOrder.item.unit
            manufactureOrderJson["item.description"] = manufactureOrder.item.description

            manufactureOrderJson["batch.id"] = manufactureOrder?.batch?.id
            manufactureOrderJson["batch.name"] = manufactureOrder?.batch?.name

            render (contentType: 'application/json') {
                [success: true,data:manufactureOrderJson]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }
    def create(){

        def manufactureOrder=new ManufactureOrder()        
        render (contentType: 'application/json') {
            [success: true,data:manufactureOrder]
        }
    }

    def save(){
        def manufactureOrderInstance=new ManufactureOrder(params)
        def batch = batchService.findOrCreateBatchInstanceByJson(params, manufactureOrderInstance)
 
        manufactureOrderInstance.batch = (Batch) batch

        render (contentType: 'application/json') {
            domainService.save(manufactureOrderInstance)
        }
    }


    def update(){
        log.info params.effectStartDate
        def  manufactureOrderInstance = ManufactureOrder.findById(params.id)
        manufactureOrderInstance.properties = params
        render (contentType: 'application/json') {
            domainService.save(manufactureOrderInstance)
        }         
    }



    def delete(){
        
        def  manufactureOrderInstance = ManufactureOrder.findById(params.id)

        def result
        try {
            
            result = domainService.delete(manufactureOrderInstance)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [manufactureOrderInstance, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
