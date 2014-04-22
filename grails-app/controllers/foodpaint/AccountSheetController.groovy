package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class AccountSheetController {

    def domainService

    def index = {

        def list = AccountSheet.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [accountSheetInstanceList: list, accountSheetInstanceTotal: list.totalCount]
        }
        
    }
    def show = {
        def accountSheet=AccountSheet.get(params.id)

        if(accountSheet){   

            render (contentType: 'application/json') {
                [success: true,data:accountSheet)]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }

    def create = {

        def accountSheet=new AccountSheet() 

        render (contentType: 'application/json') {
            [success: true,data:accountSheet]
        }
    }

    def save = {
        def accountSheet=new AccountSheet(params)

        render (contentType: 'application/json') {
            domainService.save(accountSheet)
        }
    }


    def update = {

        def  accountSheet= AccountSheet.get(params.id)
        accountSheet.properties = params
        render (contentType: 'application/json') {
            domainService.save(accountSheet)
        }         
    }



    def delete = {
        
        def  accountSheet = AccountSheet.get(params.id)

        def result
        try {
            result = domainService.delete(accountSheet)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [accountSheet, e])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
}
