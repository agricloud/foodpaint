package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.transaction.Transactional

class MaterialSheetDetController {

    def domainService
    def inventoryDetailService

    def index = {

        def materialSheet = MaterialSheet.get(params.materialSheet.id)

        if(materialSheet){
            
            def materialSheetDet = MaterialSheetDet.findAllByMaterialSheet(materialSheet)

            render (contentType: 'application/json') {
               [success: true, data:materialSheetDet, total: materialSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def materialSheetDet=MaterialSheetDet.get(params.id);


        if(materialSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:materialSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

        if(params.materialSheet.id){

            def materialSheetDet= new MaterialSheetDet(params)

            materialSheetDet.typeName = materialSheetDet.materialSheet.typeName
            materialSheetDet.name = materialSheetDet.materialSheet.name

            if(materialSheetDet.materialSheet.materialSheetDets)
                materialSheetDet.sequence = materialSheetDet.materialSheet.materialSheetDets*.sequence.max()+1
            else materialSheetDet.sequence = 1

            render (contentType: 'application/json') {
                [success: true,data:materialSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialSheetDet.message.create.failed')]
            }            
        }

    }

    @Transactional
    def save(){

        def materialSheetDet = new MaterialSheetDet(params)

        if(materialSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [materialSheetDet])]
            }
            return
        }
        //如果領料單頭工作站、供應商與製令工作站、供應商不相同 不允許儲存
        if(materialSheetDet.materialSheet.workstation != materialSheetDet.manufactureOrder.workstation || materialSheetDet.materialSheet.supplier != materialSheetDet.manufactureOrder.supplier){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialSheetDet.materialSheet.workstationOrSupplier.manufactureOrder.workstationOrSupplier.not.equal', args: [materialSheetDet, materialSheetDet.manufactureOrder])]
            }
            return
        }
        //如果領料品項與製令品項相同 不允許更新領料單身
        if(materialSheetDet.item == materialSheetDet.manufactureOrder.item){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialSheetDet.materialSheet.item.manufactureOrder.item.equal', args: [materialSheetDet, materialSheetDet.manufactureOrder])]
            }
            return
        }

        def inventoryConsumeResult = inventoryDetailService.consume(params,materialSheetDet.warehouse.id,materialSheetDet.warehouseLocation.id, materialSheetDet.item.id, materialSheetDet.batch.name, materialSheetDet.qty, materialSheetDet.dateCreated)
        if(inventoryConsumeResult.success){
            render (contentType: 'application/json') {
                domainService.save(materialSheetDet)
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

        def materialSheetDet = new MaterialSheetDet(params)
        
        if(materialSheetDet.qty<=0){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [materialSheetDet])]
            }
            return
        }
        //如果領料單頭工作站、供應商與製令工作站、供應商不相同 不允許儲存
        if(materialSheetDet.materialSheet.workstation != materialSheetDet.manufactureOrder.workstation || materialSheetDet.materialSheet.supplier != materialSheetDet.manufactureOrder.supplier){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialSheetDet.workstationOrSupplier.manufactureOrder.workstationOrSupplier.not.equal', args: [materialSheetDet, materialSheetDet.manufactureOrder])]
            }
            return
        }
        //如果領料品項與製令品項相同 不允許更新領料單身
        if(materialSheetDet.item == materialSheetDet.manufactureOrder.item){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialSheetDet.item.manufactureOrder.item.equal', args: [materialSheetDet, materialSheetDet.manufactureOrder])]
            }
            return
        }

        materialSheetDet = MaterialSheetDet.get(params.id)
        //把更新前已領的數量加回庫存
        def inventoryReplenishResult = inventoryDetailService.replenish(params,materialSheetDet.warehouse.id,materialSheetDet.warehouseLocation.id, materialSheetDet.item.id, materialSheetDet.batch.name, materialSheetDet.qty, null)
        
        if(inventoryReplenishResult.success){
            //把欲更新的領料數量扣掉庫存
            def updateBatch = Batch.get(params.batch.id)
            def inventoryConsumeResult = inventoryDetailService.consume(params,params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong(), null)
            if(inventoryConsumeResult.success){
                materialSheetDet.properties = params             
                render (contentType: 'application/json') {
                    domainService.save(materialSheetDet)
                }
            }
            else{
                //把更新前已領的數量再扣掉庫存 還原更新前狀態
                def inventoryRecoveryResult= inventoryDetailService.consume(params,materialSheetDet.warehouse.id,materialSheetDet.warehouseLocation.id, materialSheetDet.item.id, materialSheetDet.batch.name, materialSheetDet.qty, null)
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

        def materialSheetDet = MaterialSheetDet.get(params.id)

        def result
        try {
            def inventoryReplenishResult = inventoryDetailService.replenish(params,materialSheetDet.warehouse.id,materialSheetDet.warehouseLocation.id, materialSheetDet.item.id, materialSheetDet.batch.name, materialSheetDet.qty, null)
            
            if(inventoryReplenishResult.success)
                result = domainService.delete(materialSheetDet)
            else
                return inventoryReplenishResult
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [materialSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
