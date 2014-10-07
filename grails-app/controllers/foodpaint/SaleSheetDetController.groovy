package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class SaleSheetDetController {

    def grailsApplication
    def domainService
    def batchService
    def inventoryDetailService

    def index = {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleSheet = SaleSheet.get(params.saleSheet.id)

        if(saleSheet){
            
            def saleSheetDet = SaleSheetDet.findAllBySaleSheet(saleSheet)

            render (contentType: 'application/json') {
               [success: true, data:saleSheetDet, total: saleSheetDet.size()]
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

        def saleSheetDet = SaleSheetDet.get(params.id);


        if(saleSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:saleSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }


    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

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
                [success: false,message:message(code: "${i18nType}.saleSheetDet.message.create.failed")]
            }            
        }

    }

    @Transactional
    def save() {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleSheetDet=new SaleSheetDet(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(saleSheetDet.typeName != saleSheetDet.saleSheet.typeName || saleSheetDet.name != saleSheetDet.saleSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sheet.typeName.name.not.equal")]
            }
            return
        }

        if(saleSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [saleSheetDet])]
            }
            return
        }

        //檢查銷貨單品項必須等於批號品項，若有選訂單單身，銷貨單品項必須等於訂單單身品項
        if((saleSheetDet.customerOrderDet && saleSheetDet.item != saleSheetDet.customerOrderDet.item) || saleSheetDet.item != saleSheetDet.batch.item ){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.item.batch.item.not.equal", args:saleSheetDet.customerOrderDet)]
            }
            return
        }

        def inventoryConsumeResult = inventoryDetailService.consume(params,saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty, saleSheetDet.dateCreated)
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


    @Transactional
    def update() {

        def i18nType = grailsApplication.config.grails.i18nType

        def saleSheetDet = new SaleSheetDet(params)

        if(saleSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: "${i18nType}.sheet.qty.must.more.than.zero", args: [saleSheetDet])]
            }
            return
        }

        //檢查銷貨單品項必須等於批號品項，若有選訂單單身，銷貨單品項必須等於訂單單身品項
        if((saleSheetDet.customerOrderDet && saleSheetDet.item != saleSheetDet.customerOrderDet.item) || saleSheetDet.item != saleSheetDet.batch.item ){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheet.item.batch.item.not.equal", args:saleSheetDet.customerOrderDet)]
            }
            return
        }


        saleSheetDet = SaleSheetDet.get(params.id)

        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != saleSheetDet.typeName || params.name != saleSheetDet.name|| params.sequence.toLong() != saleSheetDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.sheetDetail.typeName.name.sequence.not.allowed.change")]
            }
            return
        }

        //把更新前已銷的數量加回庫存
        def inventoryReplenishResult = inventoryDetailService.replenish(params,saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty,null)
        if(inventoryReplenishResult.success){
            //把欲更新的銷貨數量扣掉庫存
            def updateBatch = Batch.get(params.batch.id)
            def inventoryConsumeResult = inventoryDetailService.consume(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toDouble(),null)
            if(inventoryConsumeResult.success){
                saleSheetDet.properties = params
                render (contentType: 'application/json') {
                    domainService.save(saleSheetDet)
                }
            }
            else{
                //把更新前已銷的數量再扣掉庫存 還原更新前狀態
                def inventoryRecoveryResult = inventoryDetailService.consume(params,saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty,null)
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
                inventoryReplenishResult
            }
        }
        
    }


    @Transactional
    def delete(){

        def i18nType = grailsApplication.config.grails.i18nType

        def saleSheetDet = SaleSheetDet.get(params.id)

        def result
        try {
            inventoryDetailService.replenish(params,saleSheetDet.warehouse.id,saleSheetDet.warehouseLocation.id, saleSheetDet.item.id, saleSheetDet.batch.name, saleSheetDet.qty,null)
            result = domainService.delete(saleSheetDet)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [saleSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
