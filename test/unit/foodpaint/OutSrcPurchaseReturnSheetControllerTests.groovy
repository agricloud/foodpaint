package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(OutSrcPurchaseReturnSheetController)
//把所有測試中會使用到的domain & service
@Mock([OutSrcPurchaseReturnSheetDet, OutSrcPurchaseReturnSheet, 
       OutSrcPurchaseSheetDet, OutSrcPurchaseSheet, ManufactureOrder,
       Item, Batch, Supplier, Warehouse, WarehouseLocation, Inventory, InventoryDetail,
       BatchService, InventoryService, InventoryDetailService, DomainService])
class OutSrcPurchaseReturnSheetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        grailsApplication.config.grails.i18nType='mfg'
        OutSrcPurchaseReturnSheet.metaClass.getGrailsApplication = {
            return grailsApplication
        }
        OutSrcPurchaseReturnSheet.metaClass.getMessageSource = {
            return messageSource
        }

        def supplier1 = new Supplier(name:"supplier1",title:"供應商1",country:Country.TAIWAN).save(failOnError: true, flush: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["typeName"] = 'OSPRS'
        params["name"] = '00001'
        params["supplier.id"]=1
    }

    void testIndex(){
        populateValidParams(params)
        //產生預設資料
        def outSrcPurchaseReturnSheet1 = new OutSrcPurchaseReturnSheet(params).save(failOnError: true, flush: true)
        
        //呼叫tController執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1   
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "OSPRS"
        assert response.json.data[0].name == "00001"
    }

    void testShow(){
        populateValidParams(params)
        //產生預設資料
        def outSrcPurchaseReturnSheetDet1 = new OutSrcPurchaseReturnSheet(params).save(failOnError: true, flush: true)
        
        //設定傳入的params值
        params["id"]=1

        //呼叫Controller執行show()
        controller.show()
        //驗證結果
        assert response.json.success
        assert response.json.data.class == "foodpaint.OutSrcPurchaseReturnSheet" 
        assert response.json.data.typeName == "OSPRS"
        assert response.json.data.name == "00001"

    }

    void testCreate() {
        
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.OutSrcPurchaseReturnSheet"
    }


    void testSave(){

        //設定傳入的params值
        populateValidParams(params)

        controller.save()

        assert response.json.success
        assert OutSrcPurchaseReturnSheet.list().size() == 1
        assert OutSrcPurchaseReturnSheet.get(1).typeName == "OSPRS"
        assert OutSrcPurchaseReturnSheet.get(1).name == "00001"
    }

    void testUpdate() {

        populateValidParams(params)

        def outSrcPurchaseReturnSheet1 = new OutSrcPurchaseReturnSheet(params).save(failOnError: true, flush: true)
        def supplier2 = new Supplier(name:"supplier2",title:"供應商2",country:Country.TAIWAN).save(failOnError: true, flush: true)
        
        params["id"] = 1
        params["supplier.id"] = 2

        controller.update()
        //執行結果應允許更新
        assert response.json.success == true
        assert OutSrcPurchaseReturnSheet.get(1).typeName == "OSPRS"
        assert OutSrcPurchaseReturnSheet.get(1).name == "00001"
        assert OutSrcPurchaseReturnSheet.get(1).supplier.id == 2
    }

    void testUpdateWithTypeNameAndName(){
        populateValidParams(params)
        def outSrcPurchaseReturnSheet = new OutSrcPurchaseReturnSheet(params).save(failOnError: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert OutSrcPurchaseReturnSheet.list().size() == 1
        assert OutSrcPurchaseReturnSheet.get(1).typeName == "OSPRS"
        assert OutSrcPurchaseReturnSheet.get(1).name == "00001"
    }

    void testUpdateWithSupplierWhenSheetDetailExisted(){
        populateValidParams(params)
        def outSrcPurchaseReturnSheet1 = new OutSrcPurchaseReturnSheet(params).save(failOnError: true, flush: true)
        def supplier1 = Supplier.get(1)
        def supplier2 = new Supplier(name:"supplier2",title:"供應商2",country:Country.TAIWAN).save(failOnError: true, flush: true)
        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)     
        def manufactureOrder1 = new ManufactureOrder(typeName:"MO",name:"00001",item:item1,qty:1000,batch:batch1,supplier:supplier1).save(failOnError: true, flush: true)
        def outSrcPurchaseSheet1 = new OutSrcPurchaseSheet(typeName:"OSPS", name:"00001", sequence:1, supplier:supplier1).save(failOnError: true)
        def outSrcPurchaseSheetDet11 = new OutSrcPurchaseSheetDet(outSrcPurchaseSheet:outSrcPurchaseSheet1, typeName:outSrcPurchaseSheet1.typeName, name:outSrcPurchaseSheet1.name, sequence:1, item:item1,batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1, qty:100, manufactureOrder:manufactureOrder1).save(failOnError: true)
        def outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(outSrcPurchaseReturnSheet:outSrcPurchaseReturnSheet1,outSrcPurchaseSheetDet:outSrcPurchaseSheetDet11, typeName:params.typeName, name:params.name, sequence:1, item:item1,batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1, qty:100, manufactureOrder:manufactureOrder1).save(failOnError: true)

        params.id = 1
        params.supplier.id= 2

        controller.update()
        
        assertFalse response.json.success
        assert OutSrcPurchaseReturnSheet.list().size() == 1
        assert OutSrcPurchaseReturnSheet.get(1).typeName == "OSPRS"
        assert OutSrcPurchaseReturnSheet.get(1).name == "00001"
        assert OutSrcPurchaseReturnSheet.get(1).supplier.id == 1
    }

    void testDelete(){

        populateValidParams(params)

        def outSrcPurchaseReturnSheet1 = new OutSrcPurchaseReturnSheet(params).save(failOnError: true, flush: true)

        params["id"] = 1
        controller.delete()

        assert response.json.success == true
        assert OutSrcPurchaseReturnSheetDet.list().size() == 0

    }
}
