package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(OutSrcPurchaseReturnSheetDetController)
//把所有測試中會使用到的domain & service
@Mock([OutSrcPurchaseReturnSheetDet, OutSrcPurchaseReturnSheet, ManufactureOrder,
       Item, Batch, Supplier, OutSrcPurchaseSheet, OutSrcPurchaseSheetDet,
       Warehouse, WarehouseLocation, Inventory, InventoryDetail, 
       InventoryService, InventoryDetailService, DomainService])

class OutSrcPurchaseReturnSheetDetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)
        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        
        def supplier1 = new Supplier(name:"supplier1",title:"供應商1",country:Country.TAIWAN).save(failOnError: true, flush: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        def manufactureOrder1 = new ManufactureOrder(typeName:"MO",name:"00001",item:item1,qty:1000,batch:batch1).save(failOnError: true, flush: true)
        def outSrcPurchaseReturnSheet1 = new OutSrcPurchaseReturnSheet(typeName:"OSPRS",name:"00001",supplier:supplier1).save(failOnError: true, flush: true)
    }
    
    def populateValidParams(params) {
        assert params != null
        params["outSrcPurchaseReturnSheet.id"]=1
        params["id"] = 1
        params["typeName"] = 'OSPRS'
        params["name"] = '00001'
        params["sequence"] = 1

        params["manufactureOrder.id"] = 1
        params["item.id"] = 1
        params["warehouse.id"]=1
        params["warehouseLocation.id"]=1
        params["batch.id"] = 1
        params["qty"] = 1000
    }

    void testIndex(){

        populateValidParams(params)
        //產生預設資料
        def outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(params).save(failOnError: true, flush: true)
        
        //設定傳入的params值
        params["outSrcPurchaseReturnSheet.id"]=1

        //呼叫PurchaseSheetDetController執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1   
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "OSPRS"
        assert response.json.data[0].name == "00001"
    }

    void testShow(){
        populateValidParams(params)
        //產生預設資料
        def outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(params).save(failOnError: true, flush: true)
       
        //設定傳入的params值
        params["id"]=1

        //呼叫PurchaseSheetDetController執行show()
        controller.show()
        //驗證結果
        assert response.json.success
        assert response.json.data.class == "foodpaint.OutSrcPurchaseReturnSheetDet" 
        assert response.json.data.typeName == "OSPRS"
        assert response.json.data.name == "00001"
    }

    void testSave(){
        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)

        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:2000).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:2000).save(failOnError: true, flush: true)

        //設定傳入的params值
        populateValidParams(params)

        controller.save()

        assert response.json.success
        assert OutSrcPurchaseReturnSheetDet.list().size() == 1
        assert OutSrcPurchaseReturnSheetDet.get(1).typeName == "OSPRS"
        assert OutSrcPurchaseReturnSheetDet.get(1).name == "00001"
        assert OutSrcPurchaseReturnSheetDet.get(1).batch.name == "batch1"
        //驗證庫存處理是否正確
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==1000
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==1000
    }

    void testSaveWithIncorrectBatchData(){
        def item2 = new Item(name:"item2",title:"橘子").save(failOnError: true, flush: true)
        def batch2 = new Batch(name:"batch2", item:item2).save(failOnError: true, flush: true)

        //設定傳入的params值
        populateValidParams(params)
        params["batch.id"] = 2

        controller.save()

        assert response.json.success ==false
        assert OutSrcPurchaseReturnSheetDet.list().size() == 0
    }

    void testUpdate() {

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(params).save(failOnError: true, flush: true)
                
        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:0).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:0).save(failOnError: true, flush: true)

        params["qty"] = 500

        controller.update()
        //執行結果應允許更新
        assert response.json.success == true
        assert OutSrcPurchaseReturnSheetDet.list().get(0).batch.name == "batch1"
        assert OutSrcPurchaseReturnSheetDet.list().get(0).item.id == 1
        assert OutSrcPurchaseReturnSheetDet.list().get(0).qty == 500
        //驗證更新前庫存
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==500
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==500
    }

    void testUpdateWithIncorrectBatchData() {

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def supplier1 = Supplier.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def manufactureOrder1 = ManufactureOrder.get(1)
        def outSrcPurchaseReturnSheet1 = OutSrcPurchaseReturnSheet.get(1)        
        def outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(params).save(failOnError: true, flush: true)
                
        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:outSrcPurchaseReturnSheetDet11.qty).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:outSrcPurchaseReturnSheetDet11.qty).save(failOnError: true, flush: true)

        def item2 = new Item(name:"item2",title:"橘子").save(failOnError: true, flush: true)
        def batch2 = new Batch(name:"batch2", item:item2).save(failOnError: true, flush: true)
        
        //給定錯誤的更新資料 批號品項與進貨品項不符

        params["batch.id"] = 2
        params["qty"] = 500

        controller.update()

        //執行結果應不允許更新 因此資料不變
        assert response.json.success == false
        assert OutSrcPurchaseReturnSheetDet.list().get(0).batch.name != "batch2"
        assert OutSrcPurchaseReturnSheetDet.list().get(0).item.id == 1
        assert OutSrcPurchaseReturnSheetDet.list().get(0).qty == 1000
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==1000
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==1000


    }

    void testDelete(){

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def supplier1 = Supplier.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def manufactureOrder1 = ManufactureOrder.get(1)
        def outSrcPurchaseReturnSheet1 = OutSrcPurchaseReturnSheet.get(1)        
        def outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(params).save(failOnError: true, flush: true)
  
        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:outSrcPurchaseReturnSheetDet11.qty).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:outSrcPurchaseReturnSheetDet11.qty).save(failOnError: true, flush: true)

        populateValidParams(params)
        controller.delete()

        assert response.json.success == true
        assert OutSrcPurchaseReturnSheetDet.list().size() == 0
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==2000
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==2000
    }
}