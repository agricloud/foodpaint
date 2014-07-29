package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class OutSrcPurchaseReturnSheetDetController{

    def domainService
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

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(outSrcPurchaseReturnSheetDet.typeName != outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.typeName || outSrcPurchaseReturnSheetDet.name != outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheetDetail.typeName.name.sheet.typeName.name.not.equal')]
            }
            return
        }

        if(outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.supplier!=outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.supplier.outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }
        if(outSrcPurchaseReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }

        if(outSrcPurchaseReturnSheetDet.item != outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.item || outSrcPurchaseReturnSheetDet.batch != outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.itemOrBatch.outSrcPurchaseSheetDet.itemOrBatch.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }
        if(outSrcPurchaseReturnSheetDet.manufactureOrder != outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.manufactureOrder){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.manufactureOrder.outSrcPurchaseSheetDet.manufactureOrder.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }

        def inventoryConsumeResult = inventoryDetailService.consume(params,outSrcPurchaseReturnSheetDet.warehouse.id,outSrcPurchaseReturnSheetDet.warehouseLocation.id, outSrcPurchaseReturnSheetDet.item.id, outSrcPurchaseReturnSheetDet.batch.name, outSrcPurchaseReturnSheetDet.qty, outSrcPurchaseReturnSheetDet.dateCreated)

        if(inventoryConsumeResult.success){
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


    @Transactional
    def update() {
        def outSrcPurchaseReturnSheetDet = new OutSrcPurchaseReturnSheetDet(params)

        if(outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.supplier!=outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.supplier.outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }
        if(outSrcPurchaseReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }
        if(outSrcPurchaseReturnSheetDet.item != outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.item || outSrcPurchaseReturnSheetDet.batch != outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.itemOrBatch.outSrcPurchaseSheetDet.itemOrBatch.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }
        if(outSrcPurchaseReturnSheetDet.manufactureOrder != outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.manufactureOrder){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheetDet.manufactureOrder.outSrcPurchaseSheetDet.manufactureOrder.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }

        outSrcPurchaseReturnSheetDet = OutSrcPurchaseReturnSheetDet.get(params.id)

        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != outSrcPurchaseReturnSheetDet.typeName || params.name != outSrcPurchaseReturnSheetDet.name|| params.sequence.toLong() != outSrcPurchaseReturnSheetDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheetDetail.typeName.name.sequence.not.allowed.change')]
            }
            return
        }
        
        // (補充存貨)還原回到還沒退貨
        def inventoryReplenishResult = inventoryDetailService.replenish(params,outSrcPurchaseReturnSheetDet.warehouse.id, outSrcPurchaseReturnSheetDet.warehouseLocation.id, outSrcPurchaseReturnSheetDet.item.id, outSrcPurchaseReturnSheetDet.batch.name, outSrcPurchaseReturnSheetDet.qty,null)
        if(inventoryReplenishResult){
            def updateBatch = Batch.get(params.batch.id)
            // (消耗存貨)做本次託外生產進貨退出
            def inventoryConsumeResult=inventoryDetailService.consume(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toDouble(),null)
            if(inventoryConsumeResult.success){
                outSrcPurchaseReturnSheetDet.properties = params
                render (contentType: 'application/json') {
                    domainService.save(outSrcPurchaseReturnSheetDet)
                }
            }
            else{
                // (還原存貨)還原到補充之前
                def inventoryRecoveryResult= inventoryDetailService.consume(params,outSrcPurchaseReturnSheetDet.warehouse.id,outSrcPurchaseReturnSheetDet.warehouseLocation.id, outSrcPurchaseReturnSheetDet.item.id, outSrcPurchaseReturnSheetDet.batch.name, outSrcPurchaseReturnSheetDet.qty,null)
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

        def outSrcPurchaseReturnSheetDet = OutSrcPurchaseReturnSheetDet.get(params.id)
        
        def result
        try {
            def inventoryReplenishResult = inventoryDetailService.replenish(params,outSrcPurchaseReturnSheetDet.warehouse.id,outSrcPurchaseReturnSheetDet.warehouseLocation.id, outSrcPurchaseReturnSheetDet.item.id, outSrcPurchaseReturnSheetDet.batch.name, outSrcPurchaseReturnSheetDet.qty,null)
            if(inventoryReplenishResult.success){
                result = domainService.delete(outSrcPurchaseReturnSheetDet)
            }
            else{
                render (contentType: 'application/json') {
                    inventoryReplenishResult
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
