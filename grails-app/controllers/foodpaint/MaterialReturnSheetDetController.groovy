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
        println "if(materialReturnSheetDet.item != materialReturnSheetDet.materialSheetDet.item && materialReturnSheetDet.batch != materialReturnSheetDet.materialSheetDet.batch)"
        println "materialReturnSheetDet.item="+materialReturnSheetDet.item
        println "materialReturnSheetDet.materialSheetDet.item="+materialReturnSheetDet.materialSheetDet.item
        println "materialReturnSheetDet.batch="+materialReturnSheetDet.batch
        println "materialReturnSheetDet.materialSheetDet.batch="+materialReturnSheetDet.materialSheetDet.batch

        def materialReturnSheetDet = new MaterialReturnSheetDet(params)
        if(materialReturnSheetDet.item != materialReturnSheetDet.materialSheetDet.item && materialReturnSheetDet.batch != materialReturnSheetDet.materialSheetDet.batch){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialReturnSheetDet.itemOrBatch.materialSheetDet.itemOrBatch.not.equal', args: [materialReturnSheetDet, materialReturnSheetDet.manufactureOrder])]
            }
        }
        else{
            if(materialReturnSheetDet.qty>0){
                def inventoryReplenishResult = inventoryDetailService.replenish(materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty)
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
            else{
                render (contentType: 'application/json') {
                    [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [materialReturnSheetDet])]
                }
            }
        }
    }


    @Transactional
    def update() {

        def  materialReturnSheetDet = new MaterialReturnSheetDet(params)
        if(materialReturnSheetDet.item != materialReturnSheetDet.materialSheetDet.item && materialReturnSheetDet.batch != materialReturnSheetDet.materialSheetDet.batch){
            render (contentType: 'application/json') {
                [success:false, message:message(code: 'materialReturnSheetDet.itemOrBatch.materialSheetDet.itemOrBatch.not.equal', args: [materialReturnSheetDet, materialReturnSheetDet.manufactureOrder])]
            }
        }
        else{
            if(materialReturnSheetDet.qty>0){
                materialReturnSheetDet = MaterialReturnSheetDet.get(params.id)

                def inventoryConsumeResult = inventoryDetailService.consume(materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty)
                if(inventoryConsumeResult.success){

                    def updateBatch = Batch.get(params.batch.id)
                    def inventoryReplenishResult = inventoryDetailService.replenish(params.warehouse.id,params.warehouseLocation.id, params.item.id, updateBatch.name, params.qty.toLong())
                    if(inventoryReplenishResult.success){
                        materialReturnSheetDet.properties = params             
                        render (contentType: 'application/json') {
                            domainService.save(materialReturnSheetDet)
                        }
                    }
                    else{
                        //把數量還原更新前狀態
                        def inventoryRecoveryResult= inventoryDetailService.replenish(materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty)
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
            else{
                render (contentType: 'application/json') {
                    [success:false, message:message(code: 'sheet.qty.must.more.than.zero', args: [materialReturnSheetDet])]
                }
            }
        }       
    }



    @Transactional
    def delete(){

        def  materialReturnSheetDet = MaterialReturnSheetDet.get(params.id)

        def result
        try {
            def inventoryConsumeResult = inventoryDetailService.consume(materialReturnSheetDet.warehouse.id,materialReturnSheetDet.warehouseLocation.id, materialReturnSheetDet.item.id, materialReturnSheetDet.batch.name, materialReturnSheetDet.qty)
            
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
