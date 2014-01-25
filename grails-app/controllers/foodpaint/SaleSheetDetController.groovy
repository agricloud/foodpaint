package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class SaleSheetDetController {

    def domainService
    def batchService

    def index = {

        def saleSheet = SaleSheet.get(params.saleSheet.id)

        if(saleSheet){
            
            def saleSheetDet = SaleSheetDet.findAllBySaleSheet(saleSheet)

            render (contentType: 'application/json') {
               [success: true, data:saleSheetDet, total: saleSheetDet.size()]
            }

        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }
        }
        
    }


    def show = {

        def saleSheetDet = SaleSheetDet.get(params.id);


        if(saleSheetDet){   

            render (contentType: 'application/json') {
                [success: true,data:saleSheetDet]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }


    def create = {

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
                [success: false,message:message(code: 'saleSheetDet.message.create.failed')]
            }            
        }

    }

    def save = {
        def saleSheetDet=new SaleSheetDet(params)
        if(saleSheetDet.batch.item == saleSheetDet.customerOrderDet.item){
            render (contentType: 'application/json') {
                domainService.save(saleSheetDet)
            }
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheet.item.batch.item.not.equal', args:saleSheetDet.customerOrderDet)]
            }
        }

    }


    def update = {

        def  saleSheetDet = SaleSheetDet.get(params.id)
        saleSheetDet.properties = params
        if(saleSheetDet.batch.item == saleSheetDet.customerOrderDet.item){
            render (contentType: 'application/json') {
                domainService.save(saleSheetDet)
            }
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'sheet.item.batch.item.not.equal', args:saleSheetDet.customerOrderDet)]
            }
        }         
    }



    def delete = {

        def  saleSheetDet = SaleSheetDet.get(params.id)

        def result
        try {
            
            result = domainService.delete(saleSheetDet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [saleSheetDet, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
