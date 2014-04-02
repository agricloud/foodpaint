package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(SaleReturnSheetDetController)
//把所有測試中會使用到的domain & service
@Mock([SaleReturnSheetDet, SaleReturnSheet,SaleSheet,SaleSheetDet,
       Item, Batch, Customer, 
       Warehouse, WarehouseLocation, Inventory, InventoryDetail, 
       BatchService, InventoryService, InventoryDetailService, DomainService])

class SaleReturnSheetDetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        def item1 = new Item(name:"item1",title:"品項1",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def customer1 = new Customer(name:"customer1",title:"客戶1").save(failOnError: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        def saleSheet1 = new SaleSheet(typeName:"ss",name:"00001",customer:customer1).save(failOnError: true, flush: true)
        def saleSheetDet1 = new SaleSheetDet(saleSheet:saleSheet1,typeName:"ss",name:"00001",sequence:1,item:item1,customerOrderDet:"",batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:"1500").save(failOnError: true, flush: true)
        def saleReturnSheet1 = new SaleReturnSheet(saleSheetDet:saleSheetDet1,typeName:"SS",name:"00001",customer:customer1).save(failOnError: true, flush: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["saleReturnSheet.id"]=1
        params["id"] = 1
        params["typeName"] = 'SS'
        params["name"] = '00001'
        params["sequence"] = 1
        params["customer.id"]=1
        params["item.id"] = 1
        params["saleSheet"] = 1
        params["SaleSheetDet.id"] = 1
        params["warehouse.id"]=1
        params["warehouseLocation.id"]=1
        params["batch.id"] = 1
        params["qty"] = 1000
    }

    void testIndex(){
        populateValidParams(params)

        //產生預設資料
        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        //def saleSheetDet1 = SaleSheetDet.get(1)
        def saleReturnSheet1 = SaleReturnSheet.get(1)
        def saleReturnSheetDet1 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)
        
        //設定傳入的params值
        params["saleReturnSheet.id"]=1

        //呼叫PurchaseSheetDetController執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1   
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "SS"
        assert response.json.data[0].name == "00001"
    }

    

    
}
