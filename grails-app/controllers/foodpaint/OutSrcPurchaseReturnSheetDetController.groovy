package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class OutSrcPurchaseReturnSheetDetController {

    def domainService
    // def batchService
    def inventoryDetailService

    def index = {

        def outSrcPurchaseReturnSheet = OutSrcPurchaseReturnSheet.get(params.outSrcPurchaseReturnSheet.id)

        if(outSrcPurchaseReturnSheet){
            
            def outSrcPurchaseReturnSheetDet = OutSrcPurchaseReturnSheetDet.findAllByOutSrcPurchaseReturnSheet(outSrcPurchaseReturnSheet)

            render (contentType: 'application/json') {
               [success: true, data:outSrcPurchaseReturnSheetDet, total: outSrcPurchaseReturnSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def outSrcPurchaseReturnSheetDet = OutSrcPurchaseReturnSheetDet.get(params.id);


        if(outSrcPurchaseReturnSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.outSrcPurchaseReturnSheet.id){

            def outSrcPurchaseReturnSheetDet= new OutSrcPurchaseReturnSheetDet(params)

            outSrcPurchaseReturnSheetDet.typeName = outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.typeName
            outSrcPurchaseReturnSheetDet.name = outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.name

            if(outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets)
                outSrcPurchaseReturnSheetDet.sequence = outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets*.sequence.max()+1
            else outSrcPurchaseReturnSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def outSrcPurchaseReturnSheetDet=new OutSrcPurchaseReturnSheetDet(params)
        if(outSrcPurchaseReturnSheetDet.qty>0){
            // def result = batchService.findOrCreateBatchInstanceByJson(params, outSrcPurchaseReturnSheetDet)
            
            println outSrcPurchaseReturnSheetDet.qty
            println outSrcPurchaseReturnSheetDet.manufactureOrder.qty
            println outSrcPurchaseReturnSheetDet.item
            println outSrcPurchaseReturnSheetDet.manufactureOrder.item
            println outSrcPurchaseReturnSheetDet.manufactureOrder.batch.item

            if(outSrcPurchaseReturnSheetDet.batch.item == outSrcPurchaseReturnSheetDet.manufactureOrder.batch.item){
                def sheet = outSrcPurchaseReturnSheetDet

                println sheet.warehouse.id
                println sheet.warehouseLocation.id
                println sheet.item.id
                println sheet.batch.name
                println sheet.qty
                println ""
                println params.warehouse.id
                println params.warehouseLocation.id
                println params.item.id
                println params.batch.name
                println params.qty
                println ""
                
                def updateBatch = Batch.get(params.batch.id)
                
                println InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(sheet.warehouse,sheet.warehouseLocation,sheet.item,updateBatch)
                println ""
              //傳入的params沒有batch.name 只有batch.id
                
                
                def inventoryConsumeResult = inventoryDetailService.consume(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong())

                if(inventoryConsumeResult.success){
                    // outSrcPurchaseReturnSheetDet.batch = (Batch) result.batch
                    render (contentType: 'application/json') {
                        domainService.save(outSrcPurchaseReturnSheetDet)
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
                    [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.item.manufactureOrder.batch.item.not.equal')]
                } 
            }
        }
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [outSrcPurchaseReturnSheetDet])]
            }
        }
    }


    @Transactional
    def update() {
        // 現在的單身資料
        def outSrcPurchaseReturnSheetDet_params = new OutSrcPurchaseReturnSheetDet(params)
        if(outSrcPurchaseReturnSheetDet_params.qty>0){
            // def result = batchService.findOrCreateBatchInstanceByJson(params, outSrcPurchaseReturnSheetDet)
            // 使用id查出Batch
            def updateBatch = Batch.get(params.batch.id)
            println updateBatch
            // 查出先前儲存之進貨退出單身檔
            println "outSrcPurchaseReturnSheetDet.id: "+params.id
            def outSrcPurchaseReturnSheetDet = OutSrcPurchaseReturnSheetDet.get(params.id)
            println outSrcPurchaseReturnSheetDet.batch
            if(outSrcPurchaseReturnSheetDet_params.batch.item==outSrcPurchaseReturnSheetDet.batch.item && outSrcPurchaseReturnSheetDet_params.batch==outSrcPurchaseReturnSheetDet.batch){
                
                println outSrcPurchaseReturnSheetDet.warehouse.id
                println outSrcPurchaseReturnSheetDet.warehouseLocation.id
                println outSrcPurchaseReturnSheetDet.manufactureOrder
                println outSrcPurchaseReturnSheetDet.manufactureOrder.batch.item
                println outSrcPurchaseReturnSheetDet.batch.name
                println outSrcPurchaseReturnSheetDet.item.id
                println outSrcPurchaseReturnSheetDet.qty
                // (補充存貨)還原回到還沒退貨
                def inventoryReplenishResult = inventoryDetailService.replenish(outSrcPurchaseReturnSheetDet.warehouse.id, outSrcPurchaseReturnSheetDet.warehouseLocation.id, outSrcPurchaseReturnSheetDet.item.id, updateBatch.name, outSrcPurchaseReturnSheetDet.qty)

                if(inventoryReplenishResult){
                    // (消耗存貨)做本次託外生產進貨退出
                    def inventoryConsumeResult=inventoryDetailService.consume(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong())
                    if(inventoryConsumeResult.success){
                        outSrcPurchaseReturnSheetDet.properties = params
                        // outSrcPurchaseReturnSheetDet.batch = (Batch) result.batch
                        render (contentType: 'application/json') {
                            domainService.save(outSrcPurchaseReturnSheetDet)
                        }
                    }
                    else{
                        // (還原存貨)還原到補充之前
                        def inventoryRecoveryResult= inventoryDetailService.consume(outSrcPurchaseReturnSheetDet.warehouse.id,outSrcPurchaseReturnSheetDet.warehouseLocation.id, outSrcPurchaseReturnSheetDet.item.id, outSrcPurchaseReturnSheetDet.batch.name, outSrcPurchaseReturnSheetDet.qty.toLong())
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
            else{
                render (contentType: 'application/json') {
                     [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.item.manufactureOrder.batch.item.not.equal')]
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [outSrcPurchaseReturnSheetDet])]
            }
        }       
    }



    @Transactional
    def delete(){

        def  outSrcPurchaseReturnSheetDet = OutSrcPurchaseReturnSheetDet.get(params.id)
        
        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.replenish(outSrcPurchaseReturnSheetDet.warehouse.id,outSrcPurchaseReturnSheetDet.warehouseLocation.id, outSrcPurchaseReturnSheetDet.item.id, outSrcPurchaseReturnSheetDet.batch.name, outSrcPurchaseReturnSheetDet.qty)
            if(inventoryConsumeResult.success){
                result = domainService.delete(outSrcPurchaseReturnSheetDet)
            }
            else{
                render (contentType: 'application/json') {
                    inventoryConsumeResult
                }
            }   
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [outSrcPurchaseReturnSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
