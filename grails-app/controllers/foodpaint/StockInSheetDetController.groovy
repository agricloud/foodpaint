package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.transaction.annotation.Transactional
import grails.converters.JSON


class StockInSheetDetController {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def i18nType = grailsApplication.config.grails.i18nType

        def stockInSheet = StockInSheet.get(params.stockInSheet.id)

        if(stockInSheet){
            
            def stockInSheetDet = StockInSheetDet.findAllByStockInSheet(stockInSheet)

            render (contentType: 'application/json') {
               [success: true, data:stockInSheetDet, total: stockInSheetDet.size()]
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

        def stockInSheetDet = StockInSheetDet.get(params.id);


        if(stockInSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:stockInSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }


    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

        if(params.stockInSheet.id){

            def stockInSheetDet= new StockInSheetDet(params)

            stockInSheetDet.typeName = stockInSheetDet.stockInSheet.typeName
            stockInSheetDet.name = stockInSheetDet.stockInSheet.name

            if(stockInSheetDet.stockInSheet.stockInSheetDets)
                stockInSheetDet.sequence = stockInSheetDet.stockInSheet.stockInSheetDets*.sequence.max()+1
            else stockInSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:stockInSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.stockInSheetDet.message.create.failed")]
            }            
        }

    }

    @Transactional
    def save(){

        def i18nType = grailsApplication.config.grails.i18nType

        def stockInSheetDet=new StockInSheetDet(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(stockInSheetDet.typeName != stockInSheetDet.stockInSheet.typeName || stockInSheetDet.name != stockInSheetDet.stockInSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sheet.typeName.name.not.equal")]
            }
            return
        }

        //如果領料單頭工作站、供應商與製令工作站、供應商不相同 不允許儲存
        if(stockInSheetDet.stockInSheet.workstation != stockInSheetDet.manufactureOrder.workstation ){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.stockInSheetDet.stockInSheet.workstation.manufactureOrder.workstation.not.equal", args: [stockInSheetDet])]
            }
            return
        }
        if(stockInSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [stockInSheetDet])]
            }
            return
        }

        def result = batchService.findOrCreateBatchInstanceByJson(params, stockInSheetDet)
        if(result.success){
            def inventoryReplenishResult = inventoryDetailService.replenish(params,stockInSheetDet.warehouse.id,stockInSheetDet.warehouseLocation.id, stockInSheetDet.item.id, stockInSheetDet.batch.name, stockInSheetDet.qty,stockInSheetDet.dateCreated)
            if(inventoryReplenishResult.success){
                stockInSheetDet.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(stockInSheetDet)
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

    @Transactional
    def update(){

        def i18nType = grailsApplication.config.grails.i18nType

        def stockInSheetDet = new StockInSheetDet(params)

        //入庫單與製令之工作站需相同
        if(stockInSheetDet.stockInSheet.workstation != stockInSheetDet.manufactureOrder.workstation ){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.stockInSheetDet.stockInSheet.workstation.manufactureOrder.workstation.not.equal", args: [stockInSheetDet])]
            }
            return
        }
        if(stockInSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [stockInSheetDet])]
            }
            return
        }

        def result = batchService.findOrCreateBatchInstanceByJson(params, stockInSheetDet)

        if(!result.success){
            render (contentType: 'application/json') {
                result
            } 
            return
        }

        stockInSheetDet = StockInSheetDet.get(params.id)

        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != stockInSheetDet.typeName || params.name != stockInSheetDet.name|| params.sequence.toLong() != stockInSheetDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sequence.not.allowed.change")]
            }
            return
        }

        //將更新前已入的數量扣除庫存
        def inventoryConsumeResult= inventoryDetailService.consume(params,stockInSheetDet.warehouse.id,stockInSheetDet.warehouseLocation.id, stockInSheetDet.item.id, stockInSheetDet.batch.name, stockInSheetDet.qty,null)
        if(inventoryConsumeResult.success){
            //將欲更新的數量加入庫存
            def inventoryReplenishResult=inventoryDetailService.replenish(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, params.batch.name, params.qty.toDouble(),null)
            if(inventoryReplenishResult.success){
                stockInSheetDet.properties = params
                stockInSheetDet.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(stockInSheetDet)
                }
            }
            else{
                //把更新前已入的數量再加回庫存 還原更新前狀態
                def inventoryRecoveryResult= inventoryDetailService.replenish(params,stockInSheetDet.warehouse.id,stockInSheetDet.warehouseLocation.id, stockInSheetDet.item.id, stockInSheetDet.batch.name, stockInSheetDet.qty,null)
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

        def stockInSheetDet = StockInSheetDet.get(params.id)
        
        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(params,stockInSheetDet.warehouse.id,stockInSheetDet.warehouseLocation.id, stockInSheetDet.item.id, stockInSheetDet.batch.name, stockInSheetDet.qty,null)
            if(inventoryConsumeResult.success){
                result = domainService.delete(stockInSheetDet)
            }
            else{
                render (contentType: 'application/json') {
                    inventoryConsumeResult
                }
            } 
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [stockInSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
