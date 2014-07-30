package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class PurchaseReturnSheetDetController {

    def domainService
    def inventoryDetailService

    def index = {

        def purchaseReturnSheet = PurchaseReturnSheet.get(params.purchaseReturnSheet.id)

        if(purchaseReturnSheet){
            
            def purchaseReturnSheetDet = PurchaseReturnSheetDet.findAllByPurchaseReturnSheet(purchaseReturnSheet)

            render (contentType: 'application/json') {
               [success: true, data:purchaseReturnSheetDet, total: purchaseReturnSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def purchaseReturnSheetDet = PurchaseReturnSheetDet.get(params.id);


        if(purchaseReturnSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.purchaseReturnSheet.id){

            def purchaseReturnSheetDet= new PurchaseReturnSheetDet(params)

            purchaseReturnSheetDet.typeName = purchaseReturnSheetDet.purchaseReturnSheet.typeName
            purchaseReturnSheetDet.name = purchaseReturnSheetDet.purchaseReturnSheet.name

            if(purchaseReturnSheetDet.purchaseReturnSheet.purchaseReturnSheetDets)
                purchaseReturnSheetDet.sequence = purchaseReturnSheetDet.purchaseReturnSheet.purchaseReturnSheetDets*.sequence.max()+1
            else purchaseReturnSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:purchaseReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def purchaseReturnSheetDet=new PurchaseReturnSheetDet(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(purchaseReturnSheetDet.typeName != purchaseReturnSheetDet.purchaseReturnSheet.typeName || purchaseReturnSheetDet.name != purchaseReturnSheetDet.purchaseReturnSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheetDetail.typeName.name.sheet.typeName.name.not.equal')]
            }
            return
        }

        if(purchaseReturnSheetDet.purchaseReturnSheet.supplier!=purchaseReturnSheetDet.purchaseSheetDet.purchaseSheet.supplier){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheetDet.purchaseReturnSheet.supplier.purchaseSheetDet.purchaseSheet.supplier.not.equal', args: [purchaseReturnSheetDet])]
            }
            return
        }

        if(purchaseReturnSheetDet.qty<=0){
             render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseReturnSheetDet])]
            }
            return
        } 

        if(purchaseReturnSheetDet.item != purchaseReturnSheetDet.purchaseSheetDet.item || purchaseReturnSheetDet.batch != purchaseReturnSheetDet.purchaseSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheetDet.itemOrBatch.purchaseSheetDet.itemOrBatch.not.equal', args: [purchaseReturnSheetDet])]
            }
            return
        }
        
        def inventoryConsumeResult = inventoryDetailService.consume(params,purchaseReturnSheetDet.warehouse.id,purchaseReturnSheetDet.warehouseLocation.id, purchaseReturnSheetDet.item.id,purchaseReturnSheetDet.batch.name , purchaseReturnSheetDet.qty, purchaseReturnSheetDet.dateCreated)
        if(inventoryConsumeResult.success){
            render (contentType: 'application/json') {
                domainService.save(purchaseReturnSheetDet)
            }
        }
        else{
            render (contentType: 'application/json') {
                inventoryConsumeResult
            }
        }            
            
    }

    @Transactional
    def update() {
        def purchaseReturnSheetDet = new PurchaseReturnSheetDet(params)
        if(purchaseReturnSheetDet.purchaseReturnSheet.supplier!=purchaseReturnSheetDet.purchaseSheetDet.purchaseSheet.supplier){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheetDet.purchaseReturnSheet.supplier.purchaseSheetDet.purchaseSheet.supplier.not.equal', args: [purchaseReturnSheetDet])]
            }
            return
        }

        if(purchaseReturnSheetDet.qty<=0){
             render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseReturnSheetDet])]
            }
            return
        } 

        if(purchaseReturnSheetDet.item != purchaseReturnSheetDet.purchaseSheetDet.item || purchaseReturnSheetDet.batch != purchaseReturnSheetDet.purchaseSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheetDet.itemOrBatch.purchaseSheetDet.itemOrBatch.not.equal', args: [purchaseReturnSheetDet])]
            }
            return
        }

        purchaseReturnSheetDet = PurchaseReturnSheetDet.get(params.id)
        
        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != purchaseReturnSheetDet.typeName || params.name != purchaseReturnSheetDet.name|| params.sequence.toLong() != purchaseReturnSheetDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheetDetail.typeName.name.sequence.not.allowed.change')]
            }
            return
        }

        def inventoryReplenishResult = inventoryDetailService.replenish(params,purchaseReturnSheetDet.warehouse.id,purchaseReturnSheetDet.warehouseLocation.id, purchaseReturnSheetDet.item.id, purchaseReturnSheetDet.batch.name, purchaseReturnSheetDet.qty,null)
        if(inventoryReplenishResult.success){
            def updateBatch = Batch.get(params.batch.id)
            def inventoryConsumeResult = inventoryDetailService.consume(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toDouble(),null)
            if(inventoryConsumeResult.success){
                purchaseReturnSheetDet.properties = params
                render (contentType: 'application/json') {
                    domainService.save(purchaseReturnSheetDet)
                }
            }
            else{
                def inventoryRecoveryResult = inventoryDetailService.consume(params,purchaseReturnSheetDet.warehouse.id,purchaseReturnSheetDet.warehouseLocation.id, purchaseReturnSheetDet.item.id, purchaseReturnSheetDet.batch.name, purchaseReturnSheetDet.qty,null)
                if(inventoryRecoveryResult.success){
                    render (contentType: 'application/json') {
                        inventoryConsumeResult
                    }
                }
                else{
                    throw new Exception("還原庫存失敗:"+inventoryRecoveryResult.message)
                }        
            }
        }
        else{
            render (contentType: 'application/json') {
                inventoryReplenishResult
            }        
        }
    }


    @Transactional
    def delete(){
        def purchaseReturnSheetDet = PurchaseReturnSheetDet.get(params.id)

        def result
        try {
            def inventoryReplenishResult=inventoryDetailService.replenish(params,purchaseReturnSheetDet.warehouse.id,purchaseReturnSheetDet.warehouseLocation.id, purchaseReturnSheetDet.item.id, purchaseReturnSheetDet.batch.name, purchaseReturnSheetDet.qty, null)
            
            if(inventoryReplenishResult.success)
                result = domainService.delete(purchaseReturnSheetDet)
            else{
                render (contentType: 'application/json') {
                    inventoryReplenishResult
                }
            }
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [purchaseReturnSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}