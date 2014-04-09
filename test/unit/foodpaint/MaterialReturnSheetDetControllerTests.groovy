package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(MaterialReturnSheetDetController)
//把所有測試中會使用到的domain & service
@Mock([MaterialReturnSheetDet, MaterialReturnSheet, ManufactureOrder,
       Item, Batch, Workstation, MaterialSheet, MaterialSheetDet,
       Warehouse, WarehouseLocation, Inventory, InventoryDetail, 
       InventoryService, InventoryDetailService, DomainService])

class MaterialReturnSheetDetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        def item1 = new Item(name:"item1",title:"原料",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def item2 = new Item(name:"item2",title:"成品").save(failOnError: true, flush: true)
        def batch2 = new Batch(name:"batch2", item:item2).save(failOnError: true, flush: true)
        def workstation1 = new Workstation(name:"workstation1",title:"生產線01").save(failOnError: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)

        def manufactureOrder1 = new ManufactureOrder(typeName:"MO",name:"00001",item:item2,qty:1000,batch:batch2).save(failOnError: true, flush: true)
        def materialSheet1 = new MaterialSheet(typeName:"MS",name:"00001",workstation:workstation1).save(failOnError: true, flush: true)
        def materialSheetDet1=new MaterialSheetDet(typeName:"MS",name:"00001",materialSheet:materialSheet1,sequence:1,workstation:workstation1,manufactureOrder:manufactureOrder1,item:item1,warehouse:warehouse1,warehouseLocation:warehouseLocation1,batch:batch1,qty:5000).save(failOnError: true, flush: true)
        def materialReturnSheet1 = new MaterialReturnSheet(typeName:"MRS",name:"00001",workstation:workstation1).save(failOnError: true, flush: true)

    }

    def populateValidParams(params) {
        assert params != null
        params["materialReturnSheet.id"]=1
        params["id"] = 1
        params["typeName"] = 'MRS'
        params["name"] = '00001'
        params["sequence"] = 1
        params["workstation.id"]=1
        params["manufactureOrder.id"] = 1
        params["item.id"] = 1
        params["warehouse.id"]=1
        params["warehouseLocation.id"]=1
        params["batch.id"]=1
        params["materialSheetDet.id"]=1
        params["qty"] = 1000
    }

    void testIndex(){
        //注入params值
        populateValidParams(params)

        //產生預設資料
        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def manufactureOrder1 = ManufactureOrder.get(1)
        def materialReturnSheet1 = MaterialReturnSheet.get(1)
        def materialSheetDet1 = MaterialSheetDet.get(1)
        def materialReturnSheetDet11 = new MaterialReturnSheetDet(params).save(failOnError: true, flush: true)
        
        //設定傳入的params值
        params["materialReturnSheet.id"]=1

        //呼叫PurchaseSheetDetController執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "MRS"
        assert response.json.data[0].name == "00001"
    }

    void testShow(){

        populateValidParams(params)

        //產生預設資料
        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def manufactureOrder1 = ManufactureOrder.get(1)
        def materialReturnSheet1 = MaterialReturnSheet.get(1)
        def materialReturnSheetDet11 = new MaterialReturnSheetDet(params).save(failOnError: true, flush: true)
       
        //設定傳入的params值
        params["id"]=1

        //呼叫PurchaseSheetDetController執行show()
        controller.show()
        //驗證結果
        assert response.json.success
        assert response.json.data.class == "foodpaint.MaterialReturnSheetDet" 
        assert response.json.data.typeName == "MRS"
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
        assert MaterialReturnSheetDet.list().size() == 1
        assert MaterialReturnSheetDet.get(1).typeName == "MRS"
        assert MaterialReturnSheetDet.get(1).name == "00001"
        assert MaterialReturnSheetDet.get(1).batch.name == "batch1"
        //驗證庫存處理是否正確
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==3000
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==3000
    }

    void testUpdate() {

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def manufactureOrder1 = ManufactureOrder.get(1)
        def materialReturnSheet1 = MaterialReturnSheet.get(1)
        def materialReturnSheetDet11 = new MaterialReturnSheetDet(params).save(failOnError: true, flush: true)

        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:4000).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:4000).save(failOnError: true, flush: true)
        
        params["qty"] = 500
        controller.update()
        //執行結果應允許更新
        assert response.json.success == true
        assert MaterialReturnSheetDet.list().get(0).batch.name == "batch1"
        assert MaterialReturnSheetDet.list().get(0).item.id == 1
        assert MaterialReturnSheetDet.list().get(0).qty == 500
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==3500
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==3500
    } // end of testUpdate

    void testUpdateWithIncorrectItemOrBatch() {

        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def manufactureOrder1 = ManufactureOrder.get(1)
        def materialReturnSheet1 = MaterialReturnSheet.get(1)
        def materialReturnSheetDet11 = new MaterialReturnSheetDet(params).save(failOnError: true, flush: true)

        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:4000).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:4000).save(failOnError: true, flush: true)

        def item3 = new Item(name:"item3",title:"品項3").save(failOnError: true, flush: true)
        def batch3 = new Batch(name:"batch3", item:item3).save(failOnError: true, flush: true)
        def inventory2 = new Inventory(warehouse:warehouse1,item:item3,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail2 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item3,batch:batch3,qty:500).save(failOnError: true, flush: true)
        
        params["item.id"] = 3
        params["batch.id"] = 3
        params["qty"] = 500
        controller.update()
        //領退單單身品項批號與領料單單身品項批號不符 執行結果應不允許更新
        assert response.json.success == false
        assert MaterialReturnSheetDet.list().get(0).batch.name == "batch1"
        assert MaterialReturnSheetDet.list().get(0).item.id == 1
        assert MaterialReturnSheetDet.list().get(0).qty == 1000
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==4000
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==4000
        assert Inventory.findByWarehouseAndItem(warehouse1,item3).qty==500
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item3,batch3).qty==500
    } // end of testUpdate

    void testDelete(){
        populateValidParams(params)

        def item1 = Item.get(1)
        def batch1 = Batch.get(1)
        def warehouse1 = Warehouse.get(1)
        def warehouseLocation1 = WarehouseLocation.get(1)
        def manufactureOrder1 = ManufactureOrder.get(1)
        def materialReturnSheet1 = MaterialReturnSheet.get(1)
        def materialReturnSheetDet11 = new MaterialReturnSheetDet(params).save(failOnError: true, flush: true)

        def inventory1 = new Inventory(warehouse:warehouse1,item:item1,qty:1000).save(failOnError: true, flush: true)
        def inventoryDetail1 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:1000).save(failOnError: true, flush: true)

        controller.delete()

        assert response.json.success == true
        assert MaterialReturnSheetDet.list().size() == 0
        assert Inventory.findByWarehouseAndItem(warehouse1,item1).qty==0
        assert InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse1,warehouseLocation1,item1,batch1).qty==0
    } // end of testDelete
}
