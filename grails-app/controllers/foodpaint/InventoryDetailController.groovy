package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.transaction.Transactional

class InventoryDetailController {

    def domainService
    def batchService
    def inventoryDetailService

    def index() {

        def list = InventoryDetail.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def indexByWarehouseAndWarehouseLocationAndItemAndBatch(){
        def warehouse = Warehouse.get(params.warehouse.id)
        def warehouseLocation = WarehouseLocation.get(params.warehouseLocation.id)
        def item = Item.get(params.item.id)
        def batch = Batch.get(params.batch.id)

        def list = InventoryDetail.where{
        	if(warehouse)
        		warehouse==warehouse
        	if(warehouseLocation)
        		warehouseLocation==warehouseLocation
        	if(item)
        		item==item
        	if(batch)
        		batch==batch
        }.list()

        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }

    }

    def show = {
        def inventoryDetail=InventoryDetail.get(params.id);  
        if(inventoryDetail){
            render (contentType: 'application/json') {
                [success: true,data:inventoryDetail]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }
    
    def create = {
        def inventoryDetail=new InventoryDetail()        
        render (contentType: 'application/json') {
            [success: true,data:inventoryDetail]
        }
    }

    def save = {

        def result = batchService.findOrCreateBatchInstanceByJson(params, null)
        if(!result.success){
             render (contentType: 'application/json') {
                result
            }
            return
        }

        def batch = (Batch) result.batch
        def replenishResult=inventoryDetailService.replenish(params,params.warehouse.id, params.warehouseLocation.id, params.item.id, batch.name, params.qty.toDouble(),new Date())
        if(replenishResult.success){
            render (contentType: 'application/json') {
                [success:true, message: message(code: 'default.message.save.success', args: [replenishResult.inventoryDetail])]
            }
        }
    }

    @Transactional
    def update(){

        def inventoryDetail = InventoryDetail.get(params.id)

        if(inventoryDetailService.consume(params,inventoryDetail.warehouse.id, inventoryDetail.warehouseLocation.id, inventoryDetail.item.id, inventoryDetail.batch.name, inventoryDetail.qty, null).success){
            def result = batchService.findOrCreateBatchInstanceByJson(params, null)
            if(!result.success){
                 render (contentType: 'application/json') {
                    result
                }
                return
            }

            def updateBatch = (Batch) result.batch
            def replenishResult = inventoryDetailService.replenish(params,params.warehouse.id, params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toDouble(), null)
            if(replenishResult.success){
                render (contentType: 'application/json') {
                    [success:true, message: message(code: 'default.message.update.success', args: [inventoryDetail])]
                }
            }
            else{
                inventoryDetailService.replenish(params,inventoryDetail.warehouse.id, inventoryDetail.warehouseLocation.id, inventoryDetail.item.id, inventoryDetail.batch.name, inventoryDetail.qty, null)
                render (contentType: 'application/json') {
                    replenishResult
                }
            }
        }
        else
           result = [success:false, message: message(code: 'default.message.update.failed', args: [inventoryDetail])]
    }

    @Transactional
    def delete(){
        
        def inventoryDetail = InventoryDetail.get(params.id)

        if(inventoryDetailService.consume(params,inventoryDetail.warehouse.id, inventoryDetail.warehouseLocation.id, inventoryDetail.item.id, inventoryDetail.batch.name, inventoryDetail.qty, null).success){
            def result
            try {         
                result = domainService.delete(inventoryDetail)
            
            }catch(DataIntegrityViolationException e){
                log.error e
                def msg = message(code: 'default.message.delete.failed', args: [inventoryDetail, e])
                result = [success:false, message: msg] 
            }
            
            render (contentType: 'application/json') {
                result
            }
        }
        else
           result = [success:false, message: message(code: 'default.message.delete.failed', args: [inventoryDetail])]  

    }
    
}
