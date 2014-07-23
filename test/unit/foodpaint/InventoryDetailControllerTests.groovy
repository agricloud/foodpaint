package foodpaint

import common.*
import org.junit.*
import grails.test.mixin.*

@TestFor(InventoryDetailController)
@Mock([Item, Batch, Warehouse, WarehouseLocation, Inventory, InventoryDetail,
    DomainService, BatchService, InventoryService, InventoryDetailService, TestService])
class InventoryDetailControllerTests {

    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)

    }

    def populateValidParams(params) {
        assert params != null
        params["item.id"]=1
        params["batch.name"]="batch1"
        params["warehouse.id"]=1
        params["warehouseLocation.id"]=1
        params["qty"] = 100.50
    }

    void testIndex() {

        populateValidParams(params)
        def inventoryDetail = new InventoryDetail(params).save(failOnError: true)
        controller.index()

        assert response.json.data.size() == 1
        assert response.json.total == 1   
        assert response.json.data[0].item.id == 1
        assert response.json.data[0].warehouse.id == 1
    }

    void testShow(){
        populateValidParams(params)
        def inventoryDetail = new InventoryDetail(params).save(failOnError: true)

        params.id = 1
        controller.show()
        assert response.json.success
        assert response.json.data.class == "foodpaint.InventoryDetail"
        assert response.json.data.item.id == 1
        assert response.json.data.warehouse.id == 1

    }

    void testCreate() {
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.InventoryDetail"
    }

    void testSave(){
        populateValidParams(params)
        controller.save()

        assert response.json.success
        assert InventoryDetail.list().size() == 1
        assert InventoryDetail.get(1).item.id == 1
        assert InventoryDetail.get(1).warehouse.id == 1
        assert InventoryDetail.get(1).qty == 100.5
    }

    void testSaveWithInccorectQty(){
        populateValidParams(params)

        params.qty=-1
        controller.save()
        
        assertFalse response.json.success
        assert InventoryDetail.list().size() == 0
    }

    void testUpdate(){
        populateValidParams(params)
        controller.save()

        params.id = 1
        params.qty= 50.5

        controller.update()
        
        // assert response.json.success //回傳的訊息來自controller.save 用來判斷更新是錯誤的
        assert InventoryDetail.list().size() == 1
        assert InventoryDetail.get(1).item.id == 1
        assert InventoryDetail.get(1).warehouse.id == 1
        assert InventoryDetail.get(1).qty == 50.5
    }

    void testUpdateWithInccorectQty(){
        populateValidParams(params)
        controller.save()
        params.id = 1
        params.qty= -1

        controller.update()
        
        // assertFalse response.json.success //回傳的訊息來自controller.save 用來判斷更新是錯誤的
        assert InventoryDetail.list().size() == 1
        assert InventoryDetail.get(1).item.id == 1
        assert InventoryDetail.get(1).warehouse.id == 1
        assert InventoryDetail.get(1).qty == 100.5
    }

    void testDelete(){
        populateValidParams(params)
        controller.save()

        params.id = 1
        controller.delete()
           
        // assert response.json.success //回傳的訊息來自controller.save 用來判斷刪除是錯誤的
        assert InventoryDetail.list().size() == 0
    }

}
