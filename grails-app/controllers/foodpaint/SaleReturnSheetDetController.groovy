package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class SaleReturnSheetDetController {

    def domainService
    def batchService
    def inventoryDetailService

    def index = {
        def saleReturnSheet = SaleReturnSheet.get(params.saleReturnSheet.id)

        if(saleReturnSheet){
            
            def saleReturnSheetDet = SaleReturnSheetDet.findAllBySaleReturnSheet(saleReturnSheet)

            render (contentType: 'application/json') {
               [success: true, data:saleReturnSheetDet, total: saleReturnSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def saleReturnSheetDet = SaleReturnSheetDet.get(params.id);


        if(saleReturnSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:saleReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.saleReturnSheet.id){

            def saleReturnSheetDet= new SaleReturnSheetDet(params)

            saleReturnSheetDet.typeName = saleReturnSheetDet.saleReturnSheet.typeName
            saleReturnSheetDet.name = saleReturnSheetDet.saleReturnSheet.name

            if(saleReturnSheetDet.saleReturnSheet.saleReturnSheetDets)
                saleReturnSheetDet.sequence = saleReturnSheetDet.saleReturnSheet.saleReturnSheetDets*.sequence.max()+1
            else saleReturnSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:saleReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'saleReturnSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){
        def saleReturnSheetDet=new SaleReturnSheetDet(params)
        if(saleReturnSheetDet.saleReturnSheet.customer!=saleReturnSheetDet.saleSheetDet.saleSheet.customer){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'saleReturnSheetDet.saleReturnSheet.customer.saleSheetDet.saleSheet.customer.not.equal', args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.item != saleReturnSheetDet.saleSheetDet.item || saleReturnSheetDet.batch != saleReturnSheetDet.saleSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal', args:saleReturnSheetDet)]
            }
            return
        }

        if(saleReturnSheetDet.customerOrderDet != saleReturnSheetDet.saleSheetDet.customerOrderDet){
            render (contentType: 'application/json') {
                    [success: false,message:message(code: 'saleReturnSheetDet.customerOrderDet.saleSheetDet.customerOrderDet.not.equal', args: [saleReturnSheetDet])]
            }
            return
        }

        def inventoryReplenishResult = inventoryDetailService.replenish(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty, saleReturnSheetDet.dateCreated)
        
        if(inventoryReplenishResult.success){
            render (contentType: 'application/json') {
                domainService.save(saleReturnSheetDet)
            }
        }
        else{
            render (contentType: 'application/json') {
                inventoryReplenishResult
            } 
        }
    }

    @Transactional
    def update(){
        def  saleReturnSheetDet = new SaleReturnSheetDet(params)
        if(saleReturnSheetDet.saleReturnSheet.customer!=saleReturnSheetDet.saleSheetDet.saleSheet.customer){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'osaleReturnSheetDet.saleReturnSheet.supplier.saleSheetDet.saleSheet.customer.not.equal', args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.item != saleReturnSheetDet.saleSheetDet.item || saleReturnSheetDet.batch != saleReturnSheetDet.saleSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal', args:saleReturnSheetDet)]
            }
            return
        }

        if(saleReturnSheetDet.customerOrderDet != saleReturnSheetDet.saleSheetDet.customerOrderDet){
            render (contentType: 'application/json') {
                    [success: false,message:message(code: 'saleReturnSheetDet.customerOrderDet.saleSheetDet.customerOrderDet.not.equal', args: [saleReturnSheetDet])]
            }
            return
        }

        saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
        def inventoryConsumeResult=inventoryDetailService.consume(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty,null)
        if(inventoryConsumeResult.success){    
            def updateBatch = Batch.get(params.batch.id)
            def inventoryReplenishResult = inventoryDetailService.replenish(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong(),null)         
            if(inventoryReplenishResult.success){
                saleReturnSheetDet.properties = params
                render (contentType: 'application/json') {
                    domainService.save(saleReturnSheetDet)
                }
            }
            else{
                // (還原存貨)還原到扣除之前
                def inventoryRecoveryResult= inventoryDetailService.replenish(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty,null)
                if(inventoryRecoveryResult.success){
                    render (contentType: 'application/json') {
                        inventoryConsumeResult
                    }
                }
                else{
                    throw new Exception("還原庫存失敗:"+inventoryRecoveryResult.message)
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                inventoryConsumeResult   
            }
        }   
    }


    @Transactional
    def delete(){

        def saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty,null)
           
            if(inventoryConsumeResult.success)
                result = domainService.delete(saleReturnSheetDet)
            else{
                inventoryConsumeResult
            }

        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [saleReturnSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
