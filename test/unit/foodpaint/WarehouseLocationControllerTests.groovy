package foodpaint

import common.*
import org.junit.*
import grails.test.mixin.*

@TestFor(WarehouseLocationController)
@Mock([Warehouse, WarehouseLocation, DomainService])
class WarehouseLocationControllerTests {

    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["warehouse.id"] = 1
        params["name"] = "warehouseLocation"
        params["title"] = "warehouseLocation"
    }

    void testIndex() {

        populateValidParams(params)
        def warehouseLocation = new WarehouseLocation(params).save(failOnError: true)
        controller.index()

        assert response.json.data.size() == 1
        assert response.json.total == 1   
        assert response.json.data[0].name == "warehouseLocation"
    }

    void testShow(){
        populateValidParams(params)
        def warehouseLocation = new WarehouseLocation(params).save(failOnError: true)

        params.id = 1
        controller.show()
        assert response.json.success
        assert response.json.data.class == "foodpaint.WarehouseLocation"
        assert response.json.data.name == "warehouseLocation"

    }

    void testCreate() {
        populateValidParams(params)
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.WarehouseLocation"
    }

    void testSave(){
        populateValidParams(params)
        controller.save()

        assert response.json.success
        assert WarehouseLocation.list().size() == 1
        assert WarehouseLocation.get(1).name == "warehouseLocation"
    }

    void testUpdate(){
        populateValidParams(params)
        def warehouseLocation = new WarehouseLocation(params).save(failOnError: true)

        params.id = 1
        params.title= "location1"

        controller.update()
        
        assert response.json.success
        assert WarehouseLocation.list().size() == 1
        assert WarehouseLocation.get(1).name == "warehouseLocation"
        assert WarehouseLocation.get(1).title == "location1"
    }

    void testDelete(){
        populateValidParams(params)
        def warehouseLocation = new WarehouseLocation(params).save(failOnError: true)

        params.id = 1
        controller.delete()
           
        assert response.json.success
        assert WarehouseLocation.list().size() == 0
    }

}
