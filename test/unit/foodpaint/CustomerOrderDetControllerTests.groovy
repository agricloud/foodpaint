package foodpaint

import common.*
import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerOrderDetController)
@Mock([Item, Customer, CustomerOrder, CustomerOrderDet, DomainService])
class CustomerOrderDetControllerTests {

    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)
        def customer1 = new Customer(name:"customer1",title:"客戶1").save(failOnError: true)
        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def customerOrder = new CustomerOrder(typeName:"CO", name:"001",customer:customer1).save(failOnError: true, flush: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["customerOrder.id"] = 1
        params["typeName"] = "CO"
        params["name"] = "001"
        params["sequence"] = 1
        params["item.id"] = 1
        params["qty"] = 1000
        
    }
 
    void testIndex() {

        populateValidParams(params)
        def customerOrderDet = new CustomerOrderDet(params).save(failOnError: true)
        controller.index()

        assert response.json.data.size() == 1
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "CO"
        assert response.json.data[0].name == "001"
        assert response.json.data[0].sequence == 1
    }

    void testShow(){
        populateValidParams(params)
        def customerOrderDet = new CustomerOrderDet(params).save(failOnError: true)

        params.id = 1
        controller.show()
        assert response.json.success
        assert response.json.data.class == "foodpaint.CustomerOrderDet"
        assert response.json.data.typeName == "CO"
        assert response.json.data.name == "001"
        assert response.json.data.sequence == 1

    }

    void testCreate() {
        populateValidParams(params)
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.CustomerOrderDet"
    }

    void testSave(){
        populateValidParams(params)
        controller.save()

        assert response.json.success
        assert CustomerOrderDet.list().size() == 1
        assert CustomerOrderDet.get(1).typeName == "CO"
        assert CustomerOrderDet.get(1).name == "001"
        assert CustomerOrderDet.get(1).sequence == 1
    }

    void testSaveWithInccorectQtyData(){
        populateValidParams(params)

        params.qty=0
        controller.save()
        
        assertFalse response.json.success
        assert CustomerOrderDet.list().size() == 0
    }

    void testUpdate(){
        populateValidParams(params)
        def customerOrderDet = new CustomerOrderDet(params).save(failOnError: true)

        params.id = 1
        params.qty=1500

        controller.update()
        
        assert response.json.success
        assert CustomerOrderDet.list().size() == 1
        assert CustomerOrderDet.get(1).typeName == "CO"
        assert CustomerOrderDet.get(1).name == "001"
        assert CustomerOrderDet.get(1).sequence == 1
        assert CustomerOrderDet.get(1).qty == 1500
    }

    void testUpdateWithInccorectQtyData(){
        populateValidParams(params)
        def customerOrderDet = new CustomerOrderDet(params).save(failOnError: true)

        params.id = 1
        params.qty = 0

        controller.update()
        
        assertFalse response.json.success
        assert CustomerOrderDet.list().size() == 1
        assert CustomerOrderDet.get(1).typeName == "CO"
        assert CustomerOrderDet.get(1).name == "001"
        assert CustomerOrderDet.get(1).sequence == 1
        assert CustomerOrderDet.get(1).qty == 1000
    }

    void testUpdateWithForbiddenChangeOfTypeNameAndName(){
        populateValidParams(params)
        def customerOrderDet = new CustomerOrderDet(params).save(failOnError: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert CustomerOrderDet.list().size() == 1
        assert CustomerOrderDet.get(1).typeName == "CO"
        assert CustomerOrderDet.get(1).name == "001"
        assert CustomerOrderDet.get(1).sequence == 1
    }


    void testDelete(){
        populateValidParams(params)
        def customerOrderDet = new CustomerOrderDet(params).save(failOnError: true)

        params.id = 1
        controller.delete()
           
        assert response.json.success
        assert CustomerOrderDet.list().size() == 0
    }

}
