package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class MaterialReturnSheetController {

    def domainService

    def index = {

        def list = MaterialReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [materialReturnSheetInstanceList: list, materialReturnSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def materialReturnSheet=MaterialReturnSheet.get(params.id)

        if(materialReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:materialReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def materialReturnSheet=new MaterialReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:materialReturnSheet]
        }
    }

    def save = {
        def materialReturnSheet=new MaterialReturnSheet(params)
        if((materialReturnSheet.workstation && materialReturnSheet.supplier)||(!materialReturnSheet.workstation && !materialReturnSheet.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheet.workstation.supplier.should.exist.one', args: [materialReturnSheet])]
            }
            return
        }
        render (contentType: 'application/json') {
            domainService.save(materialReturnSheet)
        }
    }


    def update = {

        def materialReturnSheet= MaterialReturnSheet.get(params.id)
        if(materialReturnSheet.materialReturnSheetDets && (params.workstation.id != materialReturnSheet.workstation?.id || params.supplier.id != materialReturnSheet.supplier?.id)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheet.materialReturnSheetDets.exists.workstationOrSupplier.not.allowed.change', args: [materialReturnSheet])]
            }
            return
        }
        materialReturnSheet.properties = params
        if((materialReturnSheet.workstation && materialReturnSheet.supplier)||(!materialReturnSheet.workstation && !materialReturnSheet.supplier)){
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'materialReturnSheet.workstation.supplier.should.exist.one', args: [materialReturnSheet])]
            }
            return
        }
        render (contentType: 'application/json') {
            domainService.save(materialReturnSheet)
        }         
    }



    def delete = {
        
        def materialReturnSheet = MaterialReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(materialReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [materialReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
