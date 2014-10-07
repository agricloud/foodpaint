package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.transaction.Transactional

class InventoryController {

    def grailsApplication
    def domainService
    def inventoryService

    def index = {

        def list = Inventory.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def indexByWarehouseAndItem = {
        def warehouse = Warehouse.get(params.warehouse.id)
        def item = Item.get(params.item.id)

        def list = Inventory.where{
        	if(warehouse)
        		warehouse==warehouse
        	if(item)
        		item==item
        }.list()

        render (contentType: 'application/json') {
            [data: list, total: list.size()]
        }

    }

    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def inventory=Inventory.get(params.id);  
        if(inventory){   
            render (contentType: 'application/json') {
                [success: true,data:inventory]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }
    
    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

        def inventory=new Inventory()        
        render (contentType: 'application/json') {
            [success: true,data:inventory]
        }
    }

    def save = {
        if(params.qty.toDouble()<0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.inventory.qty.must.be.more.than.zero")]
            }
            return
        }
        def inventory=new Inventory(params)
        render (contentType: 'application/json') {
            domainService.save(inventory)
        }
    }

    @Transactional
    def update(){

        def i18nType = grailsApplication.config.grails.i18nType

        if(params.qty.toDouble()<0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.inventory.qty.must.be.more.than.zero")]
            }
            return
        }
        
        def inventory = Inventory.get(params.id)
        
        if(!InventoryDetail.findByWarehouseAndItem(inventory.warehouse,inventory.item)){
            def inventoryConsumeResult = inventoryService.consume(params, inventory.warehouse.id, inventory.item.id, inventory.qty, null)
            if(inventoryConsumeResult.success){
                def inventoryReplenishResult = inventoryService.replenish(params, params.warehouse.id, params.item.id, params.qty.toDouble(), null)
                if(inventoryReplenishResult.success){
                    render (contentType: 'application/json') {
                        [success:true, message: message(code: "${i18nType}.default.message.update.success", args: [inventory, e])]
                    }
                }
                else{
                    render (contentType: 'application/json') {
                        inventoryReplenishResult
                    } 
                }
            }
            else{
                render (contentType: 'application/json') {
                    inventoryConsumeResult
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success:false, message: message(code: "${i18nType}.inventory.inventoryDetail.has.existed", args: [inventory, e])]
            }
        }

    }

    @Transactional
    def delete(){

        def i18nType = grailsApplication.config.grails.i18nType
        
        def inventory = Inventory.get(params.id)
        if(!InventoryDetail.findByWarehouseAndItem(inventory.warehouse,inventory.item)){
            def result
            try {
                
                result = domainService.delete(inventory)
            
            }catch(DataIntegrityViolationException e){
                log.error e
                def msg = message(code: "${i18nType}.default.message.delete.failed", args: [inventory, e])
                result = [success:false, message: msg] 
            }
            
            render (contentType: 'application/json') {
                result
            }
        }
        else{
            render (contentType: 'application/json') {
                [success:false, message: message(code: "${i18nType}.inventory.inventoryDetail.has.existed", args: [inventory, e])]
            }
        }
    }
    
}
