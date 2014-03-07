package foodpaint

class InventoryController {

    def domainService

    def index() {

        def list = Inventory.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [inventoryInstanceList: list, inventoryInstanceTotal: list.totalCount]
        }
        
    }
    
}
