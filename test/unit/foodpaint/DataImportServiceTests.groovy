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
    Workstation,WorkstationView,
    Operation,OperationView,
    Supplier,SupplierView,
    Batch,
    CustomerOrder, CustomerOrderView,
    CustomerOrderDet, CustomerOrderDetView,
    ManufactureOrder,ManufactureOrderView,
    MaterialSheet,MaterialSheetView,
    MaterialSheetDet,MaterialSheetDetView])
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

    void testWorkstationImport() {

        new WorkstationView(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)
        new WorkstationView(name:"workstation2",title:"慈心有機").save(failOnError: true, flush: true)

        def viewXml = WorkstationView.list() as XML

        println viewXml.toString()

        service.doDataImport(viewXml.toString())

        assert Workstation.list().size() == 2 

    }

    void testOperationImport() {

        new OperationView(name:"operation1",title:"施肥",description:"施肥肥").save(failOnError: true, flush: true)
        new OperationView(name:"operation2",title:"翻土",description:"翻土土").save(failOnError: true, flush: true)

        def viewXml = OperationView.list() as XML

        println viewXml.toString()

        service.doDataImport(viewXml.toString())

        assert Operation.list().size() == 2 

    }


    void testSupplierImport() {

        new SupplierView(name:"FJ01",title:"福智麻園",country:"TAIWAN").save(failOnError: true, flush: true)
        new SupplierView(name:"LZ01",title:"里仁生機",country:"JAPAN").save(failOnError: true, flush: true)

        def viewXml = SupplierView.list() as XML

        println viewXml.toString()

        service.doDataImport(viewXml.toString())

        assert Supplier.list().size() == 2 

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

        def customerOrderDetView = new CustomerOrderDetView(typeName: "A11", name: "98100900003",
            sequence:1, itemName : "item").save(failOnError: true, flush: true)
        def customerOrderDetView2 = new CustomerOrderDetView(typeName: "A11", name: "98100900003",
            sequence:2, itemName : "item").save(failOnError: true, flush: true)


        def customerOrderDetViewXml = CustomerOrderDetView.list() as XML


        println customerOrderDetViewXml.toString()

        service.doDataImport(customerOrderDetViewXml.toString())

        assert CustomerOrderDet.list().size() == 2 


    }

    void testManufactureOrderImport() {
        new Item(name:"410001",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)
        new Item(name:"410002",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)

        new ManufactureOrderView(typeName:"C11",name:"98100900001",itemName:"410001",qty:1000).save(failOnError: true, flush: true)
        new ManufactureOrderView(typeName:"C11",name:"98100900002",itemName:"410002",qty:5000).save(failOnError: true, flush: true)


        def viewXml = ManufactureOrderView.list() as XML


        println viewXml.toString()

        service.doDataImport(viewXml.toString())

        assert ManufactureOrder.list().size() == 2 

    }

    void testMaterialSheetImport() {
        new Workstation(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)
        new Workstation(name:"workstation2",title:"慈心有機").save(failOnError: true, flush: true)


        new MaterialSheetView(typeName:"D11",name:"98100900001",workstationName:"workstation1").save(failOnError: true, flush: true)
        new MaterialSheetView(typeName:"D11",name:"98100900002",workstationName:"workstation2").save(failOnError: true, flush: true)

        def viewXml = MaterialSheetView.list() as XML


        println viewXml.toString()

        service.doDataImport(viewXml.toString())

        assert MaterialSheet.list().size() == 2 

    }

    void testMaterialSheetDetImport() {

        def i1=new Item(name:"410001",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)
        def item2=new Item(name:"21006",title:"芝麻有機肥").save(failOnError: true, flush: true)
        def item3=new Item(name:"21007",title:"黃豆有機肥").save(failOnError: true, flush: true)

        new Batch(name:"0927-21006",item:item2).save(failOnError: true, flush: true)
        new Batch(name:"0927-21007",item:item3).save(failOnError: true, flush: true)
            


        new ManufactureOrder(typeName:"C11",name:"98100900001",item:i1,qty:1000).save(failOnError: true, flush: true)

        def w1=new Workstation(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)

        new MaterialSheet(typeName:"D11",name:"98100900001",workstation:w1).save(failOnError: true, flush: true)

        new MaterialSheetDetView(typeName:"D11",name:"98100900001",sequence:1,itemName:"21006",batchName:"0927-21006",
                manufactureOrderTypeName:"C11",manufactureOrderName:"98100900001").save(failOnError: true, flush: true)
        new MaterialSheetDetView(typeName:"D11",name:"98100900001",sequence:2,itemName:"21007",batchName:"0927-21007",
                manufactureOrderTypeName:"C11",manufactureOrderName:"98100900001").save(failOnError: true, flush: true)

        def viewXml = MaterialSheetDetView.list() as XML


        println viewXml.toString()

        service.doDataImport(viewXml.toString())

        assert MaterialSheetDet.list().size() == 2 

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
