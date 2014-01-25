package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class OutSrcPurchaseSheetDetController {

    def domainService
    def batchService

    def index = {

        def outSrcPurchaseSheet = OutSrcPurchaseSheet.get(params.outSrcPurchaseSheet.id)

        if(outSrcPurchaseSheet){
            
            def outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.findAllByOutSrcPurchaseSheet(outSrcPurchaseSheet)

            render (contentType: 'application/json') {
               [success: true, data:outSrcPurchaseSheetDet, total: outSrcPurchaseSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id);


        if(outSrcPurchaseSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:outSrcPurchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

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
                [success: false,message:message(code: 'outSrcPurchaseSheetDet.message.create.failed')]
            }            
        }

    }

    def save = {
        def outSrcPurchaseSheetDet=new OutSrcPurchaseSheetDet(params)
        def result = batchService.findOrCreateBatchInstanceByJson(params, outSrcPurchaseSheetDet)
        if(!result.success){
            render (contentType: 'application/json') {
                result
            }
        }
        else{
            outSrcPurchaseSheetDet.batch = (Batch) result.batch
            render (contentType: 'application/json') {
                domainService.save(outSrcPurchaseSheetDet)
            }
        }
    }


    def update = {

        def  outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id)
        outSrcPurchaseSheetDet.properties = params
        render (contentType: 'application/json') {
            domainService.save(outSrcPurchaseSheetDet)
        }         
    }



    def delete = {

        def  outSrcPurchaseSheetDet = OutSrcPurchaseSheetDet.get(params.id)

        def result
        try {
            
            result = domainService.delete(outSrcPurchaseSheetDet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [outSrcPurchaseSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
