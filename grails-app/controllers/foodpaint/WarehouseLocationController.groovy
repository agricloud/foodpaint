
package foodpaint

class WarehouseLocationController {

    def grailsApplication
    def domainService

    def index = {

        def i18nType = grailsApplication.config.grails.i18nType

        def warehouse = Warehouse.get(params.warehouse.id)
        
        if(warehouse){

            def warehouseLocation = WarehouseLocation.findAllByWarehouse(warehouse,[sort: params.sort, order: params.order])

            render (contentType: 'application/json') {
               [success: true, data:warehouseLocation, total: warehouseLocation.size()]
            }           
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }            
        }


    }

    def show = {

        def i18nType = grailsApplication.config.grails.i18nType

        def warehouseLocation=WarehouseLocation.get(params.id);
        if(warehouseLocation){
            render (contentType: 'application/json') {
                [success: true,data:warehouseLocation]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.default.message.show.failed")]
            }            
        } 
    }



    def create = {

        def i18nType = grailsApplication.config.grails.i18nType

        if(params.warehouse.id){

            def warehouseLocation= new WarehouseLocation(params)

            render (contentType: 'application/json') {
                [success: true,data:warehouseLocation]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: "${i18nType}.warehouseLocation.message.create.failed")]
            }            
        }   
    }

    def save = {
        def warehouseLocation = new WarehouseLocation(params)
        render (contentType: 'application/json') {
            domainService.save(warehouseLocation)
        }
    }


    def update = {
        def warehouseLocation = WarehouseLocation.get(params.id)
        warehouseLocation.properties=params   
        render (contentType: 'application/json') {
            domainService.save(warehouseLocation)
        }

    }

    def delete = {

        def i18nType = grailsApplication.config.grails.i18nType
        
        def warehouseLocation = WarehouseLocation.get(params.id)
        def result
        try {
            
            result = domainService.delete(warehouseLocation)
        
        }catch(e){
            log.error e
            def msg = message(code: "${i18nType}.default.message.delete.failed", args: [warehouseLocation, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

}
