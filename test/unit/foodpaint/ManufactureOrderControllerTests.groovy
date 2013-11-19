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

    void testSave() {
        messageSource.addMessage("default.message.save.success", Locale.getDefault(), "測試成功")

        new Item(name: 'item1').save()
        populateValidParams(params)
        controller.save()

        assert ManufactureOrder.count() == 1
        assert ManufactureOrder.list().get(0).item.name == "item1"
        assert ManufactureOrder.list().get(0).batch.name == "batch1"
    }

}
