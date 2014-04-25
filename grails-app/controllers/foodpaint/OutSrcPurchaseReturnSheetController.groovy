package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class OutSrcPurchaseReturnSheetController{

    def domainService

    def index = {

        def list = OutSrcPurchaseReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [outSrcPurchaseReturnSheetInstanceList: list, outSrcPurchaseReturnSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def outSrcPurchaseReturnSheet=OutSrcPurchaseReturnSheet.get(params.id)

        if(outSrcPurchaseReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def outSrcPurchaseReturnSheet=new OutSrcPurchaseReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:outSrcPurchaseReturnSheet]
        }
    }

    def save = {
        def outSrcPurchaseReturnSheet=new OutSrcPurchaseReturnSheet(params)

        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseReturnSheet)
        }
    }


    def update = {

        def outSrcPurchaseReturnSheet= OutSrcPurchaseReturnSheet.get(params.id)
        if(outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets && params.supplier.id != outSrcPurchaseReturnSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseReturnSheet.outSrcPurchaseReturnSheetDets.exists.supplier.not.allowed.change', args: [outSrcPurchaseReturnSheet])]
            }
            return
        }
        outSrcPurchaseReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseReturnSheet)
        }         
    }



    def delete = {
        
        def outSrcPurchaseReturnSheet = OutSrcPurchaseReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(outSrcPurchaseReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [outSrcPurchaseReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
