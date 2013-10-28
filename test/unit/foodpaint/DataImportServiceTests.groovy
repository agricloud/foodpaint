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
    Customer, CustomerView,
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

    void testCustomerImport() {

        new CustomerView(name:"C01",title:"新鮮超市",email:"test@test.com").save(failOnError: true, flush: true)
        new CustomerView(name:"C02",title:"頂好超市",address:"台北市忠孝東路999號").save(failOnError: true, flush: true)

        def customerViewXml = CustomerView.list() as XML

        println customerViewXml.toString()

        service.doDataImport(customerViewXml.toString())

        assert Customer.list().size() == 2 

    }
    /*
    //最後做 需檢查ＥＲＰ國家別建置方式
    void testSupplierImport() {

        new SupplierView(name:"FJ01",title:"福智麻園",country:"台灣").save(failOnError: true, flush: true)
        new SupplierView().save(failOnError: true, flush: true)

        def itemViewXml = ItemView.list() as XML

        println itemViewXml.toString()

        service.doDataImport(itemViewXml.toString())

        assert Item.list().size() == 2 

    }
    */

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

        def customerOrderDetView = new CustomerOrderDetView(typeName: "A11", name: "98100900003",
            sequence:1, itemName : "item").save(failOnError: true, flush: true)
        def customerOrderDetView2 = new CustomerOrderDetView(typeName: "A11", name: "98100900003",
            sequence:2, itemName : "item").save(failOnError: true, flush: true)


        def customerOrderDetViewXml = CustomerOrderDetView.list() as XML


        println customerOrderDetViewXml.toString()

        service.doDataImport(customerOrderDetViewXml.toString())

        assert CustomerOrderDet.list().size() == 2 


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
