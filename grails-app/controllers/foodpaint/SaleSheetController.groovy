package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class SaleSheetController {

    def domainService

    def index = {

        def list = SaleSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [saleSheetInstanceList: list, saleSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def saleSheet=SaleSheet.get(params.id)

        if(saleSheet){   

            render (contentType: 'application/json') {
                [success: true,data:saleSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def saleSheet=new SaleSheet() 

        render (contentType: 'application/json') {
            [success: true,data:saleSheet]
        }
    }

    def save = {
        def saleSheet=new SaleSheet(params)

        render (contentType: 'application/json') {
            domainService.save(saleSheet)
        }
    }


    def update = {

        def  saleSheet= SaleSheet.get(params.id)
        saleSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(saleSheet)
        }         
    }



    def delete = {
        
        def  saleSheet = SaleSheet.get(params.id)

        def result
        try {
            result = domainService.delete(saleSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [saleSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
