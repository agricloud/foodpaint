package foodpaint



import org.junit.*
import grails.test.mixin.*
import common.*


@TestFor(ApiController)
@Mock([Item, Workstation, Operation, Supplier, Customer,Batch,BatchRoute,BatchSource])
class ApiControllerTests {

    void testExportData() {

        new Item(name: 'item1').save()
        
        controller.exportData()

        assert response.text

    }

}