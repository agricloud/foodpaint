package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class SaleSheetDetController {

    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def saleSheet = SaleSheet.get(params.saleSheet.id)

        if(saleSheet){
            
            def saleSheetDet = SaleSheetDet.findAllBySaleSheet(saleSheet)

            render (contentType: 'application/json') {
               [success: true, data:saleSheetDet, total: saleSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def saleSheetDet = SaleSheetDet.get(params.id);


        if(saleSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:saleSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.saleSheet.id){

            def saleSheetDet= new SaleSheetDet(params)

            saleSheetDet.typeName = saleSheetDet.saleSheet.typeName
            saleSheetDet.name = saleSheetDet.saleSheet.name

            if(saleSheetDet.saleSheet.saleSheetDets)
                saleSheetDet.sequence = saleSheetDet.saleSheet.saleSheetDets*.sequence.max()+1
            else saleSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:saleSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'saleSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save() {
        def saleSheetDet=new SaleSheetDet(params)

        //檢查銷貨單品項必須等於批號品項，若有選訂單單身，銷貨單品項必須等於訂單單身品項
        if((!saleSheetDet.customerOrderDet || saleSheetDet.item == saleSheetDet.customerOrderDet.item) && saleSheetDet.item == saleSheetDet.batch.item ){
            if(saleSheetDet.qty>0){
                def inventoryConsumeResult = inventoryDetailService.consume(saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty)
                if(inventoryConsumeResult.success){
                    render (contentType: 'application/json') {
                        domainService.save(saleSheetDet)
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
                    [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleSheetDet])]
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheet.item.batch.item.not.equal', args:saleSheetDet.customerOrderDet)]
            }
        }

    }


    @Transactional
    def update() {
        def  saleSheetDet = new SaleSheetDet(params)
        //檢查銷貨單品項必須等於批號品項，若有選訂單單身，銷貨單品項必須等於訂單單身品項
        if((!saleSheetDet.customerOrderDet || saleSheetDet.item == saleSheetDet.customerOrderDet.item) && saleSheetDet.item == saleSheetDet.batch.item ){
            if(saleSheetDet.qty>0){
                saleSheetDet = SaleSheetDet.get(params.id)
                inventoryDetailService.replenish(saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty)
                def updateBatch = Batch.get(params.batch.id)
                def inventoryConsumeResult = inventoryDetailService.consume(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong())
                if(inventoryConsumeResult.success){
                    saleSheetDet.properties = params
                    render (contentType: 'application/json') {
                        domainService.save(saleSheetDet)
                    }
                }
                else{
                    saleSheetDet = SaleSheetDet.get(params.id)
                    inventoryDetailService.consume(saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty)
                    render (contentType: 'application/json') {
                        inventoryConsumeResult
                    }
                }
            }
            else{
                render (contentType: 'application/json') {
                    [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleSheetDet])]
                }
            }
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheet.item.batch.item.not.equal', args:saleSheetDet.customerOrderDet)]
            }
        }         
    }


    @Transactional
    def delete(){

        def  saleSheetDet = SaleSheetDet.get(params.id)

        def result
        try {
            inventoryDetailService.replenish(saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty)
            result = domainService.delete(saleSheetDet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [saleSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
