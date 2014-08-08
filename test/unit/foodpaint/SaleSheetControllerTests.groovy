package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(SaleSheetController)
//把所有測試中會使用到的domain & service
@Mock([SaleSheet,SaleSheetDet,
       Item, Batch, Customer, Warehouse, WarehouseLocation, 
       DomainService])

class SaleSheetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        def customer1 = new Customer(name:"customer1",title:"客戶1").save(failOnError: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["typeName"] = 'SS'
        params["name"] = '00001'
        params["customer.id"]=1
    }

    void testIndex(){
        //注入params值
        populateValidParams(params)

        //產生預設資料
        def saleSheet1 = new SaleSheet(params).save(failOnError: true, flush: true)
        
        //呼叫Controller執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1   
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "SS"
        assert response.json.data[0].name == "00001"
    }

    void testShow(){

        populateValidParams(params)

        //產生預設資料
        def saleSheet1 = new SaleSheet(params).save(failOnError: true, flush: true)
       
        //設定傳入的params值
        params["id"]=1

        //呼叫Controller執行show()
        controller.show()
        //驗證結果
        assert response.json.success
        assert response.json.data.class == "foodpaint.SaleSheet" 
        assert response.json.data.typeName == "SS"
        assert response.json.data.name == "00001"

    }

    void testCreate() {
        
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.SaleSheet"
    }

    void testSave(){

        //設定傳入的params值
        populateValidParams(params)

        controller.save()
        assert response.json.success
        assert SaleSheet.list().size() == 1
        assert SaleSheet.get(1).typeName == "SS"
        assert SaleSheet.get(1).name == "00001"
    }

    void testUpdate() {

        populateValidParams(params)
        def saleSheet1 = new SaleSheet(params).save(failOnError: true, flush: true)

        def customer2 = new Customer(name:"customer2",title:"客戶2").save(failOnError: true)

        params["id"] = 1
        params["customer.id"] = 2
        controller.update()
        //執行結果應允許更新
        assert response.json.success == true
        assert SaleSheet.get(1).typeName == "SS"
        assert SaleSheet.get(1).name == "00001"
        assert SaleSheet.get(1).customer.id == 2
    }

    void testUpdateWithTypeNameAndName(){
        populateValidParams(params)
        def saleSheet1 = new SaleSheet(params).save(failOnError: true, flush: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert SaleSheet.list().size() == 1
        assert SaleSheet.get(1).typeName == "SS"
        assert SaleSheet.get(1).name == "00001"
    }

    void testUpdateWithCustomerWhenSheetDetailExisted(){
        populateValidParams(params)

        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        
        def customer2 = new Customer(name:"customer2",title:"客戶2").save(failOnError: true)

        def saleSheet1 = new SaleSheet(params).save(failOnError: true, flush: true)
        def saleSheetDet11 = new SaleSheetDet(saleSheet:saleSheet1,typeName:saleSheet1.typeName, name:saleSheet1.name, sequence:1, item:item1, batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1, qty:100).save(failOnError: true, flush: true)

        params.id = 1
        params["customer.id"] = 2

        controller.update()
        
        assertFalse response.json.success
        assert SaleSheet.list().size() == 1
        assert SaleSheet.get(1).typeName == "SS"
        assert SaleSheet.get(1).name == "00001"
        assert SaleSheet.get(1).customer.id == 1
    }

    void testDelete(){

        populateValidParams(params)
        def saleSheet1 = new SaleSheet(params).save(failOnError: true, flush: true)

        params["id"]=1
        controller.delete()

        assert response.json.success == true
        assert SaleSheet.list().size() == 0
    }
}
