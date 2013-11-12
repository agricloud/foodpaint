package foodpaint



import org.junit.*
import grails.test.mixin.*
import common.*

@TestFor(ManufactureOrderController)
@Mock([ManufactureOrder, Item, Batch, BatchService, DomainService])
class ManufactureOrderControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["nameType"] = 'MO'
        params["name"] = 'MO0001'
        params["qty"] = 123

        params["item.id"] = Item.findByName("item1").id

        params["batch.name"] = "batch1"
    }


    // void testCreate() {
    //     def model = controller.create()

    //     assert model.manufactureOrderInstance != null
    // }

    void testSave() {
        messageSource.addMessage("default.message.save.success", Locale.getDefault(), "測試成功")

        controller.metaClass.message = { message ->
           "default.message.save.success"
        }

        new Item(name: 'item1').save()
        populateValidParams(params)
        controller.save()

        assert ManufactureOrder.count() == 1
        assert ManufactureOrder.list().get(0).item.name == "item1"
        assert ManufactureOrder.list().get(0).batch.name == "batch1"
    }

    // void testShow() {
    //     controller.show()

    //     assert flash.message != null
    //     assert response.redirectedUrl == '/manufactureOrder/list'

    //     populateValidParams(params)
    //     def manufactureOrder = new ManufactureOrder(params)

    //     assert manufactureOrder.save() != null

    //     // params.id = manufactureOrder.id

    //     // def model = controller.show()

    //     // assert model.manufactureOrderInstance == manufactureOrder
    // }


    // void testUpdate() {
    //     controller.update()

    //     assert flash.message != null
    //     assert response.redirectedUrl == '/manufactureOrder/list'

    //     response.reset()

    //     populateValidParams(params)
    //     def manufactureOrder = new ManufactureOrder(params)

    //     assert manufactureOrder.save() != null

    //     // test invalid parameters in update
    //     params.id = manufactureOrder.id
    //     //TODO: add invalid values to params object

    //     controller.update()

    //     assert view == "/manufactureOrder/edit"
    //     assert model.manufactureOrderInstance != null

    //     manufactureOrder.clearErrors()

    //     populateValidParams(params)
    //     controller.update()

    //     assert response.redirectedUrl == "/manufactureOrder/show/$manufactureOrder.id"
    //     assert flash.message != null

    //     //test outdated version number
    //     response.reset()
    //     manufactureOrder.clearErrors()

    //     populateValidParams(params)
    //     params.id = manufactureOrder.id
    //     params.version = -1
    //     controller.update()

    //     assert view == "/manufactureOrder/edit"
    //     assert model.manufactureOrderInstance != null
    //     assert model.manufactureOrderInstance.errors.getFieldError('version')
    //     assert flash.message != null
    // }

    // void testDelete() {
    //     controller.delete()
    //     assert flash.message != null
    //     assert response.redirectedUrl == '/manufactureOrder/list'

    //     response.reset()

    //     populateValidParams(params)
    //     def manufactureOrder = new ManufactureOrder(params)

    //     assert manufactureOrder.save() != null
    //     assert ManufactureOrder.count() == 1

    //     params.id = manufactureOrder.id

    //     controller.delete()

    //     assert ManufactureOrder.count() == 0
    //     assert ManufactureOrder.get(manufactureOrder.id) == null
    //     assert response.redirectedUrl == '/manufactureOrder/list'
    // }
}
