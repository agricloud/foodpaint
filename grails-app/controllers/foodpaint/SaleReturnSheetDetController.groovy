package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class SaleReturnSheetDetController {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleReturnSheet = SaleReturnSheet.get(params.saleReturnSheet.id)

        if(saleReturnSheet){
            
            def saleReturnSheetDet = SaleReturnSheetDet.findAllBySaleReturnSheet(saleReturnSheet)

            render (contentType: 'application/json') {
               [success: true, data:saleReturnSheetDet, total: saleReturnSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }
        }
        
    }


    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleReturnSheetDet = SaleReturnSheetDet.get(params.id);


        if(saleReturnSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:saleReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }


    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

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
                [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.message.create.failed")]
            }            
        }

    }

    @Transactional
    def save(){

        def i18nType = grailsApplication.config.grails.i18nType

        def saleReturnSheetDet=new SaleReturnSheetDet(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(saleReturnSheetDet.typeName != saleReturnSheetDet.saleReturnSheet.typeName || saleReturnSheetDet.name != saleReturnSheetDet.saleReturnSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sheet.typeName.name.not.equal")]
            }
            return
        }

        if(saleReturnSheetDet.saleReturnSheet.customer!=saleReturnSheetDet.saleSheetDet.saleSheet.customer){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.saleReturnSheet.customer.saleSheetDet.saleSheet.customer.not.equal", args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.item != saleReturnSheetDet.saleSheetDet.item || saleReturnSheetDet.batch != saleReturnSheetDet.saleSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal", args:saleReturnSheetDet)]
            }
            return
        }

        if(saleReturnSheetDet.customerOrderDet != saleReturnSheetDet.saleSheetDet.customerOrderDet){
            render (contentType: 'application/json') {
                    [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.customerOrderDet.saleSheetDet.customerOrderDet.not.equal", args: [saleReturnSheetDet])]
            }
            return
        }

        def inventoryReplenishResult = inventoryDetailService.replenish(params,saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty, saleReturnSheetDet.dateCreated)
        
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

        def i18nType = grailsApplication.config.grails.i18nType

        def saleReturnSheetDet = new SaleReturnSheetDet(params)
        if(saleReturnSheetDet.saleReturnSheet.customer!=saleReturnSheetDet.saleSheetDet.saleSheet.customer){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.saleReturnSheetcustomer.saleSheetDet.saleSheet.customer.not.equal", args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [saleReturnSheetDet])]
            }
            return
        }

        if(saleReturnSheetDet.item != saleReturnSheetDet.saleSheetDet.item || saleReturnSheetDet.batch != saleReturnSheetDet.saleSheetDet.batch){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal", args:saleReturnSheetDet)]
            }
            return
        }

        if(saleReturnSheetDet.customerOrderDet != saleReturnSheetDet.saleSheetDet.customerOrderDet){
            render (contentType: 'application/json') {
                    [success: false,message:message(code: "${i18nType}.saleReturnSheetDet.customerOrderDet.saleSheetDet.customerOrderDet.not.equal", args: [saleReturnSheetDet])]
            }
            return
        }

        saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
        
        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != saleReturnSheetDet.typeName || params.name != saleReturnSheetDet.name|| params.sequence.toLong() != saleReturnSheetDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sequence.not.allowed.change")]
            }
            return
        }

        def inventoryConsumeResult=inventoryDetailService.consume(params,saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty,null)
        if(inventoryConsumeResult.success){    
            def updateBatch = Batch.get(params.batch.id)
            def inventoryReplenishResult = inventoryDetailService.replenish(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toDouble(),null)         
            if(inventoryReplenishResult.success){
                saleReturnSheetDet.properties = params
                render (contentType: 'application/json') {
                    domainService.save(saleReturnSheetDet)
                }
            }
            else{
                // (還原存貨)還原到扣除之前
                def inventoryRecoveryResult= inventoryDetailService.replenish(params,saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty,null)
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

        def i18nType = grailsApplication.config.grails.i18nType

        def saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(params,saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty,null)
           
            if(inventoryConsumeResult.success)
                result = domainService.delete(saleReturnSheetDet)
            else{
                inventoryConsumeResult
            }

        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [saleReturnSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
