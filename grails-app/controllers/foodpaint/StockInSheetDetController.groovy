package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.transaction.annotation.Transactional
import grails.converters.JSON


class StockInSheetDetController {

    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def stockInSheet = StockInSheet.get(params.stockInSheet.id)

        if(stockInSheet){
            
            def stockInSheetDet = StockInSheetDet.findAllByStockInSheet(stockInSheet)

            render (contentType: 'application/json') {
               [success: true, data:stockInSheetDet, total: stockInSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def stockInSheetDet = StockInSheetDet.get(params.id);


        if(stockInSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:stockInSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

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
                [success: false,message:message(code: 'stockInSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def stockInSheetDet=new StockInSheetDet(params)
        if(stockInSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, stockInSheetDet)
            if(!result.success){
                
                render (contentType: 'application/json') {
                    result
                }
            }
            else{
                inventoryDetailService.replenish(params.warehouse.id, params.item.id, params.batch.name, stockInSheetDet.qty)
                stockInSheetDet.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(stockInSheetDet)
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [stockInSheetDet])]
            }
        }
    }

    @Transactional
    def update(){

        def stockInSheetDet = new StockInSheetDet(params)
        if(stockInSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, stockInSheetDet)

            if(!result.success){
                render (contentType: 'application/json') {
                    result
                }
            }
            else{
                stockInSheetDet = StockInSheetDet.get(params.id)
                inventoryDetailService.consume(params.warehouse.id, params.item.id, params.batch.name, stockInSheetDet.qty)
                stockInSheetDet.properties = params
                inventoryDetailService.replenish(params.warehouse.id, params.item.id, params.batch.name, stockInSheetDet.qty)
                stockInSheetDet.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(stockInSheetDet)
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [stockInSheetDet])]
            }
        }      
    }


    @Transactional
    def delete(){

        def  stockInSheetDet = StockInSheetDet.get(params.id)
        
        inventoryDetailService.consume(params.warehouse.id, params.item.id, params.batch.name, stockInSheetDet.qty)
        
        def result
        try {
            
            result = domainService.delete(stockInSheetDet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [stockInSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
