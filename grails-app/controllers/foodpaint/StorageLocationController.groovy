
package foodpaint

class StorageLocationController {

    def domainService

    def index = {

        def warehouse = Warehouse.get(params.warehouse.id)
        
        if(warehouse){
            
            def storageLocation = StorageLocation.findAllByWarehouse(warehouse)

            render (contentType: 'application/json') {
               [success: true, data:storageLocation, total: storageLocation.size()]
            }           
        }
        else{
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }            
        }


    }

    def show = {
        def storageLocation=StorageLocation.get(params.id);
        if(storageLocation){
            render (contentType: 'application/json') {
                [success: true,data:storageLocation]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }            
        } 
    }



    def create = {

        if(params.warehouse.id){

            def storageLocation= new StorageLocation(params)

            render (contentType: 'application/json') {
                [success: true,data:storageLocation]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'storageLocation.message.create.failed')]
            }            
        }   
    }

    def save = {
        def storageLocation = new StorageLocation(params)
        render (contentType: 'application/json') {
            domainService.save(storageLocation)
        }
    }


    def update = {
        def  storageLocation = StorageLocation.get(params.id)
        storageLocation.properties=params   
        render (contentType: 'application/json') {
            domainService.save(storageLocation)
        }

    }

    def delete = {
        def  storageLocation = StorageLocation.get(params.id)
        def result
        try {
            
            result = domainService.delete(storageLocation)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [storageLocation, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }

}
