package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class OutSrcPurchaseSheetController {

    def domainService

    def index = {

        def list = OutSrcPurchaseSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [outSrcPurchaseSheetInstanceList: list, outSrcPurchaseSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def outSrcPurchaseSheet=OutSrcPurchaseSheet.get(params.id)

        if(outSrcPurchaseSheet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def outSrcPurchaseSheet=new OutSrcPurchaseSheet() 

        render (contentType: 'application/json') {
            [success: true,data:outSrcPurchaseSheet]
        }
    }

    def save = {
        def outSrcPurchaseSheet=new OutSrcPurchaseSheet(params)

        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseSheet)
        }
    }


    def update = {

        def outSrcPurchaseSheet= OutSrcPurchaseSheet.get(params.id)
        if(outSrcPurchaseSheet.outSrcPurchaseSheetDets && params.supplier.id != outSrcPurchaseSheet.supplier.id){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'outSrcPurchaseSheet.outSrcPurchaseSheetDets.exists.supplier.not.allowed.change', args: [outSrcPurchaseSheet])]
            }
            return
        }
        outSrcPurchaseSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseSheet)
        }         
    }



    def delete = {
        
        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.id)

        def result
        try {
            result = domainService.delete(outSrcPurchaseSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [outSrcPurchaseSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
