package foodpaint



import org.junit.*
import grails.test.mixin.*
import common.*

@TestFor(ManufactureOrderController)
@Mock([ManufactureOrder, Item, Batch, Workstation, BatchService, DomainService])
class ManufactureOrderControllerTests {

    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def workstation1 = new Workstation(name:"workstation1",title:"生產線01").save(failOnError: true)
    }
    def populateValidParams(params) {
        assert params != null
        params["typeName"] = 'MO'
        params["name"] = 'MO0001'
        params["qty"] = 123
        params["item.id"] = 1
        params["batch.name"] = "batch1"
        params["workstation.id"]=1
    }

    void testCreate() {
        populateValidParams(params)
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

}
