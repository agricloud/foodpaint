package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(SaleReturnSheetController)
//把所有測試中會使用到的domain & service
@Mock([SaleReturnSheet,SaleReturnSheetDet, SaleSheet, SaleSheetDet,
       Item, Batch, Customer,
       Warehouse, WarehouseLocation, DomainService])

class SaleReturnSheetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        grailsApplication.config.grails.i18nType='mfg'
        SaleReturnSheet.metaClass.getGrailsApplication = {
            return grailsApplication
        }
        SaleReturnSheet.metaClass.getMessageSource = {
            return messageSource
        }

        def customer1 = new Customer(name:"customer1",title:"客戶1").save(failOnError: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["typeName"] = 'SRS'
        params["name"] = '00001'
        params["customer.id"]=1
    }

    void testIndex(){
        //注入params值
        populateValidParams(params)

        //產生預設資料
        def saleReturnSheet1 = new SaleReturnSheet(params).save(failOnError: true, flush: true)
        
        //呼叫Controller執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1   
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "SRS"
        assert response.json.data[0].name == "00001"
    }

    void testShow(){

        populateValidParams(params)

        //產生預設資料
        def saleReturnSheet1 = new SaleReturnSheet(params).save(failOnError: true, flush: true)
       
        //設定傳入的params值
        params["id"]=1

        //呼叫Controller執行show()
        controller.show()
        //驗證結果
        assert response.json.success
        assert response.json.data.class == "foodpaint.SaleReturnSheet" 
        assert response.json.data.typeName == "SRS"
        assert response.json.data.name == "00001"

    }

    void testCreate() {
        
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.SaleReturnSheet"
    }

    void testSave(){

        //設定傳入的params值
        populateValidParams(params)

        controller.save()
        assert response.json.success
        assert SaleReturnSheet.list().size() == 1
        assert SaleReturnSheet.get(1).typeName == "SRS"
        assert SaleReturnSheet.get(1).name == "00001"
    }

    void testUpdate() {

        populateValidParams(params)
        def saleReturnSheet1 = new SaleReturnSheet(params).save(failOnError: true, flush: true)
        def customer2 = new Customer(name:"customer2",title:"客戶2").save(failOnError: true)

        params["id"] = 1
        params["customer.id"] = 2
        controller.update()
        //執行結果應允許更新
        assert response.json.success == true
        assert SaleReturnSheet.get(1).typeName == "SRS"
        assert SaleReturnSheet.get(1).name == "00001"
        assert SaleReturnSheet.get(1).customer.id == 2
    }

    void testUpdateWithTypeNameAndName(){
        populateValidParams(params)
        def saleReturnSheet1 = new SaleReturnSheet(params).save(failOnError: true, flush: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert SaleReturnSheet.list().size() == 1
        assert SaleReturnSheet.get(1).typeName == "SRS"
        assert SaleReturnSheet.get(1).name == "00001"
    }

    void testUpdateWithCustomerWhenSheetDetailExisted(){
        populateValidParams(params)

        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def customer1 = Customer.get(1)
        def customer2 = new Customer(name:"customer2",title:"客戶2").save(failOnError: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        
        def saleSheet1 = new SaleSheet(typeName:"SS", name:"00001", customer:customer1).save(failOnError: true, flush: true)  
        def saleSheetDet11 = new SaleSheetDet(saleSheet:saleSheet1,typeName:saleSheet1.typeName, name:saleSheet1.name, sequence:1, item:item1, batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1, qty:100).save(failOnError: true, flush: true)

        def saleReturnSheet1 = new SaleReturnSheet(params).save(failOnError: true, flush: true)  
        def saleReturnSheetDet11 = new SaleReturnSheetDet(saleReturnSheet:saleReturnSheet1,typeName:saleReturnSheet1.typeName, name:saleReturnSheet1.name, sequence:1, item:item1, batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1, qty:100, saleSheetDet:saleSheetDet11).save(failOnError: true, flush: true)

        params.id = 1
        params["customer.id"]=2

        controller.update()
        
        assertFalse response.json.success
        assert SaleReturnSheet.list().size() == 1
        assert SaleReturnSheet.get(1).typeName == "SRS"
        assert SaleReturnSheet.get(1).name == "00001"
        assert SaleReturnSheet.get(1).customer.id == 1
    }

    void testDelete(){

        populateValidParams(params)
        def saleReturnSheet1 = new SaleReturnSheet(params).save(failOnError: true, flush: true)

        params["id"]=1
        controller.delete()

        assert response.json.success == true
        assert SaleReturnSheet.list().size() == 0
    }
}
