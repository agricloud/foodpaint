package foodpaint

class InventoryDetailController {

    def domainService

    def index() {

        def list = InventoryDetail.createCriteria().list(params,params.criteria)


        render (contentType: 'application/json') {
            [inventoryDetailInstanceList: list, inventoryDetailInstanceTotal: list.totalCount]
        }
        
    }
    
}
