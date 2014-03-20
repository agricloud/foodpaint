package foodpaint



import org.junit.*
import grails.test.mixin.*
import common.*

@TestFor(PurchaseSheetDetController)
@Mock([PurchaseSheetDet, PurchaseSheet, Item, Batch, Warehouse, StorageLocation, Supplier, BatchService, DomainService])
class PurchaseSheetDetControllerTests {

    def populateValidParams(params) {
        assert params != null
        params["typeName"] = 'MO'
        params["name"] = 'MO0001'
        params["qty"] = 123

        params["item.id"] = Item.findByName("item1").id

        params["batch.name"] = "batch1"
    }

    void testUpdate() {
        messageSource.addMessage("default.message.save.success", Locale.getDefault(), "儲存成功")
        messageSource.addMessage("batch.name.params.notfound", Locale.getDefault(), "批號未輸入")
        messageSource.addMessage("sheet.item.batch.item.not.equal", Locale.getDefault(), " {0} 品項與批號品項不符")

        def item1 = new Item(name:"paint410002",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
        def item2 = new Item(name:"paintitem2",title:"橘子").save(failOnError: true, flush: true)
        def batch1 = new Batch(name:"paintbatch1", item:item1).save(failOnError: true, flush: true)
        def batch2 = new Batch(name:"paintbatch2", item:item2).save(failOnError: true, flush: true)
        def supplier1 = new Supplier(name:"paintFJ01",title:"福智麻園",country:Country.TAIWAN).save(failOnError: true, flush: true)
        def warehouse1 = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true, flush: true)
        def storageLocation1 = new StorageLocation(name:"storageLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true, flush: true)

        def purchaseSheet1 = new PurchaseSheet(typeName:"B21",name:"98100900001",supplier:supplier1).save(failOnError: true, flush: true)
        def purchaseSheetDet11 = new PurchaseSheetDet(purchaseSheet:purchaseSheet1,typeName:"B21",name:"98100900001",sequence:1,item:item1,warehouse:warehouse1,storageLocation:storageLocation1,batch:batch1,qty:10000).save(failOnError: true, flush: true)
        
        //給定錯誤的更新資料 批號品項與進貨品項不符
        params["purchaseSheet.id"] = 1
        params["id"] = 1
        params["typeName"] = "B21"
        params["name"] = "98100900001"
        params["sequence"] = 1
        params["batch.id"] = 2
        params["batch.name"] = "paintbatch2"
        params["item.id"] = 1
        params["qty"] = 500

        controller.update()
        //執行結果應不允許更新 因此資料不變
        assert PurchaseSheetDet.list().get(0).batch.name == "paintbatch1"
        assert PurchaseSheetDet.list().get(0).item.id == 1
        assert PurchaseSheetDet.list().get(0).qty == 10000
    }

}
