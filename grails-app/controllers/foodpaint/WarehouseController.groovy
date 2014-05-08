package foodpaint

class WarehouseController {

    def domainService

    def index() {

        def list = Warehouse.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [data: list, total: list.totalCount]
        }
        
    }

     def show(Long id){

        def warehouse=Warehouse.get(id);  
        if(warehouse){   
            render (contentType: 'application/json') {
                [success: true,data:warehouse]
            }
        }else {
            render (contentType: 'application/json') {
                [success: false,message:message(code: 'default.message.show.failed')]
            }          
        }
    }
    def create(){

        def warehouse = new Warehouse()        
        render (contentType: 'application/json') {
            [success: true,data:warehouse]
        }
    }
    def save(){

        def warehouseInstance = new Warehouse(params)
        
        render (contentType: 'application/json') {
            domainService.save(warehouseInstance)
        }
    }

    def update(){
        def warehouseInstance = Warehouse.get(params.id)
        warehouseInstance.properties=params
        render (contentType: 'application/json') {
            domainService.save(warehouseInstance)
        }         
    }


    def delete(){
        def warehouseInstance = Warehouse.get(params.id)
        def result
        try {
            
            result = domainService.delete(warehouseInstance)
        
        }catch(e){
            log.error e
            def msg = message(code: 'default.message.delete.failed', args: [warehouseInstance, e.getMessage()])
            result = [success:false, message: msg] 
        }
        
        render (contentType: 'application/json') {
            result
        }
    }
    
}
