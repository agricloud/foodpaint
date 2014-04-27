package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class AccountSheetDetController {

    def domainService

    def index = {
        def accountSheet = AccountSheet.get(params.accountSheet.id)

        if(accountSheet){
            
            def accountSheetDet = AccountSheetDet.findAllByAccountSheet(accountSheet)

            render (contentType: 'application/json') {
               [success: true, data:accountSheetDet, total: accountSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def accountSheetDet = AccountSheetDet.get(params.id);


        if(accountSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:accountSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.accountSheet.id){

            def accountSheetDet= new AccountSheetDet(params)

            accountSheetDet.typeName = accountSheetDet.accountSheet.typeName
            accountSheetDet.name = accountSheetDet.accountSheet.name

            if(accountSheetDet.accountSheet.accountSheetDets)
                accountSheetDet.sequence = accountSheetDet.accountSheet.accountSheetDets*.sequence.max()+1
            else accountSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:accountSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'accountSheetDet.message.create.failed')]
            }            
        }

    }

    // @Transactional
    // def save(){
    //     def accountSheetDet=new AccountSheetDet(params)
    //     if((!accountSheetDet.saleReturnSheetDet || !accountSheetDet.saleSheetDet)&&(accountSheetDet.saleSheetDet.name==saleSheetDet.name||accountSheetDet.saleReturnSheetDet.name==saleReturnSheetDet.name)){  //判斷結帳單的銷貨單銷退單是否存在
           




    //         // if(saleSheet.totalAmount-saleReturnSheet.totalAmount == accountSheetDet.saleSheetDet.batch &&saleReturnSheetDet.item==saleReturnSheetDet.saleSheetDet.item){
    //         // }
    //     }
    //     else{
    //                 render (contentType: 'application/json') {
    //                     [success: false,message:message(code: 'accountSheetDet.saleSheetDetORsaleReturnSheetDet.itemOrBatch.not.equal', args:accountSheetDet)]
    //                 }
    //             }
    // }
    // @Transactional
    // def update(){
    //     def  saleReturnSheetDet = new SaleReturnSheetDet(params)
    //     if((!saleReturnSheetDet.customerOrderDet || saleReturnSheetDet.item == saleReturnSheetDet.customerOrderDet.item) && saleReturnSheetDet.batch == saleReturnSheetDet.saleSheetDet.batch &&saleReturnSheetDet.item==saleReturnSheetDet.saleSheetDet.item){
    //         if(saleReturnSheetDet.qty>0){
    //             saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
    //             def inventoryConsumeResult=inventoryDetailService.consume(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
    //             if(inventoryConsumeResult.success){          
    //                 def updateBatch = Batch.get(params.batch.id)
    //                 def inventoryReplenishResult = inventoryDetailService.replenish(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong())         
    //                 if(inventoryReplenishResult.success){
    //                     saleReturnSheetDet.properties = params
    //                     render (contentType: 'application/json') {
    //                         domainService.save(saleReturnSheetDet)
    //                     }
    //                 }
    //                 else{
    //                     render (contentType: 'application/json') {
    //                         inventoryReplenishResult
    //                     }
    //                 }
    //             }
    //             else{
    //                 render (contentType: 'application/json') {
    //                     inventoryConsumeResult   
    //                 }
    //             }
    //         }
    //         else{
    //             render (contentType: 'application/json') {
    //                 [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [saleReturnSheetDet])]
    //             }
    //         }
    //     }
    //     else{
    //         render (contentType: 'application/json') {
    //             [success: false,message:message(code: 'saleReturnSheetDet.itemOrBatch.saleSheetDet.itemOrBatch.not.equal', args:saleReturnSheetDet)]
    //         }
    //     }         
    // }


    // @Transactional
    // def delete(){

    //     def saleReturnSheetDet = SaleReturnSheetDet.get(params.id)
    //     def result
    //     try {
    //         def inventoryConsumeResult = inventoryDetailService.consume(saleReturnSheetDet.warehouse.id,saleReturnSheetDet.warehouseLocation.id, saleReturnSheetDet.item.id, saleReturnSheetDet.batch.name, saleReturnSheetDet.qty)
           
    //         if(inventoryConsumeResult.success)
    //             result = domainService.delete(saleReturnSheetDet)
    //         else{
    //             inventoryConsumeResult
    //         }

    //     }catch(e){
    //         log.error e
    //         def msg = message(code: 'default.message.delete.failed', args: [saleReturnSheetDet, e.getMessage()])
    //         result = [success:false, message: msg] 
    //     }
        
    //     render (contentType: 'application/json') {
    //         result
    //     }
    // }
}
