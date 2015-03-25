package foodpaint

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON

class DocumentsController {

    static allowedMethods = [create:"POST",update: "POST",  delete: "POST"]
    def grailsApplication
    def domainService

    def enumService

    def index = {

        def list = Documents.createCriteria().list(params,params.criteria)
        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

    def show = {

        log.debug "${controllerName}-${actionName}"

        def i18nType = grailsApplication.config.grails.i18nType

        def documents=Documents.get(params.id);

        if(documents){   

            render (contentType: 'application/json') {
                [success: true, data:documents]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false, message:message(code: "${i18nType}.default.message.show.failed")]
            }          
        }
    }
 
    def create = {

        def documents=new Documents()
        render (contentType: 'application/json') {
            [success: true,data:documents]
        }
    }

    def save = {
        log.debug "${controllerName}-${actionName}"
        def documents=new Documents(params)
        
        render (contentType: 'application/json') {
            domainService.save(documents)
        }
    }

    def update = {

        def documents = Documents.get(params.id)
        documents.properties=params
        render (contentType: 'application/json') {
            domainService.save(documents)
        }
    }

    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def documents = Documents.get(params.id)
        def result
        try {
            
            result = domainService.delete(documents)
        
        }catch(DataIntegrityViolationException e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [documents, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
    
}
