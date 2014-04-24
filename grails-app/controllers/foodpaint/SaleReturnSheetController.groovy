package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class SaleReturnSheetController {

    def domainService

    def index = {

        def list = SaleReturnSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [saleReturnSheetInstanceList: list, saleReturnSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def saleReturnSheet=SaleReturnSheet.get(params.id)

        if(saleReturnSheet){   

            render (contentType: 'application/json') {
                [success: true,data:saleReturnSheet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def saleReturnSheet=new SaleReturnSheet() 

        render (contentType: 'application/json') {
            [success: true,data:saleReturnSheet]
        }
    }

    def save = {
        def saleReturnSheet=new SaleReturnSheet(params)

        render (contentType: 'application/json') {
            domainService.save(saleReturnSheet)
        }
    }


    def update = {

        def  saleReturnSheet= SaleReturnSheet.get(params.id)
        saleReturnSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(saleReturnSheet)
        }         
    }

    def delete = {
        
        def  saleReturnSheet = SaleReturnSheet.get(params.id)

        def result
        try {
            result = domainService.delete(saleReturnSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [saleReturnSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
