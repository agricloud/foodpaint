package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

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

        if(purchaseSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseSheetDet])]
            }
            return
        }

        def result = batchService.findOrCreateBatchInstanceByJson(params, purchaseSheetDet)
        if(!result.success){
             render (contentType: 'application/json') {
                result
            }
            return
        }

        def inventoryReplenishResult = inventoryDetailService.replenish(params,purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty, purchaseSheetDet.dateCreated)
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

    @Transactional
    def update() {
        def purchaseSheetDet = new PurchaseSheetDet(params)
        //單別、單號一旦建立不允許變更
        if(params.typeName != purchaseSheetDet.typeName || params.name != purchaseSheetDet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheet.typeName.name.not.allowed.change')]
            }
            return
        }
        if(purchaseSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseSheetDet])]
            }
            return
        }


        def result = batchService.findOrCreateBatchInstanceByJson(params, purchaseSheetDet)
        if(!result.success){
            render (contentType: 'application/json') {
                result
            }
            return
        }

        purchaseSheetDet = PurchaseSheetDet.get(params.id)

        //將更新前的已進數量扣除庫存
        def inventoryConsumeResult = inventoryDetailService.consume(params,purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty, null)
        
        if(inventoryConsumeResult.success){
            //將欲更新的已進數量補充庫存
            def inventoryReplenishResult = inventoryDetailService.replenish(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, params.batch.name, params.qty.toDouble(), null)
            if(inventoryReplenishResult.success){
                //庫存更新完畢，儲存單據
                purchaseSheetDet.properties = params
                purchaseSheetDet.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(purchaseSheetDet)
                }
            }
            else{
                //還原更新前的已進數量，補充回庫存
                def inventoryRecoveryResult = inventoryDetailService.replenish(params,purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty, null)
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



    @Transactional
    def delete(){
        def purchaseSheetDet = PurchaseSheetDet.get(params.id)

        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(params,purchaseSheetDet.warehouse.id,purchaseSheetDet.warehouseLocation.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty, null)
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
