package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PurchaseReturnSheetDetController {

    def domainService
    def batchService  
    def inventoryDetailService   //庫存處理

    def index = {

        def purchaseReturnSheet = PurchaseReturnSheet.get(params.purchaseReturnSheet.id)

        if(purchaseReturnSheet){
            
            def purchaseReturnSheetDet = PurchaseReturnSheetDet.findAllByPurchaseReturnSheet(purchaseReturnSheet)

            render (contentType: 'application/json') {
               [success: true, data:purchaseReturnSheetDet, total: purchaseReturnSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def purchaseReturnSheetDet = PurchaseReturnSheetDet.get(params.id);


        if(purchaseReturnSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:purchaseReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.purchaseReturnSheet.id){

            def purchaseReturnSheetDet= new PurchaseReturnSheetDet(params)

            purchaseReturnSheetDet.typeName = purchaseReturnSheetDet.purchaseReturnSheet.typeName
            purchaseReturnSheetDet.name = purchaseReturnSheetDet.purchaseReturnSheet.name

            if(purchaseReturnSheetDet.purchaseReturnSheet.purchaseReturnSheetDets)
                purchaseReturnSheetDet.sequence = purchaseReturnSheetDet.purchaseReturnSheet.purchaseReturnSheetDets*.sequence.max()+1
            else purchaseReturnSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:purchaseReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'purchaseReturnSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def purchaseReturnSheetDet=new PurchaseReturnSheetDet(params)
        if(purchaseReturnSheetDet.qty>0){
                purchaseReturnSheetDet.properties = params
                inventoryDetailService.consume(params.warehouse.id,params.warehouseLocation.id, params.item.id, params.batch.name, purchaseReturnSheetDet.qty)
                render (contentType: 'application/json') { 
                    domainService.save(purchaseReturnSheetDet)
                }
        } 
        else{
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseReturnSheetDet])]
            }
        } 
    }

    @Transactional
    def update() {
        def purchaseReturnSheetDet = new PurchaseReturnSheetDet(params)
         if((purchaseReturnSheetDet.purchaseSheetDet || purchaseReturnSheetDet.item == purchaseReturnSheetDet.purchaseSheetDet.item) && purchaseReturnSheetDet.item == purchaseReturnSheetDet.purchaseSheetDet.batch.item ){
                if(purchaseReturnSheetDet.qty>0){
                        purchaseReturnSheetDet = PurchaseReturnSheetDet.get(params.id)
                        if(!inventoryDetailService.replenish(purchaseReturnSheetDet.warehouse.id,purchaseReturnSheetDet.warehouseLocation.id, purchaseReturnSheetDet.item.id, purchaseReturnSheetDet.batch.name, purchaseReturnSheetDet.qty).success){
                            render (contentType: 'application/json') {
                                [success:false, message:message(code: 'inventoryDetail.had.been.used', args: [purchaseReturnSheetDet.warehouse, purchaseReturnSheetDet.item, purchaseReturnSheetDet.batch])]
                            }
                        }
                        else{
                            purchaseReturnSheetDet.properties = params
                            inventoryDetailService.consume(params.warehouse.id,params.warehouseLocation.id, params.item.id, params.batch.name, purchaseReturnSheetDet.qty)
                            render (contentType: 'application/json') {
                                domainService.save(purchaseReturnSheetDet)
                            }
                        }
                    }
                else{
                    render (contentType: 'application/json') {
                        [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [purchaseReturnSheetDet])]
                    }
                }
             }  
        else{
                render (contentType: 'application/json') {
                    [success: false,message:message(code: 'sheet.item.batch.item.not.equal', args: [purchaseReturnSheetDet])]
                }
            }
           

    }



    @Transactional
    def delete(){
        def  purchaseReturnSheetDet = PurchaseReturnSheetDet.get(params.id)

        if(!inventoryDetailService.replenish(purchaseReturnSheetDet.warehouse.id,purchaseReturnSheetDet.warehouseLocation.id, purchaseReturnSheetDet.item.id, purchaseReturnSheetDet.batch.name, purchaseReturnSheetDet.qty).success){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'inventoryDetail.had.been.used', args: [purchaseReturnSheetDet.warehouse, purchaseReturnSheetDet.item, purchaseReturnSheetDet.batch])]
            }
        }
        else{

            def result
            try {
                
                result = domainService.delete(purchaseReturnSheetDet)
            
            }catch(e){
                log.error e
                def msg = message(code: 'default.message.delete.failed', args: [purchaseReturnSheetDet, e.getMessage()])
                result = [success:false, message: msg] 
            }
            
            render (contentType: 'application/json') {
                result
            }
        }
    }
}
