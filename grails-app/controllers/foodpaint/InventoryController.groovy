package foodpaint

class InventoryController {

    def domainService

    def index() {

        def list = Inventory.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [inventoryInstanceList: list, inventoryInstanceTotal: list.totalCount]
        }
        
    }

    def indexByWarehouseAndItem(){
        def warehouse = Warehouse.get(params.warehouse.id)
        def item = Item.get(params.item.id)

        def list = Inventory.where{
        	if(warehouse)
        		warehouse==warehouse
        	if(item)
        		item==item
        }.list()

        render (contentType: 'application/json') {
            [inventoryInstanceList: list, inventoryInstanceTotal: list.size()]
        }

    }
    
}
