package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(SaleReturnSheetDetController)
//把所有測試中會使用到的domain & service
@Mock([SaleReturnSheetDet, SaleReturnSheet, SaleSheetDet, SaleSheet, 
       Item, Batch, Customer, 
       Warehouse, WarehouseLocation, Inventory, InventoryDetail, 
       BatchService, InventoryService, InventoryDetailService, DomainService])

class SaleReturnSheetDetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        grailsApplication.config.grails.i18nType='mfg'
        SaleReturnSheetDet.metaClass.getGrailsApplication = {
            return grailsApplication
        }
        SaleReturnSheetDet.metaClass.getMessageSource = {
            return messageSource
        }

        def item1 = new Item(name:"item1",title:"item1",unit:"kg").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def item2 = new Item(name:"item2",title:"item2",unit:"kg").save(failOnError: true, flush: true)
        def batch2 = new Batch(name:"batch2", item:item2).save(failOnError: true, flush: true)
        def customer1 = new Customer(name:"customer1",title:"客戶1").save(failOnError: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)

        def saleSheet1 = new SaleSheet(typeName:"SS", name:"00001", customer:customer1).save(failOnError: true, flush: true)  
        def saleSheetDet11 = new SaleSheetDet(saleSheet:saleSheet1,typeName:saleSheet1.typeName, name:saleSheet1.name, sequence:1, item:item1, batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1, qty:1000).save(failOnError: true, flush: true)
        
        def saleReturnSheet1 = new SaleReturnSheet(typeName:"SRS",name:"00001",customer:customer1).save(failOnError: true, flush: true)

    }

    def populateValidParams(params) {
        assert params != null
        params["saleReturnSheet.id"]=1
        params["typeName"] = 'SRS'
        params["name"] = '00001'
        params["sequence"] = 1
        params["saleSheetDet.id"]=1
        params["item.id"] = 1
        params["warehouse.id"]=1
        params["warehouseLocation.id"]=1
        params["batch.id"] = 1
        params["qty"] = 1000
    }

    void testIndex(){
        //注入params值
        populateValidParams(params)

        //產生預設資料
        def saleReturnSheetDet11 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)
        
        //設定傳入的params值
        params["saleReturnSheet.id"]=1

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
        def saleReturnSheetDet11 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)
       
        //設定傳入的params值
        params["id"]=1

        //呼叫Controller執行show()
        controller.show()
        //驗證結果
        assert response.json.success
        assert response.json.data.class == "foodpaint.SaleReturnSheetDet" 
        assert response.json.data.typeName == "SRS"
        assert response.json.data.name == "00001"
        assert response.json.data.batch.id == 1

    }

    void testCreate() {
        params["saleReturnSheet.id"]=1
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.SaleReturnSheetDet"
    }

    void testSave(){
        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)

        //設定傳入的params值
        populateValidParams(params)

        controller.save()
        assert response.json.success
        assert SaleReturnSheetDet.list().size() == 1
        assert SaleReturnSheetDet.get(1).typeName == "SRS"
        assert SaleReturnSheetDet.get(1).name == "00001"
        assert SaleReturnSheetDet.get(1).batch.name == "batch1"
        //驗證庫存處理是否正確
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==1000
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==1000
    }

    void testSaveWithIncorrectQty(){
        populateValidParams(params)

        params.qty=0
        controller.save()
        
        assertFalse response.json.success
        assert SaleReturnSheetDet.list().size() == 0
    }

    void testSaveWithIncorrectItem1(){
        //設定傳入的params值
        populateValidParams(params)
        //給定錯誤的資料 品項與銷貨品項不符
        params["item.id"] = 2

        controller.save()

        assert response.json.success == false
        assert SaleReturnSheetDet.list().size() == 0
        assert Inventory.get(1)==null
        assert InventoryDetail.get(1)==null
    }

    void testSaveWithIncorrectBatch1(){
        def item1=Item.get(1)
        def batch1=Batch.get(1)
        def item2=Item.get(2)
        def batch2=Batch.get(2)
        def warehouse1=Warehouse.get(1)
        def warehouseLocation1=WarehouseLocation.get(1)

        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:0d).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:0d).save(failOnError: true, flush: true)
        def inventory2 = new Inventory(warehouse:warehouse1,item:item2,qty:0d).save(failOnError: true, flush: true)
        def inventoryDetail2 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item2,batch:batch2,qty:0d).save(failOnError: true, flush: true)

        //設定傳入的params值
        populateValidParams(params)
        //給定錯誤的資料 批號品項與銷貨品項不符
        params["batch.id"] = 2

        controller.save()

        assert response.json.success == false
        assert SaleReturnSheetDet.list().size() == 0
        assert Inventory.get(1).qty==0
        assert InventoryDetail.get(1).qty==0
        assert Inventory.get(2).qty==0
        assert InventoryDetail.get(2).qty==0
    }

    void testSaveWithIncorrectBatch2(){
        def item1 = Item.get(1)
        def batch3 = new Batch(name:"batch3", item:item1).save(failOnError: true, flush: true)

        //設定傳入的params值
        populateValidParams(params)
        //給定錯誤的資料 批號與銷貨單不同
        params["batch.id"] = 3

        controller.save()

        assert response.json.success == false
        assert SaleReturnSheetDet.list().size() == 0
        assert Inventory.get(1)==null
        assert InventoryDetail.get(1)==null
    }


    void testSaveWithIncorrectTypeNameAndName(){
        populateValidParams(params)

        params.typeName= "AAA"

        controller.save()
        
        assertFalse response.json.success
        assert SaleReturnSheetDet.list().size() == 0
    }

    void testUpdate() {

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def saleReturnSheetDet11 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)

        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:saleReturnSheetDet11.qty).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:saleReturnSheetDet11.qty).save(failOnError: true, flush: true)

        params["id"] = 1
        params["qty"] = 500
        controller.update()
        //執行結果應允許更新
        assert response.json.success
        assert SaleReturnSheetDet.get(1).batch.name == "batch1"
        assert SaleReturnSheetDet.get(1).item.id == 1
        assert SaleReturnSheetDet.get(1).qty == 500
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==500
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==500
    }

    void testUpdateWithIncorrectItem1(){
        populateValidParams(params)
        controller.save()

        //給定錯誤的資料 品項與銷貨單不同
        params["id"] = 1
        params["item.id"] = 2

        controller.update()

        assert SaleReturnSheetDet.get(1).batch.name == "batch1"
        assert SaleReturnSheetDet.get(1).item.id == 1
        assert SaleReturnSheetDet.get(1).qty == 1000
        assert Inventory.get(1).qty==1000
        assert InventoryDetail.get(1).qty==1000
        assert Inventory.get(2)== null
        assert InventoryDetail.get(2)==null
    }

    void testUpdateWithIncorrectBatch1() {

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def saleReturnSheetDet11 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)

        def item3 = new Item(name:"item3",title:"品項3",unit:"kg").save(failOnError: true, flush: true)
        def batch3 = new Batch(name:"batch3", item:item3).save(failOnError: true, flush: true)
        
        params["id"] = 1
        params["item.id"] = 3
        params["batch.id"] = 3
        params["qty"] = 500
        controller.update()

        assertFalse response.json.success
        assert SaleReturnSheetDet.get(1).batch.name == "batch1"
        assert SaleReturnSheetDet.get(1).item.id == 1
        assert SaleReturnSheetDet.get(1).qty == 1000
        assert Inventory.findByWarehouseAndItem(warehouse1,item1)==null
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1)==null
        assert Inventory.findByWarehouseAndItem(warehouse1,item3)== null
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item3,batch3)==null
    }

    void testUpdateWithIncorrectBatch2(){
        populateValidParams(params)
        controller.save()

        def item1 = Item.get(1)
        def batch3 = new Batch(name:"batch3", item:item1).save(failOnError: true, flush: true)

        //給定錯誤的資料 批號與銷貨單不同
        params["id"] = 1
        params["batch.id"] = 2

        controller.update()

        assert SaleReturnSheetDet.get(1).batch.name == "batch1"
        assert SaleReturnSheetDet.get(1).item.id == 1
        assert SaleReturnSheetDet.get(1).qty == 1000
        assert Inventory.get(1).qty==1000
        assert InventoryDetail.get(1).qty==1000
        assert Inventory.get(2)== null
        assert InventoryDetail.get(2)==null
    }

    void testUpdateWithIncorrectQty(){
        populateValidParams(params)
        def saleReturnSheetDet11 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)

        params.id = 1
        params.qty = 0

        controller.update()
        
        assertFalse response.json.success
        assert SaleReturnSheetDet.list().size() == 1
        assert SaleReturnSheetDet.get(1).typeName == "SRS"
        assert SaleReturnSheetDet.get(1).name == "00001"
        assert SaleReturnSheetDet.get(1).sequence == 1
        assert SaleReturnSheetDet.get(1).qty == 1000
    }

    void testUpdateWithTypeNameAndName(){
        populateValidParams(params)
        def saleReturnSheetDet11 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert SaleReturnSheetDet.list().size() == 1
        assert SaleReturnSheetDet.get(1).typeName == "SRS"
        assert SaleReturnSheetDet.get(1).name == "00001"
        assert SaleReturnSheetDet.get(1).sequence == 1
        assert SaleReturnSheetDet.get(1).qty == 1000
    }


    void testDelete(){

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def saleReturnSheetDet11 = new SaleReturnSheetDet(params).save(failOnError: true, flush: true)

        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:1000).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:1000).save(failOnError: true, flush: true)

        params["id"]=1
        controller.delete()

        assert response.json.success
        assert SaleReturnSheetDet.list().size() == 0
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==0
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==0


    }
}
