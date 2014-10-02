package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class MaterialReturnSheetDetController {

    def domainService
    def inventoryDetailService

    def index = {

        def materialReturnSheet = MaterialReturnSheet.get(params.materialReturnSheet.id)

        if(materialReturnSheet){
            
            def materialReturnSheetDet = MaterialReturnSheetDet.findAllByMaterialReturnSheet(materialReturnSheet)

            render (contentType: 'application/json') {
               [success: true, data:materialReturnSheetDet, total: materialReturnSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def materialReturnSheetDet=MaterialReturnSheetDet.get(params.id);


        if(materialReturnSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:materialReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.materialReturnSheet.id){

            def materialReturnSheetDet= new MaterialReturnSheetDet(params)

            materialReturnSheetDet.typeName = materialReturnSheetDet.materialReturnSheet.typeName
            materialReturnSheetDet.name = materialReturnSheetDet.materialReturnSheet.name

            if(materialReturnSheetDet.materialReturnSheet.materialReturnSheetDets)
                materialReturnSheetDet.sequence = materialReturnSheetDet.materialReturnSheet.materialReturnSheetDets*.sequence.max()+1
            else materialReturnSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:materialReturnSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){

        def materialReturnSheetDet = new MaterialReturnSheetDet(params)

        //「單身單別、單號」與「單頭單別、單號」不同不允許儲存
        if(materialReturnSheetDet.typeName != materialReturnSheetDet.materialReturnSheet.typeName || materialReturnSheetDet.name != materialReturnSheetDet.materialReturnSheet.name){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheetDetail.typeName.name.sheet.typeName.name.not.equal')]
            }
            return
        }

        if(materialReturnSheetDet.materialReturnSheet.workstation!=materialReturnSheetDet.materialSheetDet.materialSheet.workstation || materialReturnSheetDet.materialReturnSheet.supplier!=materialReturnSheetDet.materialSheetDet.materialSheet.supplier){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheetDet.materialReturnSheet.workstationOrSupplier.materialSheetDet.materialSheet.workstationOrSupplier.not.equal', args: [materialReturnSheetDet])]
            }
            return
        }

        if(materialReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [materialReturnSheetDet])]
            }
            return
        }

        if(materialReturnSheetDet.item != materialReturnSheetDet.materialSheetDet.item || materialReturnSheetDet.batch != materialReturnSheetDet.materialSheetDet.batch){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialReturnSheetDet.itemOrBatch.materialSheetDet.itemOrBatch.not.equal', args: [materialReturnSheetDet, materialReturnSheetDet.manufactureOrder])]
            }
            return
        }

        if(materialReturnSheetDet.manufactureOrder != materialReturnSheetDet.materialSheetDet.manufactureOrder){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheetDet.manufactureOrder.materialSheetDet.manufactureOrder.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }

        def inventoryReplenishResult = inventoryDetailService.replenish(params,materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty, materialReturnSheetDet.dateCreated)
        if(inventoryReplenishResult.success){
            render (contentType: 'application/json') {
                domainService.save(materialReturnSheetDet)
            }
        }
        else{
            render (contentType: 'application/json') {
                inventoryReplenishResult
            }
        }
        
    }


    @Transactional
    def update() {

        def materialReturnSheetDet = new MaterialReturnSheetDet(params)

        

        if(materialReturnSheetDet.materialReturnSheet.workstation!=materialReturnSheetDet.materialSheetDet.materialSheet.workstation || materialReturnSheetDet.materialReturnSheet.supplier!=materialReturnSheetDet.materialSheetDet.materialSheet.supplier){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheetDet.materialReturnSheet.workstationOrSupplier.materialSheetDet.materialSheet.workstationOrSupplier.not.equal', args: [materialReturnSheetDet])]
            }
            return
        }

        if(materialReturnSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [materialReturnSheetDet])]
            }
            return
        }

        if(materialReturnSheetDet.item != materialReturnSheetDet.materialSheetDet.item || materialReturnSheetDet.batch != materialReturnSheetDet.materialSheetDet.batch){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialReturnSheetDet.itemOrBatch.materialSheetDet.itemOrBatch.not.equal', args: [materialReturnSheetDet, materialReturnSheetDet.manufactureOrder])]
            }
            return
        }

        if(materialReturnSheetDet.manufactureOrder != materialReturnSheetDet.materialSheetDet.manufactureOrder){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheetDet.manufactureOrder.materialSheetDet.manufactureOrder.not.equal', args: [outSrcPurchaseReturnSheetDet])]
            }
            return
        }

        materialReturnSheetDet = MaterialReturnSheetDet.get(params.id)

        //單別、單號、序號一旦建立不允許變更
        if(params.typeName != materialReturnSheetDet.typeName || params.name != materialReturnSheetDet.name|| params.sequence.toLong() != materialReturnSheetDet.sequence){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheetDetail.typeName.name.sequence.not.allowed.change')]
            }
            return
        }
        
        def inventoryConsumeResult = inventoryDetailService.consume(params,materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty,null)
        
        if(inventoryConsumeResult.success){
            def updateBatch = Batch.get(params.batch.id)
            def inventoryReplenishResult = inventoryDetailService.replenish(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toDouble(),null)
            if(inventoryReplenishResult.success){
                materialReturnSheetDet.properties = params             
                render (contentType: 'application/json') {
                    domainService.save(materialReturnSheetDet)
                }
            }
            else{
                //把數量還原更新前狀態
                def inventoryRecoveryResult= inventoryDetailService.replenish(params,materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty,null)
                if(inventoryRecoveryResult.success){
                    render (contentType: 'application/json') {
                        inventoryReplenishResult
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

        def materialReturnSheetDet = MaterialReturnSheetDet.get(params.id)

        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(params,materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty,null)
            
            if(inventoryConsumeResult.success)
                result = domainService.delete(materialReturnSheetDet)
            else
                return inventoryConsumeResult
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [materialReturnSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
