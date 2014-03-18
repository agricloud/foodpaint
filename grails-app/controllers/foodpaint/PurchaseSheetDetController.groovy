package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PurchaseSheetDetController {

    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def purchaseSheet = PurchaseSheet.get(params.purchaseSheet.id)

        if(purchaseSheet){
            
            def purchaseSheetDet = PurchaseSheetDet.findAllByPurchaseSheet(purchaseSheet)

            render (contentType: 'application/json') {
               [success: true, data:purchaseSheetDet, total: purchaseSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def purchaseSheetDet = PurchaseSheetDet.get(params.id);


        if(purchaseSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.purchaseSheet.id){

            def purchaseSheetDet= new PurchaseSheetDet(params)

            purchaseSheetDet.typeName = purchaseSheetDet.purchaseSheet.typeName
            purchaseSheetDet.name = purchaseSheetDet.purchaseSheet.name

            if(purchaseSheetDet.purchaseSheet.purchaseSheetDets)
                purchaseSheetDet.sequence = purchaseSheetDet.purchaseSheet.purchaseSheetDets*.sequence.max()+1
            else purchaseSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:purchaseSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def purchaseSheetDet=new PurchaseSheetDet(params)
        if(purchaseSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, purchaseSheetDet)
            if(!result.success){
                render (contentType: 'application/json') {
                    result
                }
            }
            else{
                inventoryDetailService.replenish(params.warehouse.id, params.item.id, params.batch.name, purchaseSheetDet.qty)
                purchaseSheetDet.batch = (Batch) result.batch
                render (contentType: 'application/json') {
                    domainService.save(purchaseSheetDet)
                }
            }
        } 
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseSheetDet])]
            }
        } 
    }

    @Transactional
    def update() {
        def purchaseSheetDet = new PurchaseSheetDet(params)
        if(purchaseSheetDet.qty>0){
            def result = batchService.findOrCreateBatchInstanceByJson(params, purchaseSheetDet)
            if(!result.success){
                render (contentType: 'application/json') {
                    result
                }
            }
            else{
                purchaseSheetDet = PurchaseSheetDet.get(params.id)
                if(!inventoryDetailService.consume(purchaseSheetDet.warehouse.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty).success){
                    render (contentType: 'application/json') {
                        [success:false, message:message(code: 'inventoryDetail.had.been.used', args: [purchaseSheetDet.warehouse, purchaseSheetDet.item, purchaseSheetDet.batch])]
                    }
                }
                else{
                    purchaseSheetDet.properties = params
                    inventoryDetailService.replenish(params.warehouse.id, params.item.id, params.batch.name, purchaseSheetDet.qty)
                    purchaseSheetDet.batch = (Batch) result.batch
                    render (contentType: 'application/json') {
                        domainService.save(purchaseSheetDet)
                    }
                }
            }
        } 
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseSheetDet])]
            }
        }  
    }



    @Transactional
    def delete(){
        def  purchaseSheetDet = PurchaseSheetDet.get(params.id)

        if(!inventoryDetailService.consume(purchaseSheetDet.warehouse.id, purchaseSheetDet.item.id, purchaseSheetDet.batch.name, purchaseSheetDet.qty).success){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'inventoryDetail.had.been.used', args: [purchaseSheetDet.warehouse, purchaseSheetDet.item, purchaseSheetDet.batch])]
            }
        }
        else{

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
}