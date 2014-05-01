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
                [success: true,data:accountSheet]
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

    def save = { //如果已確認則無法動作
        def accountSheet=new AccountSheet(params)
       //    if(params.name||params.typeName||params.customer){  
                render (contentType: 'application/json') {
                    domainService.save(accountSheet)
                }
         // //   }
         //    else{
         //        render (contentType: 'application/json') {
         //            [success: false,message:message(code: 'accountSheet.dataisnotfill', args:accountSheet)]
         //        }
         //    }
    }


    def update = { //如果已確認則無法動作

        def  accountSheet= AccountSheet.get(params.id)
        accountSheet.properties = params
       if(!accountSheet.name||!accountSheet.typeName||accountSheet.currency==null||!accountSheet.customer){  
                render (contentType: 'application/json') {
                    domainService.save(accountSheet)
                }
            }
            else{
                render (contentType: 'application/json') {
                    [success: false,message:message(code: 'accountSheet.dataisnotfill', args:accountSheet)]
                }
            }   
    }



    def delete = {  //如果已確認則無法動作
        
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
