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
    def querySaleSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def saleSheetDets=SaleSheetDet.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:saleSheetDets]
        }
    }

    def querySaleReturnSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def saleReturnSheetDets=SaleReturnSheetDet.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:saleReturnSheetDets]
        }
    }
    
    //查詢指定批號的入庫單單身其所屬的製令
    def queryManufactureOrderFromStockInSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def stockInSheetDets=StockInSheetDet.findAllByBatchAndSite(batch,site)
        def manufactureOrders=stockInSheetDets.manufactureOrder.unique()

        render (contentType: 'text/json') {
            [data:manufactureOrders]
        }
    }

    //查詢指定批號的託外進貨單單身其所屬的製令
    def queryManufactureOrderFromOutSrcPurchaseSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def outSrcPurchaseSheets=OutSrcPurchaseSheetDet.findAllByBatchAndSite(batch,site)
        def manufactureOrders=outSrcPurchaseSheets.manufactureOrder.unique()

        render (contentType: 'text/json') {
            [data:manufactureOrders]
        }
    }

    //查詢指定批號的進貨單單身其所屬的供應商
    def querySupplierFromPurchaseSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def purchaseSheetDets=PurchaseSheetDet.findAllByBatchAndSite(batch,site)
        def suppliers=purchaseSheetDets.purchaseSheet*.supplier.unique()

        render (contentType: 'text/json') {
            [data:suppliers]
        }
    }

    //查詢指定供應商、批號的進貨單單身
    def queryPurchaseSheetDetBySupplierAndBatch(String supplierName, String batchName, String siteId){
        def site = Site.get(siteId)
        def supplier =Supplier.findByNameAndSite(supplierName,site)
        def batch=Batch.findByNameAndSite(batchName,site)

        def purchaseSheetDets=PurchaseSheetDet.where{
            purchaseSheet.supplier==supplier && batch==batch && site==site
        }.list()

        render (contentType: 'text/json') {
            [data:purchaseSheetDets]
        }
    }

    def queryPurchaseReturnSheetDetBySupplierAndBatch(String supplierName, String batchName, String siteId){
        def site = Site.get(siteId)
        def supplier =Supplier.findByNameAndSite(supplierName,site)
        def batch=Batch.findByNameAndSite(batchName,site)

        def purchaseReturnSheetDets=PurchaseReturnSheetDet.where{
            purchaseReturnSheet.supplier==supplier && batch==batch && site==site
        }.list()

        render (contentType: 'text/json') {
            [data:purchaseReturnSheetDets]
        }
    }

    //查詢指定製令的領料單單身其所有的批號
    def queryBatchFromMaterialSheetDetByManufactureOrder(String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def batchs = manufactureOrder.materialSheetDets*.batch.unique()

        render (contentType: 'text/json') {
            [data: batchs]
        }
    }

    //順溯

    //查詢指定批號的領料單單身其所屬的製令
    def queryManufactureOrderFromMaterialSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def materialSheetDets=MaterialSheetDet.findAllByBatchAndSite(batch,site)
        def manufactureOrders=materialSheetDets.manufactureOrder.unique()

        render (contentType: 'text/json') {
            [data:manufactureOrders]
        }
    }

    //查詢指定批號的領料單單身
    def queryMaterialSheetDetByBatchAndManufactureOrder(String batchName, String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def materialSheetDets=MaterialSheetDet.findAllByBatchAndManufactureOrderAndSite(batch,manufactureOrder,site)

        render (contentType: 'text/json') {
            [data: materialSheetDets]
        }
    }

    def queryMaterialReturnSheetDetByBatchAndManufactureOrder(String batchName, String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def materialReturnSheetDets=MaterialReturnSheetDet.findAllByBatchAndManufactureOrderAndSite(batch,manufactureOrder,site)

        render (contentType: 'text/json') {
            [data: materialReturnSheetDets]
        }
    }

    //查詢指定批號之銷貨單單身其所屬的客戶
    def queryCustomerFromSaleSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def saleSheetDets=SaleSheetDet.findAllByBatchAndSite(batch,site)
        def customers=saleSheetDets.saleSheet*.customer.unique()

        render (contentType: 'text/json') {
            [data:customers]
        }

    }

    //查詢指定客戶、批號之銷貨單單身
    def querySaleSheetDetByCustomerAndBatch(String customerName, String batchName, String siteId){
        def site = Site.get(siteId)
        def customer=Customer.findByNameAndSite(customerName,site)
        def batch=Batch.findByNameAndSite(batchName,site)

        def saleSheetDets=SaleSheetDet.where{
            saleSheet.customer == customer && batch==batch && site==site
        }.list()

        render (contentType: 'text/json') {
            [data:saleSheetDets]
        }

    }

    def querySaleReturnSheetDetByCustomerAndBatch(String customerName, String batchName, String siteId){
        def site = Site.get(siteId)
        def customer=Customer.findByNameAndSite(customerName,site)
        def batch=Batch.findByNameAndSite(batchName,site)

        def saleReturnSheetDets=SaleReturnSheetDet.where{
            saleReturnSheet.customer == customer && batch==batch && site==site
        }.list()

        render (contentType: 'text/json') {
            [data:saleReturnSheetDets]
        }

    }

    //查詢指定批號位於哪些倉庫中
    def queryInventoryByBatchAndGroupByWarehouse(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def inventoryDetailsGroupByWarehouse = inventoryDetailService.indexByBatchAndGroupByWarehouse(batch,site)

        render (contentType: 'text/json') {
            [data:inventoryDetailsGroupByWarehouse]
        }
    }

    //查詢指定製令的入庫單單身其所有的批號
    def queryBatchFormStockInSheetDetByManufactureOrder(String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def batchs = manufactureOrder.stockInSheetDets*.batch.unique()

        render (contentType: 'text/json') {
            [data: batchs]
        }
    }

    //查詢指定製令的託外進貨單單身其所有的批號
    def queryBatchFormOutSrcPurchaseSheetDetByManufactureOrder(String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def batchs = manufactureOrder.outSrcPurchaseSheetDets*.batch.unique()

        render (contentType: 'text/json') {
            [data: batchs]
        }
    }

    //順+逆

    //查詢指定批號的入庫單單身
    def queryStockInSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def stockInSheetDets=StockInSheetDet.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:stockInSheetDets]
        }
    }

    //查詢指定批號的託外進貨單單身
    def queryOutSrcPurchaseSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def outSrcPurchaseSheetDets=OutSrcPurchaseSheetDet.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:outSrcPurchaseSheetDets]
        }
    }

    def queryOutSrcPurchaseReturnSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def outSrcPurchaseReturnSheetDets=OutSrcPurchaseReturnSheetDet.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:outSrcPurchaseReturnSheetDets]
        }
    }

    //查詢指定批號的進貨單單身
    def queryPurchaseSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def purchaseSheetDets=PurchaseSheetDet.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:purchaseSheetDets]
        }
    }

    def queryPurchaseReturnSheetDetByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def purchaseReturnSheetDets=PurchaseReturnSheetDet.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:purchaseReturnSheetDets]
        }
    }

    //查詢指定批號的製令
    def queryManufactureOrderByBatch(String batchName, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def manufactureOrders=ManufactureOrder.findAllByBatchAndSite(batch,site)

        render (contentType: 'text/json') {
            [data:manufactureOrders]
        }
    }

    //查詢指定製令的入庫單單身
    def queryStockInSheetDetByBatchAndManufactureOrder(String batchName, String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def stockInSheetDets=StockInSheetDet.findAllByBatchAndManufactureOrderAndSite(batch,manufactureOrder,site)

        render (contentType: 'text/json') {
            [data: stockInSheetDets]
        }
    }

    //查詢指定批號與製令的託外進貨單
    def queryOutSrcPurchaseSheetDetByBatchAndManufactureOrder(String batchName, String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def outSrcPurchaseSheetDets=OutSrcPurchaseSheetDet.findAllByBatchAndManufactureOrderAndSite(batch,manufactureOrder,site)

        render (contentType: 'text/json') {
            [data: outSrcPurchaseSheetDets]
        }
    }

    def queryOutSrcPurchaseReturnSheetDetByBatchAndManufactureOrder(String batchName, String typeName, String name, String siteId){
        def site = Site.get(siteId)
        def batch=Batch.findByNameAndSite(batchName,site)
        def manufactureOrder=ManufactureOrder.findByTypeNameAndNameAndSite(typeName,name,site)
        def outSrcPurchaseReturnSheetDets=OutSrcPurchaseReturnSheetDet.findAllByBatchAndManufactureOrderAndSite(batch,manufactureOrder,site)

        render (contentType: 'text/json') {
            [data: outSrcPurchaseReturnSheetDets]
        }
    }



}
