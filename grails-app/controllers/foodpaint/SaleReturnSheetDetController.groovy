package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

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
    //PS save、update、delete需加transaction處理
    @Transactional
    def save = {
        def saleReturnSheetDet=new SaleReturnSheetDet(params)
        if((!saleReturnSheetDet.customerOrderDet || saleReturnSheetDet.item == saleReturnSheetDet.customerOrderDet.item) && saleReturnSheetDet.batch == saleReturnSheetDet.saleSheetDet.batch &&saleReturnSheetDet.item==saleReturnSheetDet.saleSheetDet.item){
            if(saleReturnSheetDet.qty>0){
                def inventoryReplenishResult  = inventoryDetailService.replenish(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
                     render (contentType: 'application/json') {
                        domainService.save(saleReturnSheetDet)
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
                [success: false,message:message(code: 'sheet.item.batch.item.not.equal', args:saleReturnSheetDet.customerOrderDet)]
            }
        }

    }

    @Transactional
    def update = {
        def  saleReturnSheetDet = new SaleReturnSheetDet(params)
        if((!saleReturnSheetDet.customerOrderDet || saleReturnSheetDet.item == saleReturnSheetDet.customerOrderDet.item) && saleReturnSheetDet.batch == saleReturnSheetDet.saleSheetDet.batch &&saleReturnSheetDet.item==saleReturnSheetDet.saleSheetDet.item){
            if(saleReturnSheetDet.qty>0){
                saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
                def inventoryConsumeResult=inventoryDetailService.consume(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
                if(inventoryConsumeResult.success){          
                    def updateBatch = Batch.get(params.batch.id)
                    inventoryDetailService.replenish(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong())         
                    saleReturnSheetDet.properties = params
                    render (contentType: 'application/json') {
                        domainService.save(saleReturnSheetDet)
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
                    [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleReturnSheetDet])]
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheet.item.batch.item.not.equal', args:saleReturnSheetDet.customerOrderDet)]
            }
        }         
    }


    @Transactional
    def delete = {

        def  saleReturnSheetDet = SaleReturnSheetDet.get(params.id)

        def result
        try {
            inventoryDetailService.replenish(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
            result = domainService.delete(saleReturnSheetDet)
        
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
