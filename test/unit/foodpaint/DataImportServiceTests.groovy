package foodpaint



import grails.test.mixin.*
import org.junit.*
import grails.converters.*
import foodpaint.view.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DataImportService)
@Mock([
    Item, ItemView, 
    CustomerOrder, CustomerOrderView,
    CustomerOrderDet, CustomerOrderDetView])
class DataImportServiceTests {



    void testItemImport() {

        new ItemView(name:"410001",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)
        new ItemView(name:"410002",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)

        def itemViewXml = ItemView.list() as XML

        println itemViewXml.toString()

    	service.doDataImport(itemViewXml.toString())

    	assert Item.list().size() == 2 

    }
    void testCustomerOrderImport() {

        
        new CustomerOrderView(typeName:"A11",name:"98100900001").save(failOnError: true, flush: true)
        new CustomerOrderView(typeName:"A11",name:"98100900002").save(failOnError: true, flush: true)


        def customerOrderViewXml = CustomerOrderView.list() as XML


        println customerOrderViewXml.toString()

        service.doDataImport(customerOrderViewXml.toString())

        assert CustomerOrder.list().size() == 2 

    }
    void testCustomerOrderDetImport() {
        new Item(name:"item",title:"item").save(failOnError: true, flush: true)
        
        new CustomerOrder(typeName:"A11",name:"98100900003").save(failOnError: true, flush: true)

        def customerOrderDetView = new CustomerOrderDetView(typeName: "A11", name: "98100900003", sequence:1)
        def customerOrderDetView2 = new CustomerOrderDetView(typeName: "A11", name: "98100900003", sequence:2)
        customerOrderDetView.itemName = "item"
        customerOrderDetView2.itemName = "item"
        customerOrderDetView.save(failOnError: true, flush: true)
        customerOrderDetView2.save(failOnError: true, flush: true)

        def customerOrderDetViewXml = CustomerOrderDetView.list() as XML


        println customerOrderDetViewXml.toString()

        service.doDataImport(customerOrderDetViewXml.toString())

        assert CustomerOrderDetView.list().size() == 2 

    }
    // void testBatchImport() {
    //     def writer = new StringWriter()
    //     def xml = new MarkupBuilder(writer)

    //     new Item(name:'item1').save()

    //     xml.root(){
    //         importTable('Batch')
    //         batch{
    //             name("batch1")
    //             // 日期格式
    //             dueDate(new Date())
    //             item{
    //                 name("item1")
    //             }
    //         }
    //         batch{
    //             name("batch2")
    //             item{
    //                 name("item1")
    //             }
    //         }
    //         batch{
    //             name("batch3")
    //             item{
    //                 name("item2")
    //             }
    //         }
    //     }


    //     service.doDataImport(writer.toString())


    //     assert Batch.list().size() == 2 

    // }
}
