package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class PurchaseReturnSheetController{

    def domainService

    def index = {

        def list = PurchaseReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [purchaseReturnSheetInstanceList: list, purchaseReturnSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def purchaseReturnSheet=PurchaseReturnSheet.get(params.id)

        if(purchaseReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def purchaseReturnSheet=new PurchaseReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:purchaseReturnSheet]
        }
    }

    def save = {
        def purchaseReturnSheet=new PurchaseReturnSheet(params)

        render (contentType: 'application/json') {
            domainService.save(purchaseReturnSheet)
        }
    }


    def update = {

        def purchaseReturnSheet= PurchaseReturnSheet.get(params.id)
        if(purchaseReturnSheet.purchaseReturnSheetDets && params.supplier.id != purchaseReturnSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheet.purchaseReturnSheetDets.exists.supplier.not.allowed.change', args: [purchaseReturnSheet])]
            }
            return
        }
        purchaseReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(purchaseReturnSheet)
        }         
    }



    def delete = {
        
        def purchaseReturnSheet = PurchaseReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(purchaseReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [purchaseReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}