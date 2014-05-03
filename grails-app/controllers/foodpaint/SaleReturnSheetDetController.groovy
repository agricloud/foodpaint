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
        if((!saleReturnSheetDet.customerOrderDet || saleReturnSheetDet.item == saleReturnSheetDet.customerOrderDet.item) && saleReturnSheetDet.batch == saleReturnSheetDet.saleSheetDet.batch &&saleReturnSheetDet.item==saleReturnSheetDet.saleSheetDet.item){
            if(saleReturnSheetDet.qty>0){
                 if(saleReturnSheetDet.price>0&&saleReturnSheetDet.tax>=0){ 
                    def  price  =   saleReturnSheetDet.price* saleReturnSheetDet.qty 
                    params.subamounts = price
                    params.totalAmount =price+saleReturnSheetDet.tax
                    def inventoryReplenishResult = inventoryDetailService.replenish(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
                        if(inventoryReplenishResult.success){
                            saleReturnSheetDet.properties = params
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
                    else{   
                    render (contentType: 'application/json') {
                        [success: false,message:message(code: 'sheet.price.must.more.than.zero', args: [saleReturnSheetDet])]
                    }
                }
            }
            else{
                render (contentType: 'application/json') {
                    [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleReturnSheetDet])]
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal', args:saleReturnSheetDet)]
            }
        }

    }

    @Transactional
    def update(){
        def  saleReturnSheetDet = new SaleReturnSheetDet(params)
        if((!saleReturnSheetDet.customerOrderDet || saleReturnSheetDet.item == saleReturnSheetDet.customerOrderDet.item) && saleReturnSheetDet.batch == saleReturnSheetDet.saleSheetDet.batch &&saleReturnSheetDet.item==saleReturnSheetDet.saleSheetDet.item){
            if(saleReturnSheetDet.qty>0){
                if(saleReturnSheetDet.price>0&&saleReturnSheetDet.tax>=0){                     
                        def  price  =   saleReturnSheetDet.price* saleReturnSheetDet.qty 
                        params.subamounts = price
                        params.totalAmount =price+saleReturnSheetDet.tax
                        saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
                        def inventoryConsumeResult=inventoryDetailService.consume(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
                        if(inventoryConsumeResult.success){          
                            def updateBatch = Batch.get(params.batch.id)
                            def inventoryReplenishResult = inventoryDetailService.replenish(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong())         
                            if(inventoryReplenishResult.success){
                                saleReturnSheetDet.properties = params
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
                        else{
                            render (contentType: 'application/json') {
                                inventoryConsumeResult   
                            }
                        }
                    }
                    else{   
                        render (contentType: 'application/json') {
                            [success: false,message:message(code: 'sheet.price.must.more.than.zero', args: [saleReturnSheetDet])]
                        }
                    }
            }
            else{
                render (contentType: 'application/json') {
                    [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleReturnSheetDet])]
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal', args:saleReturnSheetDet)]
            }
        }         
    }


    @Transactional
    def delete(){

        def saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
           
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
