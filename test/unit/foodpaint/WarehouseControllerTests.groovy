package foodpaint

import common.*
import org.junit.*
import grails.test.mixin.*

@TestFor(WarehouseController)
@Mock([Warehouse, DomainService])
class WarehouseControllerTests {

    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)
    }

    def populateValidParams(params) {
        assert params != null
        params["name"] = "warehouse"
        params["title"] = "warehouse"
    }

    void testIndex() {

        populateValidParams(params)
        def warehouse = new Warehouse(params).save(failOnError: true)
        controller.index()

        assert response.json.data.size() == 1
        assert response.json.total == 1   
        assert response.json.data[0].name == "warehouse"
    }

    void testShow(){
        populateValidParams(params)
        def warehouse = new Warehouse(params).save(failOnError: true)

        params.id = 1
        controller.show()
        assert response.json.success
        assert response.json.data.class == "foodpaint.Warehouse"
        assert response.json.data.name == "warehouse"

    }

    void testCreate() {
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.Warehouse"
    }

    void testSave(){
        populateValidParams(params)
        controller.save()

        assert response.json.success
        assert Warehouse.list().size() == 1
        assert Warehouse.get(1).name == "warehouse"
    }

    void testUpdate(){
        populateValidParams(params)
        def warehouse = new Warehouse(params).save(failOnError: true)

        params.id = 1
        params.title= "product"

        controller.update()
        
        assert response.json.success
        assert Warehouse.list().size() == 1
        assert Warehouse.get(1).name == "warehouse"
        assert Warehouse.get(1).title == "product"
    }

    void testDelete(){
        populateValidParams(params)
        def warehouse = new Warehouse(params).save(failOnError: true)

        params.id = 1
        controller.delete()
           
        assert response.json.success
        assert Warehouse.list().size() == 0
    }

}
