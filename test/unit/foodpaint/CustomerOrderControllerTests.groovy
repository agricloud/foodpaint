package foodpaint

import common.*
import org.junit.*
import grails.test.mixin.*

@TestFor(CustomerOrderController)
@Mock([Item, Customer, CustomerOrder, CustomerOrderDet, DomainService])
class CustomerOrderControllerTests {

    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)
        def customer1 = new Customer(name:"customer1",title:"客戶1").save(failOnError: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["typeName"] = "CO"
        params["name"] = "001"
        params["customer.id"] = 1
    }
 
    void testIndex() {

        populateValidParams(params)
        def customerOrder = new CustomerOrder(params).save(failOnError: true)
        controller.index()

        assert response.json.data.size() == 1
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "CO"
        assert response.json.data[0].name == "001"
    }

    void testShow(){
        populateValidParams(params)
        def customerOrder = new CustomerOrder(params).save(failOnError: true)

        params.id = 1
        controller.show()
        assert response.json.success
        assert response.json.data.class == "foodpaint.CustomerOrder"
        assert response.json.data.typeName == "CO"
        assert response.json.data.name == "001"

    }

    void testCreate() {
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.CustomerOrder"
    }

    void testSave(){
        populateValidParams(params)
        controller.save()

        assert response.json.success
        assert CustomerOrder.list().size() == 1
        assert CustomerOrder.get(1).typeName == "CO"
        assert CustomerOrder.get(1).name == "001"
    }

    void testUpdate(){
        populateValidParams(params)
        def customerOrder = new CustomerOrder(params).save(failOnError: true)
        def customer2 = new Customer(name:"customer2",title:"客戶2").save(failOnError: true)

        params.id = 1
        params.customer.id= 2

        controller.update()
        
        assert response.json.success
        assert CustomerOrder.list().size() == 1
        assert CustomerOrder.get(1).typeName == "CO"
        assert CustomerOrder.get(1).name == "001"
    }

    void testUpdateWithTypeNameAndName(){
        populateValidParams(params)
        def customerOrder = new CustomerOrder(params).save(failOnError: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert CustomerOrder.list().size() == 1
        assert CustomerOrder.get(1).typeName == "CO"
        assert CustomerOrder.get(1).name == "001"
    }

    void testUpdateWithCustomerWhenSheetDetailExisted(){
        populateValidParams(params)
        def customerOrder = new CustomerOrder(params).save(failOnError: true)
        def customer2 = new Customer(name:"customer2",title:"客戶2").save(failOnError: true)
        def item = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def customerOrderDet = new CustomerOrderDet(customerOrder:customerOrder, typeName:params.typeName, name:params.name, sequence:1, item:item, qty:100.5).save(failOnError: true)

        params.id = 1
        params.customer.id= 2

        controller.update()
        
        assertFalse response.json.success
        assert CustomerOrder.list().size() == 1
        assert CustomerOrder.get(1).typeName == "CO"
        assert CustomerOrder.get(1).name == "001"
        assert CustomerOrder.get(1).customer.id == 1
    }

    void testDelete(){
        populateValidParams(params)
        def customerOrder = new CustomerOrder(params).save(failOnError: true)

        params.id = 1
        controller.delete()
           
        assert response.json.success
        assert CustomerOrder.list().size() == 0
    }

}
