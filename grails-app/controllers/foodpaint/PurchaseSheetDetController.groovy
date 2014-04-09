package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PurchaseSheetDetController {

    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def purchaseSheet = PurchaseSheet.get(params.purchaseSheet.id)

        if(purchaseSheet){
            
            def purchaseSheetDet = PurchaseSheetDet.findAllByPurchaseSheet(purchaseSheet)

            render (contentType: 'application/json') {
               [success: true, data:purchaseSheetDet, total: purchaseSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def purchaseSheetDet = PurchaseSheetDet.get(params.id);


        if(purchaseSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.purchaseSheet.id){

            def purchaseSheetDet= new PurchaseSheetDet(params)

            purchaseSheetDet.typeName = purchaseSheetDet.purchaseSheet.typeName
            purchaseSheetDet.name = purchaseSheetDet.purchaseSheet.name

            if(purchaseSheetDet.purchaseSheet.purchaseSheetDets)
                purchaseSheetDet.sequence = purchaseSheetDet.purchaseSheet.purchaseSheetDets*.sequence.max()+1
            else purchaseSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:purchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def purchaseSheetDet=new PurchaseSheetDet(params)
        if(purchaseSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, purchaseSheetDet)
            if(result.success){
                def inventoryReplenishResult = inventoryDetailService.replenish(purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty)
                if(inventoryReplenishResult.success){
                    purchaseSheetDet.batch = (Batch) result.batch
                    render (contentType: 'application/json') {
                        domainService.save(purchaseSheetDet)
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
                    result
                }
            }
        } 
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseSheetDet])]
            }
        } 
    }

    @Transactional
    def update() {
        def purchaseSheetDet = new PurchaseSheetDet(params)
        if(purchaseSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, purchaseSheetDet)
            if(result.success){
                purchaseSheetDet = PurchaseSheetDet.get(params.id)
                //將更新前已進的數量扣除庫存
                def inventoryConsumeResult = inventoryDetailService.consume(purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty)
                if(inventoryConsumeResult.success){
                    //將欲更新的數量加入庫存
                    def inventoryReplenishResult = inventoryDetailService.replenish(params.warehouse.id,params.warehouseLocation.id, params.item.id, params.batch.name, params.qty.toLong())
                    if(inventoryReplenishResult.success){
                        purchaseSheetDet.properties = params
                        purchaseSheetDet.batch = (Batch) result.batch
                        render (contentType: 'application/json') {
                            domainService.save(purchaseSheetDet)
                        }
                    }
                    else{
                        def inventoryRecoveryResult = inventoryDetailService.replenish(purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty)
                        if(inventoryRecoveryResult.success){
                            render (contentType: 'application/json') {
                                inventoryReplenishResult
                            }
                        }
                        else{
                            throw new Exception("還原庫存失敗:"+inventoryRecoveryResult.message)
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
                    result
                }
            }
        } 
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseSheetDet])]
            }
        }  
    }



    @Transactional
    def delete(){
        def  purchaseSheetDet = PurchaseSheetDet.get(params.id)

        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty)
            if(inventoryConsumeResult.success){
                result = domainService.delete(purchaseSheetDet)
            }
            else{
                render (contentType: 'application/json') {
                    inventoryConsumeResult
                }
            }
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [purchaseSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
