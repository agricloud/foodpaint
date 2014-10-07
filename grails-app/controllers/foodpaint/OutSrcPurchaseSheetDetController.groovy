package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class OutSrcPurchaseSheetDetController {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.outSrcPurchaseSheet.id)

        if(outSrcPurchaseSheet){
            
            def outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.findAllByOutSrcPurchaseSheet(outSrcPurchaseSheet)

            render (contentType: 'application/json') {
               [success: true, data:outSrcPurchaseSheetDet, total: outSrcPurchaseSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }
        }
        
    }


    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id);


        if(outSrcPurchaseSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }


    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

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
                [success: false,message:message(code: "${i18nType}.outSrcPurchaseSheetDet.message.create.failed")]
            }            
        }

    }

    @Transactional
    def save(){

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseSheetDet=new OutSrcPurchaseSheetDet(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(outSrcPurchaseSheetDet.typeName != outSrcPurchaseSheetDet.outSrcPurchaseSheet.typeName || outSrcPurchaseSheetDet.name != outSrcPurchaseSheetDet.outSrcPurchaseSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sheet.typeName.name.not.equal")]
            }
            return
        }

        if(outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier != outSrcPurchaseSheetDet.manufactureOrder.supplier ){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier.manufactureOrder.supplier.not.equal", args: [outSrcPurchaseSheetDet])]
            }
            return
        }

        if(outSrcPurchaseSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [outSrcPurchaseSheetDet])]
            }
            return
        }

        def result = batchService.findOrCreateBatchInstanceByJson(params, outSrcPurchaseSheetDet)
        
        if(!result.success){
             render (contentType: 'application/json') {
                result
            }
            return
        }

        def inventoryReplenishResult = inventoryDetailService.replenish(params,outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty, outSrcPurchaseSheetDet.dateCreated)
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


    @Transactional
    def update() {

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseSheetDet = new OutSrcPurchaseSheetDet(params)

        if(outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier != outSrcPurchaseSheetDet.manufactureOrder.supplier ){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.outSrcPurchaseSheetDet.outSrcPurchaseSheet.supplier.manufactureOrder.supplier.not.equal", args: [outSrcPurchaseSheetDet])]
            }
            return
        }
        
        if(outSrcPurchaseSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [outSrcPurchaseSheetDet])]
            }
            return
        }

        def result = batchService.findOrCreateBatchInstanceByJson(params, outSrcPurchaseSheetDet)
   
        if(!result.success){
             render (contentType: 'application/json') {
                result
            }
            return
        }

        outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id)

        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != outSrcPurchaseSheetDet.typeName || params.name != outSrcPurchaseSheetDet.name|| params.sequence.toLong() != outSrcPurchaseSheetDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sequence.not.allowed.change")]
            }
            return
        }

        //將更新前已進的數量扣除庫存
        def inventoryConsumeResult= inventoryDetailService.consume(params,outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty, null)
        if(inventoryConsumeResult){
            //將欲更新的數量加入庫存
            def inventoryReplenishResult=inventoryDetailService.replenish(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, params.batch.name, params.qty.toDouble(), null)
            if(inventoryReplenishResult.success){
                outSrcPurchaseSheetDet.properties = params
                outSrcPurchaseSheetDet.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(outSrcPurchaseSheetDet)
                }
            }
            else{
                //把更新前已進的數量再加回庫存 還原更新前狀態
                def inventoryRecoveryResult= inventoryDetailService.replenish(params,outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty, null)
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

        def i18nType = grailsApplication.config.grails.i18nType

        def outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id)
        
        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(params,outSrcPurchaseSheetDet.warehouse.id,outSrcPurchaseSheetDet.warehouseLocation.id, outSrcPurchaseSheetDet.item.id, outSrcPurchaseSheetDet.batch.name, outSrcPurchaseSheetDet.qty, null)
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
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [outSrcPurchaseSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
