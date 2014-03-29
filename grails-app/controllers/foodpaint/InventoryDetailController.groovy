package foodpaint

class InventoryDetailController {

    def domainService

    def index() {

        def list = InventoryDetail.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [inventoryDetailInstanceList: list, inventoryDetailInstanceTotal: list.totalCount]
        }
        
    }

    def indexByWarehouseAndWarehouseLocationAndItemAndBatch(){
        def warehouse = Warehouse.get(params.warehouse.id)
        def warehouseLocation = WarehouseLocation.get(params.warehouseLocation.id)
        def item = Item.get(params.item.id)
        def batch = Batch.get(params.batch.id)

        def list = InventoryDetail.where{
        	if(warehouse)
        		warehouse==warehouse
        	if(warehouseLocation)
        		warehouseLocation==warehouseLocation
        	if(item)
        		item==item
        	if(batch)
        		batch==batch
        }.list()

        render (contentType: 'application/json') {
            [inventoryDetailInstanceList: list, inventoryDeatilInstanceTotal: list.size()]
        }

    }
    
}
