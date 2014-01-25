package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class MaterialSheetDetController {

    def domainService

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

    def save = {

        def materialSheetDet=new MaterialSheetDet(params)
        render (contentType: 'application/json') {
            domainService.save(materialSheetDet)
        }
    }


    def update = {

        def  materialSheetDet = MaterialSheetDet.get(params.id)
        materialSheetDet.properties = params
        render (contentType: 'application/json') {
            domainService.save(materialSheetDet)
        }         
    }



    def delete = {

        def  materialSheetDet = MaterialSheetDet.get(params.id)

        def result
        try {
            
            result = domainService.delete(materialSheetDet)
        
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
