package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class MaterialSheetController {

    def domainService

    def index = {

        def list = MaterialSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [materialSheetInstanceList: list, materialSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def materialSheet=MaterialSheet.get(params.id)

        if(materialSheet){   

            render (contentType: 'application/json') {
                [success: true,data:materialSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def materialSheet=new MaterialSheet() 

        render (contentType: 'application/json') {
            [success: true,data:materialSheet]
        }
    }

    def save = {
        def materialSheet=new MaterialSheet(params)

        render (contentType: 'application/json') {
            domainService.save(materialSheet)
        }
    }


    def update = {

        def  materialSheet= MaterialSheet.get(params.id)
        materialSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(materialSheet)
        }         
    }



    def delete = {
        
        def  materialSheet = MaterialSheet.get(params.id)

        def result
        try {
            result = domainService.delete(materialSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [materialSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
