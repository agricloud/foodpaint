package foodpaint
import org.junit.*
import grails.test.mixin.*
import common.*

//主要測試的對象
@TestFor(MaterialSheetController)
//把所有測試中會使用到的domain & service
@Mock([MaterialSheet,MaterialSheetDet, ManufactureOrder,
       Item, Batch, Workstation, Supplier,
       Warehouse, WarehouseLocation, DomainService])

class MaterialSheetControllerTests {
    //開始測試前預備資料
    void setUp(){
        def testService = new TestService()
        testService.createTestMessage(messageSource)

        grailsApplication.config.grails.i18nType='mfg'
        MaterialSheet.metaClass.getGrailsApplication = {
            return grailsApplication
        }
        MaterialSheet.metaClass.getMessageSource = {
            return messageSource
        }

        def workstation1 = new Workstation(name:"workstation1",title:"生產線1").save(failOnError: true)
        def supplier1 = new Supplier(name:"supplier1",title:"供應商1",country:Country.TAIWAN).save(failOnError: true, flush: true)
    }

    def populateValidParams(params) {
        assert params != null
        params["typeName"] = 'MS'
        params["name"] = '00001'
        params["workstation.id"]=1
    }

    void testIndex(){
        //注入params值
        populateValidParams(params)

        //產生預設資料
        def materialSheet1 = new MaterialSheet(params).save(failOnError: true, flush: true)
        
        //呼叫Controller執行index()
        controller.index()
        //驗證結果
        assert response.json.data.size() == 1   
        assert response.json.total == 1   
        assert response.json.data[0].typeName == "MS"
        assert response.json.data[0].name == "00001"
    }

    void testShow(){

        populateValidParams(params)

        //產生預設資料
        def materialSheet1 = new MaterialSheet(params).save(failOnError: true, flush: true)
       
        //設定傳入的params值
        params["id"]=1

        //呼叫Controller執行show()
        controller.show()
        //驗證結果
        assert response.json.success
        assert response.json.data.class == "foodpaint.MaterialSheet" 
        assert response.json.data.typeName == "MS"
        assert response.json.data.name == "00001"

    }

    void testCreate() {
        
        controller.create()
        assert response.json.success
        assert response.json.data.class == "foodpaint.MaterialSheet"
    }

    void testSave(){

        //設定傳入的params值
        populateValidParams(params)

        controller.save()
        assert response.json.success
        assert MaterialSheet.list().size() == 1
        assert MaterialSheet.get(1).typeName == "MS"
        assert MaterialSheet.get(1).name == "00001"
    }

    void testSaveWithWorkstationAndSupplier(){
        populateValidParams(params)

        params["supplier.id"]= 1

        controller.save()
        
        assertFalse response.json.success
        assert MaterialSheet.list().size() == 0
    }

    void testUpdate() {

        populateValidParams(params)
        def materialSheet1 = new MaterialSheet(params).save(failOnError: true, flush: true)

        params["id"] = 1
        params["workstation.id"] = null
        params["supplier.id"] = 1
        controller.update()
        //執行結果應允許更新
        assert response.json.success == true
        assert MaterialSheet.get(1).typeName == "MS"
        assert MaterialSheet.get(1).name == "00001"
        assert MaterialSheet.get(1).workstation == null
        assert MaterialSheet.get(1).supplier.id == 1
    }

    void testUpdateWithTypeNameAndName(){
        populateValidParams(params)
        def materialSheet1 = new MaterialSheet(params).save(failOnError: true, flush: true)

        params.id = 1
        params.typeName= "AAA"

        controller.update()
        
        assertFalse response.json.success
        assert MaterialSheet.list().size() == 1
        assert MaterialSheet.get(1).typeName == "MS"
        assert MaterialSheet.get(1).name == "00001"
    }

    void testUpdateWithWorkstationAndSupplier(){
        populateValidParams(params)
        def materialSheet1 = new MaterialSheet(params).save(failOnError: true, flush: true)

        params.id = 1
        params["supplier.id"]= 1

        controller.update()
        
        assertFalse response.json.success
        assert MaterialSheet.list().size() == 1
        assert MaterialSheet.get(1).typeName == "MS"
        assert MaterialSheet.get(1).name == "00001"
        assert MaterialSheet.get(1).workstation.id == 1
        assert MaterialSheet.get(1).supplier == null
    }

    void testUpdateWithSupplierWhenSheetDetailExisted(){
        populateValidParams(params)

        def item1 = new Item(name:"item1",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def item2 = new Item(name:"item2",title:"item2",unit:"kg").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"batch1", item:item1).save(failOnError: true, flush: true)
        def batch2 = new Batch(name:"batch2", item:item2).save(failOnError: true, flush: true)
        def workstation1 = Workstation.get(1)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def warehouseLocation1 = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)
        
        def materialSheet1 = new MaterialSheet(params).save(failOnError: true, flush: true)
        def manufactureOrder1 = new ManufactureOrder(typeName:"MO",name:"00001",item:item1,qty:1000,batch:batch1,workstation:workstation1).save(failOnError: true, flush: true)
        def materialSheetDet11 = new MaterialSheetDet(materialSheet:materialSheet1,typeName:materialSheet1.typeName, name:materialSheet1.name, sequence:1, item:item2, batch:batch2,warehouse:warehouse1,warehouseLocation:warehouseLocation1, qty:100, manufactureOrder:manufactureOrder1).save(failOnError: true, flush: true)

        params.id = 1
        params["workstation.id"]=null
        params["supplier.id"] = 1

        controller.update()
        
        assertFalse response.json.success
        assert MaterialSheet.list().size() == 1
        assert MaterialSheet.get(1).typeName == "MS"
        assert MaterialSheet.get(1).name == "00001"
        assert MaterialSheet.get(1).workstation.id == 1
        assert MaterialSheet.get(1).supplier == null
    }

    void testDelete(){

        populateValidParams(params)
        def materialSheet1 = new MaterialSheet(params).save(failOnError: true, flush: true)

        params["id"]=1
        controller.delete()

        assert response.json.success == true
        assert MaterialSheet.list().size() == 0
    }
}
