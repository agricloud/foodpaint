package foodpaint
import grails.converters.*
import org.codehaus.groovy.grails.web.json.*;
import foodpaint.view.*

class ApiController {

    def traceService
    def messageSource
    def inventoryDetailService

    /**
     * PING
     */
    def ping() {
        render(contentType: "application/json") {
            result = true
            version = "1.0" // FIX ME, change to config value
        }
    }

    /*
     * 匯出履歷相關資料
     */
    def exportData() {

        def data = [:]
        data.put("item",Item.list())
        data.put("workstation",Workstation.list())
        data.put("operation",Operation.list())
        data.put("supplier",Supplier.list())
        data.put("customer",Customer.list())
        data.put("batch",Batch.list())
        data.put("batchRoute",BatchRoute.list())
        data.put("batchSource",BatchSource.list())


        def converter = data as JSON
        def jsParse = JSON.parse(converter.toString())

        jsParse.item.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.workstation.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.operation.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.supplier.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.customer.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.batch.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
            if(!it.supplier){
                it.supplier=[:]
                it.supplier.put("id","null")
                it.supplier.put("name","null")
            }
        }
        jsParse.batchSource.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
            if(!it.lastUpdated)
                it.lastUpdated=new Date()
            if(!it.dateCreated)
                it.dateCreated=new Date()
        }
        jsParse.batchRoute.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
            if(!it.lastUpdated)
                it.lastUpdated=new Date()
            if(!it.dateCreated)
                it.dateCreated=new Date()
            if(!it.supplier){
                it.supplier=[:]
                it.supplier.put("id","null")
                it.supplier.put("name","null")
            }
            if(!it.workstation){
                it.workstation=[:]
                it.workstation.put("id","null")
                it.workstation.put("name","null")
            }
        }
        println "feed data to Foodprint finished!!"
        converter = jsParse as JSON
        converter.render(response)

        // render (contentType: 'text/json') {
        //     data
        // }
        // JSON.use('deep')
        // def converter=data as JSON
        // converter.render(response)

    }


    //逆溯
    //查詢指定批號的銷貨單單身
    def querySaleSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def sourceSheet=SaleSheetDet.findAllByBatch(batch)

        render (contentType: 'text/json') {
            [data:sourceSheet]
        }
    }
    //查詢指定批號的入庫單單身
    def queryStockInSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def sourceSheet=StockInSheetDet.findAllByBatch(batch)

        render (contentType: 'text/json') {
            [data:sourceSheet]
        }
    }
    //質詢指定批號的託外進貨單單身
    def queryOutSrcPurchaseSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def sourceSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)

        render (contentType: 'text/json') {
            [data:sourceSheet]
        }
    }
    //查詢指定批號的進貨單單身
    def queryPurchaseSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def sourceSheet=PurchaseSheetDet.findAllByBatch(batch)

        render (contentType: 'text/json') {
            [data:sourceSheet]
        }
    }
    //查詢指定批號的製令
    def queryManufactureOrderByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def sourceSheet=ManufactureOrder.findAllByBatch(batch)

        render (contentType: 'text/json') {
            [data:sourceSheet]
        }
    }
    //查詢指定批號的入庫單單身其所屬的製令
    def queryManufactureOrderFromStockInSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def stockInSheetDets=StockInSheetDet.findAllByBatch(batch)
        def manufactureOrders=stockInSheetDets.manufactureOrder.unique()

        render (contentType: 'text/json') {
            [data:manufactureOrders]
        }
    }
    //查詢指定批號的託外進貨單單身其所屬的製令
    def queryManufactureOrderFromOutSrcPurchaseSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def outSrcPurchaseSheets=OutSrcPurchaseSheetDet.findAllByBatch(batch)
        def manufactureOrders=outSrcPurchaseSheets.manufactureOrder.unique()

        render (contentType: 'text/json') {
            [data:manufactureOrders]
        }
    }
    //查詢指定批號的進貨單單身其所屬的供應商
    def querySupplierFromPurchaseSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def purchaseSheetDets=PurchaseSheetDet.findAllByBatch(batch)
        def suppliers=purchaseSheetDets.purchaseSheet*.supplier.unique()

        render (contentType: 'text/json') {
            [data:suppliers]
        }
    }
    //查詢指定供應商、批號的進貨單單身
    def queryPurchaseSheetDetBySupplierAndBatch(String supplierName, String batchName){
        def supplier =Supplier.findByName(supplierName)
        def batch=Batch.findByName(batchName)

        def purchaseSheetDets=PurchaseSheetDet.where{
            purchaseSheet.supplier==supplier && batch==batch
        }.list()

        render (contentType: 'text/json') {
            [data:purchaseSheetDets]
        }
    }
    //查詢指定製令的領料單單身其所有的批號
    def queryBatchFromMaterialSheetDetByManufactureOrder(String typeName, String name){
        def manufactureOrder=ManufactureOrder.findByTypeNameAndName(typeName,name)
        def batchs = []
        manufactureOrder.materialSheetDets.each(){ materialSheetDet ->
            batchs << materialSheetDet.batch
        }

        render (contentType: 'text/json') {
            [data: batchs]
        }
    }
    //順溯
    //查詢指定批號的領料單單身其所屬的製令
    def queryManufactureOrderFromMaterialSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def materialSheetDets=MaterialSheetDet.findAllByBatch(batch)
        def sourceSheet=materialSheetDets.manufactureOrder.unique()

        render (contentType: 'text/json') {
            [data:sourceSheet]
        }
    }

    //查詢指定批號之銷貨單單身其所屬的客戶
    def queryCustomerFromSaleSheetDetByBatch(String batchName){
        def batch=Batch.findByName(batchName)
        def saleSheetDets=SaleSheetDet.findAllByBatch(batch)
        def customers=saleSheetDets.saleSheet*.customer.unique()

        render (contentType: 'text/json') {
            [data:customers]
        }

    }

    //查詢指定批號位於哪些倉庫中
    def queryInventoryByBatchAndGroupByWarehouse(String batchName){
        def inventoryDetailsGroupByWarehouse = inventoryDetailService.indexByBatchAndGroupByWarehouse(batchName)

        render (contentType: 'text/json') {
            [data:inventoryDetailsGroupByWarehouse]
        }
    }

    //查詢指定客戶、批號之銷貨單單身
    def querySaleSheetDetByCustomerAndBatch(String customerName, String batchName){
        def customer=Customer.findByName(customerName)
        def batch=Batch.findByName(batchName)

        def saleSheetDets=SaleSheetDet.where{
            saleSheet.customer == customer && batch==batch
        }.list()

        render (contentType: 'text/json') {
            [data:saleSheetDets]
        }

    }

    //查詢指定製令的入庫單單身其所有的批號
    def queryBatchFormStockInSheetDetByManufactureOrder(String typeName, String name){
        def manufactureOrder=ManufactureOrder.findByTypeNameAndName(typeName,name)
        def batchs = []
        manufactureOrder.stockInSheetDets.each(){ stockInSheetDet ->
            batchs << stockInSheetDet.batch
        }

        render (contentType: 'text/json') {
            [data: batchs]
        }
    }

    //查詢指定製令的託外進貨單單身其所有的批號
    def queryBatchFormOutSrcPurchaseSheetDetByManufactureOrder(String typeName, String name){
        def manufactureOrder=ManufactureOrder.findByTypeNameAndName(typeName,name)
        def batchs = []
        manufactureOrder.outSrcPurchaseSheetDets.each(){ outSrcPurchaseSheetDet ->
            batchs << outSrcPurchaseSheetDet.batch
        }

        render (contentType: 'text/json') {
            [data: batchs]
        }
    }



}
