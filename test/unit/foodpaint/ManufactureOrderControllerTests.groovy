package foodpaint



import org.junit.*
import grails.test.mixin.*
import common.*

@TestFor(ManufactureOrderController)
@Mock([ManufactureOrder, Item, Batch, Workstation, Supplier, BatchService, DomainService])
class ManufactureOrderControllerTests {

    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def workstation1 = new Workstation(name:"workstation1",title:"生產線1").save(failOnError: true)
        def supplier1 = new Supplier(name:"supplier1",title:"供應商1",country:Country.TAIWAN).save(failOnError: true, flush: true)
    }
    def populateValidParams(params) {
        assert params != null
        params["typeName"] = 'MO'
        params["name"] = '0001'
        params["qty"] = 1000
        params["item.id"] = 1
        params["batch.name"] = "batch1"
        params["workstation.id"]=1
    }

    void testIndex(){
        populateValidParams(params)
        //產生預設資料
        def manufactureOrder = new ManufactureOrder(params).save(failOnError: true, flush: true)

        //呼叫Controller執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1   
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "MO"
        assert response.json.data[0].name == "0001"
    }

    void testCreate() {
        
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.ManufactureOrder"
    }

    void testSave() {

        populateValidParams(params)
        controller.save()

        assert ManufactureOrder.count() == 1
        assert ManufactureOrder.list().get(0).item.name == "item1"
        assert ManufactureOrder.list().get(0).batch.name == "batch1"
    }

    void testSaveWithInccorectQty(){
        populateValidParams(params)

        params.qty=0
        controller.save()
        
        assertFalse response.json.success
        assert ManufactureOrder.list().size() == 0
    }

    void testSaveWithExistBatch(){
        def item1 = Item.get(1).save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)

        //設定傳入的params值
        populateValidParams(params)

        controller.save()

        assertFalse response.json.success
        assert ManufactureOrder.list().size() == 0
    }

    void testSaveWithWorkstationAndSupplier(){
        //設定傳入的params值
        populateValidParams(params)
        params["supplier.id"] = 1

        controller.save()

        assertFalse response.json.success
        assert ManufactureOrder.list().size() == 0
    }

    void testUpdate() {

        populateValidParams(params)
        def manufactureOrder = new ManufactureOrder(params).save(failOnError: true, flush: true)

        params["id"] = 1
        params["workstation.id"]=null
        params["supplier.id"]=1
        params["qty"] = 500
        controller.update()
        //執行結果應允許更新
        assert response.json.success
        assert ManufactureOrder.list().size() == 1
        assert ManufactureOrder.get(1).typeName == "MO"
        assert ManufactureOrder.get(1).name == "0001"
        assert ManufactureOrder.get(1).workstation == null
        assert ManufactureOrder.get(1).supplier.id == 1
        assert ManufactureOrder.get(1).qty == 500
    }

    void testUpdateWithInccorectQty(){
        populateValidParams(params)
        def manufactureOrder = new ManufactureOrder(params).save(failOnError: true, flush: true)

        params.id = 1
        params.qty = 0

        controller.update()

        assertFalse response.json.success
        assert ManufactureOrder.list().size() == 1
        assert ManufactureOrder.get(1).typeName == "MO"
        assert ManufactureOrder.get(1).name == "0001"
        assert ManufactureOrder.get(1).qty == 1000
    }

    void testUpdateWithExistBatch(){
        populateValidParams(params)
        def item1 = Item.get(1).save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def manufactureOrder = new ManufactureOrder(params).save(failOnError: true, flush: true)

        params.id = 1
        params["batch.name"]="batch1"

        controller.update()

        assert response.json.success
        assert ManufactureOrder.list().size() == 1
        assert ManufactureOrder.get(1).typeName == "MO"
        assert ManufactureOrder.get(1).name == "0001"
        assert ManufactureOrder.get(1).batch.name == "batch1"
    }

    void testUpdateWithWorkstationAndSupplier(){
        populateValidParams(params)
        def manufactureOrder = new ManufactureOrder(params).save(failOnError: true, flush: true)
        //設定傳入的params值
        params.id=1
        params["supplier.id"] = 1

        controller.update()

        assertFalse response.json.success
        assert ManufactureOrder.list().size() == 1
        assert ManufactureOrder.get(1).typeName == "MO"
        assert ManufactureOrder.get(1).name == "0001"
        assert ManufactureOrder.get(1).workstation.id == 1
        assert ManufactureOrder.get(1).supplier == null
        assert ManufactureOrder.get(1).qty == 1000
    }

    void testUpdateWithTypeNameAndName(){
        populateValidParams(params)
        def manufactureOrder = new ManufactureOrder(params).save(failOnError: true, flush: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert ManufactureOrder.list().size() == 1
        assert ManufactureOrder.get(1).typeName == "MO"
        assert ManufactureOrder.get(1).name == "0001"
        assert ManufactureOrder.get(1).qty == 1000
    }

    void testDelete(){

        populateValidParams(params)
        def manufactureOrder = new ManufactureOrder(params).save(failOnError: true, flush: true)

        params["id"] = 1
        controller.delete()

        assert response.json.success
        assert ManufactureOrder.list().size() == 0
    }

}
