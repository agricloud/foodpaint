package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class StockInSheetController {

    def domainService

    def index = {

        def list = StockInSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [stockInSheetInstanceList: list, stockInSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def stockInSheet=StockInSheet.get(params.id)

        if(stockInSheet){   

            render (contentType: 'application/json') {
                [success: true,data:stockInSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def stockInSheet=new StockInSheet() 

        render (contentType: 'application/json') {
            [success: true,data:stockInSheet]
        }
    }

    def save = {
        def stockInSheet=new StockInSheet(params)

        render (contentType: 'application/json') {
            domainService.save(stockInSheet)
        }
    }


    def update = {

        def  stockInSheet= StockInSheet.get(params.id)
        stockInSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(stockInSheet)
        }         
    }



    def delete = {
        
        def  stockInSheet = StockInSheet.get(params.id)

        def result
        try {
            result = domainService.delete(stockInSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [stockInSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}