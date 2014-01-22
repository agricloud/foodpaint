package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class StockInSheetDetController {

    def domainService
    def batchService

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

    def save = {
        def purchaseSheetDet=new StockInSheetDet(params)
        def batch = batchService.findOrCreateBatchInstanceByJson(params, purchaseSheetDet)
        purchaseSheetDet.batch = (Batch) batch
        render (contentType: 'application/json') {
            domainService.save(purchaseSheetDet)
        }
    }


    def update = {

        def  purchaseSheetDet = StockInSheetDet.get(params.id)
        purchaseSheetDet.properties = params
        render (contentType: 'application/json') {
            domainService.save(purchaseSheetDet)
        }         
    }



    def delete = {

        def  purchaseSheetDet = StockInSheetDet.get(params.id)

        def result
        try {
            
            result = domainService.delete(purchaseSheetDet)
        
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
