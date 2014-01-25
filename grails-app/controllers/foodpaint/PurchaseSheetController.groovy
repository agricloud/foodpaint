package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class PurchaseSheetController {

    def domainService

    def index = {

        def list = PurchaseSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [purchaseSheetInstanceList: list, purchaseSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def purchaseSheet=PurchaseSheet.get(params.id)

        if(purchaseSheet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def purchaseSheet=new PurchaseSheet() 

        render (contentType: 'application/json') {
            [success: true,data:purchaseSheet]
        }
    }

    def save = {
        def purchaseSheet=new PurchaseSheet(params)

        render (contentType: 'application/json') {
            domainService.save(purchaseSheet)
        }
    }


    def update = {

        def  purchaseSheet= PurchaseSheet.get(params.id)
        purchaseSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(purchaseSheet)
        }         
    }



    def delete = {
        
        def  purchaseSheet = PurchaseSheet.get(params.id)

        def result
        try {
            result = domainService.delete(purchaseSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [purchaseSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
