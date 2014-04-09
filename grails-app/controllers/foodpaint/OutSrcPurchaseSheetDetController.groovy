package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class OutSrcPurchaseSheetDetController {

    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.outSrcPurchaseSheet.id)

        if(outSrcPurchaseSheet){
            
            def outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.findAllByOutSrcPurchaseSheet(outSrcPurchaseSheet)

            render (contentType: 'application/json') {
               [success: true, data:outSrcPurchaseSheetDet, total: outSrcPurchaseSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id);


        if(outSrcPurchaseSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.outSrcPurchaseSheet.id){

            def outSrcPurchaseSheetDet= new OutSrcPurchaseSheetDet(params)

            outSrcPurchaseSheetDet.typeName = outSrcPurchaseSheetDet.outSrcPurchaseSheet.typeName
            outSrcPurchaseSheetDet.name = outSrcPurchaseSheetDet.outSrcPurchaseSheet.name

            if(outSrcPurchaseSheetDet.outSrcPurchaseSheet.outSrcPurchaseSheetDets)
                outSrcPurchaseSheetDet.sequence = outSrcPurchaseSheetDet.outSrcPurchaseSheet.outSrcPurchaseSheetDets*.sequence.max()+1
            else outSrcPurchaseSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def outSrcPurchaseSheetDet=new OutSrcPurchaseSheetDet(params)
        if(outSrcPurchaseSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, outSrcPurchaseSheetDet)
            if(result.success){
                def inventoryReplenishResult = inventoryDetailService.replenish(outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty)
                if(inventoryReplenishResult.success){
                    outSrcPurchaseSheetDet.batch = (Batch) result.batch
                    render (contentType: 'application/json') {
                        domainService.save(outSrcPurchaseSheetDet)
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
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [outSrcPurchaseSheetDet])]
            }
        }
    }


    @Transactional
    def update() {
        def outSrcPurchaseSheetDet = new OutSrcPurchaseSheetDet(params)
        if(outSrcPurchaseSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, outSrcPurchaseSheetDet)
       
            if(result.success){
                outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id)
                //將更新前已進的數量扣除庫存
                def inventoryConsumeResult= inventoryDetailService.consume(outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty)
                if(inventoryConsumeResult){
                    //將欲更新的數量加入庫存
                    def inventoryReplenishResult=inventoryDetailService.replenish(params.warehouse.id,params.warehouseLocation.id, params.item.id, params.batch.name, params.qty.toLong())
                    if(inventoryReplenishResult.success){
                        outSrcPurchaseSheetDet.properties = params
                        outSrcPurchaseSheetDet.batch = (Batch) result.batch
                        render (contentType: 'application/json') {
                            domainService.save(outSrcPurchaseSheetDet)
                        }
                    }
                    else{
                        //把更新前已進的數量再加回庫存 還原更新前狀態
                        def inventoryRecoveryResult= inventoryDetailService.replenish(outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty)
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
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [outSrcPurchaseSheetDet])]
            }
        }       
    }



    @Transactional
    def delete(){

        def  outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id)
        
        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty)
            if(inventoryConsumeResult.success){
                result = domainService.delete(outSrcPurchaseSheetDet)
            }
            else{
                render (contentType: 'application/json') {
                    inventoryConsumeResult
                }
            }   
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [outSrcPurchaseSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
