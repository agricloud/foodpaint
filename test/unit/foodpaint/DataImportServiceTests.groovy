package foodpaint



import grails.test.mixin.*
import org.junit.*
import grails.converters.*
import foodpaint.view.*
import common.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DataImportService)
@Mock([
    Item, ItemView, 
    Customer, CustomerView,
    Warehouse,WarehouseLocation,
    Workstation,WorkstationView,
    Operation,OperationView,
    Supplier,SupplierView,
    Batch,
    CustomerOrder, CustomerOrderView,
    CustomerOrderDet, CustomerOrderDetView,
    PurchaseSheet, PurchaseSheetView,
    PurchaseSheetDet, PurchaseSheetDetView,
    StockInSheet, StockInSheetView,
    StockInSheetDet, StockInSheetDetView,
    OutSrcPurchaseSheet, OutSrcPurchaseSheetView,
    OutSrcPurchaseSheetDet, OutSrcPurchaseSheetDetView,
    ManufactureOrder,ManufactureOrderView,
    BatchRoute,ManufactureOrderRouteView,
    MaterialSheet,MaterialSheetView,
    MaterialSheetDet,MaterialSheetDetView, 
    BatchService, DomainService])
class DataImportServiceTests {

    void testItemImport() {

        // new ItemView(name:"410001",flag:5,title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)
        // new ItemView(name:"410002",flag:6,title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)
        // def itemViewXml = ItemView.list() as XML
        // println itemViewXml.toString()

        def xmlString = '''
<list>
  <itemView>
    <description>
      非基因轉殖品種
    </description>
    <importFlag>
      -1
    </importFlag>
    <name>
      410001
    </name>
    <spec>
      華珍甜玉米，高糖分、皮薄
    </spec>
    <title>
      華珍玉米
    </title>
    <unit>
      kg
    </unit>
  </itemView>
  <itemView>
    <description>
      非基因轉殖品種
    </description>
    <importFlag>
      -1
    </importFlag>
    <name>
      410002
    </name>
    <spec>
      華珍甜玉米，高糖分、皮薄
    </spec>
    <title>
      華珍玉米
    </title>
    <unit>
      kg
    </unit>
  </itemView>
</list>
        '''

    	service.doDataImport(xmlString)

    	assert Item.list().size() == 2 

    }

    void testCustomerImport() {

        // new CustomerView(name:"C01",title:"新鮮超市",email:"test@test.com").save(failOnError: true, flush: true)
        // new CustomerView(name:"C02",title:"頂好超市",address:"台北市忠孝東路999號").save(failOnError: true, flush: true)

        // def customerViewXml = CustomerView.list() as XML

        def xmlString = '''
<list>
  <customerView>
    <address>
      
    </address>
    <email>
      test@test.com
    </email>
    <importFlag>
      -1
    </importFlag>
    <name>
      C01
    </name>
    <title>
      新鮮超市
    </title>
  </customerView>
  <customerView>
    <address>
      台北市忠孝東路999號
    </address>
    <email>
      
    </email>
    <importFlag>
      -1
    </importFlag>
    <name>
      C02
    </name>
    <title>
      頂好超市
    </title>
  </customerView>
</list>
        '''

        service.doDataImport(xmlString)

        assert Customer.list().size() == 2 

    }

    void testWorkstationImport() {

        // new WorkstationView(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)
        // new WorkstationView(name:"workstation2",title:"慈心有機").save(failOnError: true, flush: true)

        // def viewXml = WorkstationView.list() as XML

        // println viewXml.toString()

        def xmlString = '''
<list>
  <workstationView>
    <importFlag>
      -1
    </importFlag>
    <name>
      workstation1
    </name>
    <title>
      民雄線A
    </title>
  </workstationView>
  <workstationView>
    <importFlag>
      -1
    </importFlag>
    <name>
      workstation2
    </name>
    <title>
      慈心有機
    </title>
  </workstationView>
</list>

        '''

        service.doDataImport(xmlString)

        assert Workstation.list().size() == 2 

    }

    void testOperationImport() {

        // new OperationView(name:"operation1",title:"施肥",description:"施肥肥").save(failOnError: true, flush: true)
        // new OperationView(name:"operation2",title:"翻土",description:"翻土土").save(failOnError: true, flush: true)

        // def viewXml = OperationView.list() as XML

        // println viewXml.toString()

        def xmlString = '''
<list>
  <operationView>
    <description>
      施肥肥
    </description>
    <importFlag>
      -1
    </importFlag>
    <name>
      operation1
    </name>
    <title>
      施肥
    </title>
  </operationView>
  <operationView>
    <description>
      翻土土
    </description>
    <importFlag>
      -1
    </importFlag>
    <name>
      operation2
    </name>
    <title>
      翻土
    </title>
  </operationView>
</list>
        '''
        service.doDataImport(xmlString)

        assert Operation.list().size() == 2 

    }


    void testSupplierImport() {

        // new SupplierView(name:"FJ01",title:"福智麻園",country:"TAIWAN").save(failOnError: true, flush: true)
        // new SupplierView(name:"LZ01",title:"里仁生機",country:"JAPAN").save(failOnError: true, flush: true)

        // def viewXml = SupplierView.list() as XML

        // println viewXml.toString()

        def xmlString = '''
<list>
  <supplierView>
    <importFlag>
      -1
    </importFlag>
    <name>
      FJ01
    </name>
    <title>
      福智麻園
    </title>
  </supplierView>
  <supplierView>
    <importFlag>
      -1
    </importFlag>
    <name>
      LZ01
    </name>
    <title>
      里仁生機
    </title>
  </supplierView>
</list>
        '''

        service.doDataImport(xmlString)

        assert Supplier.list().size() == 2 

    }


    void testCustomerOrderImport() {        
        // new CustomerOrderView(typeName:"A11",name:"98100900001").save(failOnError: true, flush: true)
        // new CustomerOrderView(typeName:"A11",name:"98100900002").save(failOnError: true, flush: true)

        // def customerOrderViewXml = CustomerOrderView.list() as XML
        // println customerOrderViewXml.toString()

        def xmlString = '''
<list>
  <customerOrderView id="1">
    <customerName />
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900001
    </name>
    <typeName>
      A11
    </typeName>
  </customerOrderView>
  <customerOrderView id="2">
    <customerName />
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900002
    </name>
    <typeName>
      A11
    </typeName>
  </customerOrderView>
</list>

        '''

        service.doDataImport(xmlString)

        assert CustomerOrder.list().size() == 2 

    }


    void testCustomerOrderDetImport() {
        new Item(name:"item",title:"item",unit:"kg").save(failOnError: true, flush: true)
        new CustomerOrder(typeName:"A11",name:"98100900003").save(failOnError: true, flush: true)
        // def customerOrderDetView = new CustomerOrderDetView(typeName: "A11", name: "98100900003",
        //     sequence:1, itemName : "item").save(failOnError: true, flush: true)
        // def customerOrderDetView2 = new CustomerOrderDetView(typeName: "A11", name: "98100900003",
        //     sequence:2, itemName : "item").save(failOnError: true, flush: true)


        // def customerOrderDetViewXml = CustomerOrderDetView.list() as XML
        // println customerOrderDetViewXml.toString()

        def xmlString = '''
<list>
  <customerOrderDetView id="1">
    <importFlag>
      -1
    </importFlag>
    <itemName>
      item
    </itemName>
    <name>
      98100900003
    </name>
    <sequence>
      1
    </sequence>
    <typeName>
      A11
    </typeName>
  </customerOrderDetView>
  <customerOrderDetView id="2">
    <importFlag>
      -1
    </importFlag>
    <itemName>
      item
    </itemName>
    <name>
      98100900003
    </name>
    <sequence>
      2
    </sequence>
    <typeName>
      A11
    </typeName>
  </customerOrderDetView>
</list>        
        '''

        service.doDataImport(xmlString)

        assert CustomerOrderDet.list().size() == 2 


    }

    void testPurchaseSheetImport() {
        
        new Supplier(name:"FJ01",title:"福智麻園",country:Country.TAIWAN).save(failOnError: true, flush: true)

        // new PurchaseSheetView(typeName:"B21",name:"98100900001",supplierName:"FJ01",incomingDate:"20091009",orderDate:"20091009").save(failOnError: true, flush: true)
        // new PurchaseSheetView(typeName:"B21",name:"98100900002",supplierName:"FJ01",incomingDate:"20091009",orderDate:"20091009").save(failOnError: true, flush: true)

        // def viewXml = PurchaseSheetView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <purchaseSheetView id="1">
    <importFlag>
      -1
    </importFlag>
    <incomingDate>
      20091009
    </incomingDate>
    <name>
      98100900001
    </name>
    <orderDate>
      20091009
    </orderDate>
    <supplierName>
      FJ01
    </supplierName>
    <typeName>
      B21
    </typeName>
  </purchaseSheetView>
  <purchaseSheetView id="2">
    <importFlag>
      -1
    </importFlag>
    <incomingDate>
      20091009
    </incomingDate>
    <name>
      98100900002
    </name>
    <orderDate>
      20091009
    </orderDate>
    <supplierName>
      FJ01
    </supplierName>
    <typeName>
      B21
    </typeName>
  </purchaseSheetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert PurchaseSheet.list().size() == 2 

    }

    void testPurchaseSheetDetImport() {
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        def workstation1 = new Workstation(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)
        def supplier1=new Supplier(name:"FJ01",title:"福智麻園",country:Country.TAIWAN).save(failOnError: true, flush: true)

        new PurchaseSheet(typeName:"B21",name:"98100900001",supplier:supplier1).save(failOnError: true, flush: true)
        
        new Item(name:"21006",title:"芝麻有機肥",unit:"kg").save(failOnError: true, flush: true)
        new Item(name:"21007",title:"黃豆有機肥",unit:"kg").save(failOnError: true, flush: true)

        // new PurchaseSheetDetView(typeName:"B21",name:"98100900001",sequence:1,itemName:"21006",batchName:"0927-21006",qty:10000).save(failOnError: true, flush: true)
        // new PurchaseSheetDetView(typeName:"B21",name:"98100900001",sequence:2,itemName:"21007",batchName:"0927-21007",qty:10000).save(failOnError: true, flush: true)
        
        // def viewXml = PurchaseSheetDetView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <purchaseSheetDetView id="1">
    <batchName>
      0927-21006
    </batchName>
    <importFlag>
      -1
    </importFlag>
    <itemName>
      21006
    </itemName>
    <name>
      98100900001
    </name>
    <warehouseName>
      warehouse1
    </warehouseName>
    <warehouseLocationName>
      warehouseLocation1
    </warehouseLocationName>
    <qty>
      10000
    </qty>
    <sequence>
      1
    </sequence>
    <typeName>
      B21
    </typeName>
  </purchaseSheetDetView>
  <purchaseSheetDetView id="2">
    <batchName>
      0927-21007
    </batchName>
    <importFlag>
      -1
    </importFlag>
    <itemName>
      21007
    </itemName>
    <name>
      98100900001
    </name>
    <warehouseName>
      warehouse1
    </warehouseName>
    <warehouseLocationName>
      warehouseLocation1
    </warehouseLocationName>
    <qty>
      10000
    </qty>
    <sequence>
      2
    </sequence>
    <typeName>
      B21
    </typeName>
  </purchaseSheetDetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert PurchaseSheetDet.list().size() == 2 

    }

    void testStockInSheetImport() {
        new Workstation(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)

        // new StockInSheetView(typeName:"BD31",name:"98100900001",workstationName:"workstation1", stockInDate: '20130101').save(failOnError: true, flush: true)
        // new StockInSheetView(typeName:"BD31",name:"98100900002",workstationName:"workstation1", stockInDate: '20130101').save(failOnError: true, flush: true)

        // def viewXml = StockInSheetView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <stockInSheetView id="1">
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900001
    </name>
    <stockInDate>
      20130101
    </stockInDate>
    <typeName>
      BD31
    </typeName>
    <workstationName>
      workstation1
    </workstationName>
  </stockInSheetView>
  <stockInSheetView id="2">
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900002
    </name>
    <stockInDate>
      20130101
    </stockInDate>
    <typeName>
      BD31
    </typeName>
    <workstationName>
      workstation1
    </workstationName>
  </stockInSheetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert StockInSheet.list().size() == 2 

    }

    void testStockInSheetDetImport() {
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        def workstation1 = new Workstation(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)
        
        def item1 = new Item(name:"410002",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def item6 = new Item(name:"31002",title:"玉米半成品",spec:"半成品測試",unit:"kg",description:"半成品測試").save(failOnError: true, flush: true)


        new ManufactureOrder(typeName:"C11",name:"98100900001",item:item1,qty:1000).save(failOnError: true, flush: true)
        new ManufactureOrder(typeName:"C11",name:"98100900002",item:item6,qty:1000).save(failOnError: true, flush: true)

        new StockInSheet(typeName:"BD31",name:"98100900001",workstation:workstation1, stockInDate: new Date()).save(failOnError: true, flush: true)
        new StockInSheet(typeName:"BD31",name:"98100900002",workstation:workstation1, stockInDate: new Date()).save(failOnError: true, flush: true)
        
        // new StockInSheetDetView(typeName:"BD31",name:"98100900001",sequence:1,batchName:"0927-31002",itemName:"31002",
        //         warehouse:"warehouse2", manufactureOrderTypeName:"C11",manufactureOrderName:"98100900002").save(failOnError: true, flush: true)        
        // new StockInSheetDetView(typeName:"BD31",name:"98100900002",sequence:1,batchName:"0927-410002",itemName:"410002",
        //         warehouse:"warehouse3", manufactureOrderTypeName:"C11",manufactureOrderName:"98100900001").save(failOnError: true, flush: true)

        // def viewXml = StockInSheetDetView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <stockInSheetDetView id="1">
    <batchName>
      0927-31002
    </batchName>
    <importFlag>
      -1
    </importFlag>
    <itemName>
      31002
    </itemName>
    <manufactureOrderName>
      98100900002
    </manufactureOrderName>
    <manufactureOrderTypeName>
      C11
    </manufactureOrderTypeName>
    <name>
      98100900001
    </name>
    <sequence>
      1
    </sequence>
    <stockLocationName />
    <typeName>
      BD31
    </typeName>
    <warehouseName>
      warehouse1
    </warehouseName>
    <warehouseLocationName>
      warehouseLocation1
    </warehouseLocationName>
  </stockInSheetDetView>
  <stockInSheetDetView id="2">
    <batchName>
      0927-410002
    </batchName>
    <importFlag>
      -1
    </importFlag>
    <itemName>
      410002
    </itemName>
    <manufactureOrderName>
      98100900001
    </manufactureOrderName>
    <manufactureOrderTypeName>
      C11
    </manufactureOrderTypeName>
    <name>
      98100900002
    </name>
    <sequence>
      1
    </sequence>
    <stockLocationName />
    <typeName>
      BD31
    </typeName>
    <warehouseName>
      warehouse1
    </warehouseName>
    <warehouseLocationName>
      warehouseLocation1
    </warehouseLocationName>
  </stockInSheetDetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert StockInSheetDet.list().size() == 2 

    }


    void testOutSrcPurchaseSheetImport() {
        new Supplier(name:"FJ01",title:"福智麻園",country:Country.TAIWAN).save(failOnError: true, flush: true)

        // new OutSrcPurchaseSheetView(typeName:"B32",name:"98100900001",supplierName:"FJ01", outSrcPurchaseDate: '20130101').save(failOnError: true, flush: true)
        // new OutSrcPurchaseSheetView(typeName:"B32",name:"98100900002",supplierName:"FJ01", outSrcPurchaseDate: '20130101').save(failOnError: true, flush: true)

        // def viewXml = OutSrcPurchaseSheetView.list() as XML
        // println viewXml.toString()
        def xmlString = '''
<list>
  <outSrcPurchaseSheetView id="1">
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900001
    </name>
    <outSrcPurchaseDate>
      20130101
    </outSrcPurchaseDate>
    <supplierName>
      FJ01
    </supplierName>
    <typeName>
      B32
    </typeName>
  </outSrcPurchaseSheetView>
  <outSrcPurchaseSheetView id="2">
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900002
    </name>
    <outSrcPurchaseDate>
      20130101
    </outSrcPurchaseDate>
    <supplierName>
      FJ01
    </supplierName>
    <typeName>
      B32
    </typeName>
  </outSrcPurchaseSheetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert OutSrcPurchaseSheet.list().size() == 2 
    }

    void testOutSrcPurchaseSheetDetImport() {
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        def supplier1 = new Supplier(name:"FJ01",title:"福智麻園",country:Country.TAIWAN).save(failOnError: true, flush: true)

        def item1 = new Item(name:"410002",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)

        new ManufactureOrder(typeName:"C11",name:"98100900002",item:item1,qty:1000).save(failOnError: true, flush: true)

        new OutSrcPurchaseSheet(typeName:"BD32",name:"98100900001",supplier:supplier1, outSrcPurchaseDate: new Date()).save(failOnError: true, flush: true)

        // new OutSrcPurchaseSheetDetView(typeName:"BD32",name:"98100900001",sequence:1,itemName:"410002",batchName:"0927-420002",
        //         qty:5000,manufactureOrderTypeName:"C11",manufactureOrderName:"98100900002").save(failOnError: true, flush: true)

        // def viewXml = OutSrcPurchaseSheetDetView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <outSrcPurchaseSheetDetView id="1">
    <batchName>
      0927-420002
    </batchName>
    <importFlag>
      -1
    </importFlag>
    <itemName>
      410002
    </itemName>
    <manufactureOrderName>
      98100900002
    </manufactureOrderName>
    <manufactureOrderTypeName>
      C11
    </manufactureOrderTypeName>
    <name>
      98100900001
    </name>
    <warehouseName>
      warehouse1
    </warehouseName>
    <warehouseLocationName>
      warehouseLocation1
    </warehouseLocationName>
    <qty>
      5000
    </qty>
    <sequence>
      1
    </sequence>
    <typeName>
      BD32
    </typeName>
  </outSrcPurchaseSheetDetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert OutSrcPurchaseSheetDet.list().size() == 1

    }


    void testManufactureOrderImport() {
        new Item(name:"410001",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)
        new Item(name:"410002",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true, flush: true)

        // new ManufactureOrderView(typeName:"C11",name:"98100900001",itemName:"410001",qty:1000, batchName: 'batch1').save(failOnError: true, flush: true)
        // new ManufactureOrderView(typeName:"C11",name:"98100900002",itemName:"410002",qty:5000, batchName: 'batch2').save(failOnError: true, flush: true)

        // def viewXml = ManufactureOrderView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <manufactureOrderView id="1">
    <batchName>
      batch1
    </batchName>
    <importFlag>
      -1
    </importFlag>
    <itemName>
      410001
    </itemName>
    <name>
      98100900001
    </name>
    <qty>
      1000
    </qty>
    <typeName>
      C11
    </typeName>
  </manufactureOrderView>
  <manufactureOrderView id="2">
    <batchName>
      batch2
    </batchName>
    <importFlag>
      -1
    </importFlag>
    <itemName>
      410002
    </itemName>
    <name>
      98100900002
    </name>
    <qty>
      5000
    </qty>
    <typeName>
      C11
    </typeName>
  </manufactureOrderView>
</list>
        '''

        service.doDataImport(xmlString)

        assert ManufactureOrder.list().size() == 2 

    }


    void testManufactureOrderRouteImport() {

        //廠內製令
        new Operation(name:"operation1",title:"施肥",description:"施肥肥",importFlag:1).save(failOnError: true, flush: true)
        
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        def workstation1 = new Workstation(name:"workstation1",title:"民雄線A",importFlag:1).save(failOnError: true, flush: true)

        def item1 = new Item(name:"410001",title:"華珍玉米1",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。",importFlag:1).save(failOnError: true, flush: true)

        def mo1 = new ManufactureOrder(typeName:"C11",name:"98100900001",item:item1,qty:1000,importFlag:1).save(failOnError: true, flush: true)

        def batch1 = new Batch(name:"0927-410001",item:item1,importFlag:1).save(failOnError: true, flush: true)

        def sis1 = new StockInSheet(typeName:"BD31",name:"98100900001",workstation:workstation1,importFlag:1, stockInDate: new Date()).save(failOnError: true, flush: true)
        
        new StockInSheetDet(typeName:"BD31",name:"98100900001",sequence:1,stockInSheet:sis1,
                batch:batch1,item:item1,warehouse:warehouse1, warehouseLocation:warehouseLocation1, manufactureOrder:mo1,importFlag:1).save(failOnError: true, flush: true)


        new Operation(name:"operation2",title:"翻土",description:"翻土土",importFlag:1).save(failOnError: true, flush: true)

        def supplier1 = new Supplier(name:"FJ01",title:"福智麻園",country:Country.TAIWAN,importFlag:1).save(failOnError: true, flush: true)

        def item2 = new Item(name:"410002",title:"華珍玉米2",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。",importFlag:1).save(failOnError: true, flush: true)

        def mo2 = new ManufactureOrder(typeName:"C11",name:"98100900002",item:item2,qty:1000,importFlag:1).save(failOnError: true, flush: true)

        def batch2 = new Batch(name:"0927-410002",item:item2,importFlag:1).save(failOnError: true, flush: true)  

        def osps1 = new OutSrcPurchaseSheet(typeName:"BD32",name:"98100900001",supplier:supplier1,importFlag:1, outSrcPurchaseDate: new Date()).save(failOnError: true, flush: true)

        new OutSrcPurchaseSheetDet(typeName:"BD32",name:"98100900001",sequence:1,outSrcPurchaseSheet:osps1,
                item:item2,warehouse:warehouse1, warehouseLocation:warehouseLocation1,batch:batch2,qty:5000,manufactureOrder:mo2,importFlag:1).save(failOnError: true, flush: true)

        // new ManufactureOrderRouteView(typeName:"C11",name:"98100900001",sequence:1,operationName:"operation1",makerType:1,makerName:"workstation1",importFlag:1).save(failOnError: true, flush: true)
        // new ManufactureOrderRouteView(typeName:"C11",name:"98100900002",sequence:1,operationName:"operation2",makerType:2,makerName:"FJ01",importFlag:1).save(failOnError: true, flush: true)

        // def viewXml = ManufactureOrderRouteView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <manufactureOrderRouteView id="1">
    <endDate />
    <importFlag>
      1
    </importFlag>
    <makerName>
      workstation1
    </makerName>
    <makerType>
      1
    </makerType>
    <name>
      98100900001
    </name>
    <operationName>
      operation1
    </operationName>
    <sequence>
      1
    </sequence>
    <startDate />
    <typeName>
      C11
    </typeName>
  </manufactureOrderRouteView>
  <manufactureOrderRouteView id="2">
    <endDate />
    <importFlag>
      1
    </importFlag>
    <makerName>
      FJ01
    </makerName>
    <makerType>
      2
    </makerType>
    <name>
      98100900002
    </name>
    <operationName>
      operation2
    </operationName>
    <sequence>
      1
    </sequence>
    <startDate />
    <typeName>
      C11
    </typeName>
  </manufactureOrderRouteView>
</list>
        '''

        service.doDataImport(xmlString)

        assert BatchRoute.list().size() == 2 

    }

    void testMaterialSheetImport() {
        new Workstation(name:"workstation1",title:"民雄線A").save(failOnError: true, flush: true)
        new Workstation(name:"workstation2",title:"慈心有機").save(failOnError: true, flush: true)

        // new MaterialSheetView(typeName:"D11",name:"98100900001",workstationName:"workstation1").save(failOnError: true, flush: true)
        // new MaterialSheetView(typeName:"D11",name:"98100900002",workstationName:"workstation2").save(failOnError: true, flush: true)

        // def viewXml = MaterialSheetView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <materialSheetView id="1">
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900001
    </name>
    <typeName>
      D11
    </typeName>
    <workstationName>
      workstation1
    </workstationName>
  </materialSheetView>
  <materialSheetView id="2">
    <importFlag>
      -1
    </importFlag>
    <name>
      98100900002
    </name>
    <typeName>
      D11
    </typeName>
    <workstationName>
      workstation2
    </workstationName>
  </materialSheetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert MaterialSheet.list().size() == 2 

    }

    void testMaterialSheetDetImport() {

        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        def i1=new Item(name:"410001",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種",importFlag:1).save(failOnError: true, flush: true)
        def item2=new Item(name:"21006",title:"芝麻有機肥",unit:"kg",importFlag:1).save(failOnError: true, flush: true)
        def item3=new Item(name:"21007",title:"黃豆有機肥",unit:"kg",importFlag:1).save(failOnError: true, flush: true)

        new Batch(name:"0927-21006",item:item2,importFlag:1).save(failOnError: true, flush: true)
        new Batch(name:"0927-21007",item:item3,importFlag:1).save(failOnError: true, flush: true)
            
        def mo = new ManufactureOrder(typeName:"C11",name:"98100900001",item:i1,qty:1000,importFlag:1).save(failOnError: true, flush: true)

        def w1=new Workstation(name:"workstation1",title:"民雄線A",importFlag:1).save(failOnError: true, flush: true)

        new MaterialSheet(typeName:"D11",name:"98100900001",workstation:w1,importFlag:1).save(failOnError: true, flush: true)

        // new MaterialSheetDetView(typeName:"D11",name:"98100900001",sequence:1,itemName:"21006",batchName:"0927-21006",
        //         manufactureOrderTypeName:"C11",manufactureOrderName:"98100900001",importFlag:1).save(failOnError: true, flush: true)
        // new MaterialSheetDetView(typeName:"D11",name:"98100900001",sequence:2,itemName:"21007",batchName:"0927-21007",
        //         manufactureOrderTypeName:"C11",manufactureOrderName:"98100900001",importFlag:1).save(failOnError: true, flush: true)

        // def viewXml = MaterialSheetDetView.list() as XML
        // println viewXml.toString()

        def xmlString = '''
<list>
  <materialSheetDetView id="1">
    <batchName>
      0927-21006
    </batchName>
    <importFlag>
      1
    </importFlag>
    <itemName>
      21006
    </itemName>
    <warehouseName>
      warehouse1
    </warehouseName>
    <warehouseLocationName>
      warehouseLocation1
    </warehouseLocationName>
    <manufactureOrderName>
      98100900001
    </manufactureOrderName>
    <manufactureOrderTypeName>
      C11
    </manufactureOrderTypeName>
    <name>
      98100900001
    </name>
    <sequence>
      1
    </sequence>
    <typeName>
      D11
    </typeName>
  </materialSheetDetView>
  <materialSheetDetView id="2">
    <batchName>
      0927-21007
    </batchName>
    <importFlag>
      1
    </importFlag>
    <itemName>
      21007
    </itemName>
    <warehouseName>
      warehouse1
    </warehouseName>
    <warehouseLocationName>
      warehouseLocation1
    </warehouseLocationName>
    <manufactureOrderName>
      98100900001
    </manufactureOrderName>
    <manufactureOrderTypeName>
      C11
    </manufactureOrderTypeName>
    <name>
      98100900001
    </name>
    <sequence>
      2
    </sequence>
    <typeName>
      D11
    </typeName>
  </materialSheetDetView>
</list>
        '''

        service.doDataImport(xmlString)

        assert MaterialSheetDet.list().size() == 2 
        assert mo.materialSheetDets.size() == 2 

    }

}
