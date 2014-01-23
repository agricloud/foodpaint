import foodpaint.*
import common.*
import foodpaint.view.*
import grails.converters.JSON

class BootStrap {
	def convertService
    def init = { servletContext ->

    	// 預設時區，避免 json 轉換自動扣除 8 小時(台灣 +8:00)
    	TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

    	jsonParseDefine()

		environments {
			development {
				
				def item1 = new Item(name:"paint410002",title:"華珍玉米",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種 (Non-Genetically Modifie) 生長強健，特別耐熱、耐濕及抗倒伏，抗病毒病、葉斑病、螟蟲， 果穗整齊飽滿，著粒完整，穗粒淡黃色， 皮非常薄(有無皮的感覺)，脆嫩香甜，品質非常優良。 糖分保持力較長，較耐貯運。").save(failOnError: true, flush: true)
				def item2 = new Item(name:"paintitem2",title:"橘子").save(failOnError: true, flush: true)
				def item3 = new Item(name:"paintitem3",title:"柚子").save(failOnError: true, flush: true)
				def item4 = new Item(name:"paintitem4",title:"balasun").save(failOnError: true, flush: true)
				def item5 = new Item(name:"paintitem5",title:"colasun").save(failOnError: true, flush: true)
				def item6 = new Item(name:"paintitem6",title:"番茄").save(failOnError: true, flush: true)

				def supplier1 = new Supplier(name:"paintFJ01",title:"福智麻園",country:Country.TAIWAN).save(failOnError: true, flush: true)
				def supplier2 = new Supplier(name:"paintLZ01",title:"里仁生機",country:Country.JAPAN).save(failOnError: true, flush: true)
				def supplier3 = new Supplier(name:"paintZS01",title:"慈心有機",country:Country.TAIWAN).save(failOnError: true, flush: true)

				def workstation1 = new Workstation(name:"paintworkstation1",title:"民雄線A").save(failOnError: true, flush: true)
				def workstation2 = new Workstation(name:"paintworkstation2",title:"慈心有機").save(failOnError: true, flush: true)
				
				def customer1 = new Customer(name:"paintC01",title:"新鮮超市").save(failOnError: true, flush: true)
				def customer2 = new Customer(name:"paintC02",title:"頂好超市").save(failOnError: true, flush: true)

				def operation1=new Operation(name:"paintoperation1",title:"施肥").save(failOnError: true, flush: true)
				def operation2=new Operation(name:"paintoperation2",title:"翻土").save(failOnError: true, flush: true)
				def operation3=new Operation(name:"paintoperation3",title:"病蟲害防治").save(failOnError: true, flush: true)
				def operation4=new Operation(name:"paintoperation4",title:"檢驗").save(failOnError: true, flush: true)



				//訂單
				def customerOrder1 = new CustomerOrder(typeName:"A11",name:"98100900001",customer:customer1).save(failOnError: true, flush: true)
				def customerOrderDet11 = new CustomerOrderDet(customerOrder:customerOrder1,typeName:"A11",name:"98100900001",sequence:1,item:item1,qty:3000).save(failOnError: true, flush: true)

				//製令
				def manufactureOrder1 = new ManufactureOrder(typeName:"C11",name:"98100900001",item:item1,qty:1000).save(failOnError: true, flush: true)
				def manufactureOrder2 = new ManufactureOrder(typeName:"C11",name:"98100900002",item:item1,qty:5000,customerOrderDet:customerOrderDet11).save(failOnError: true, flush: true)

				def batch1 = new Batch(name:"paintbatch1", item:item1).save(failOnError: true, flush: true)
				def batch2 = new Batch(name:"paintbatch2", item:item2).save(failOnError: true, flush: true)
				def batch3 = new Batch(name:"paintbatch3", item:item3).save(failOnError: true, flush: true)
				def batch4 = new Batch(name:"paintbatch4", item:item4).save(failOnError: true, flush: true)
				def batch5 = new Batch(name:"paintbatch5", item:item5).save(failOnError: true, flush: true)
				def batch6 = new Batch(name:"paintbatch6", item:item6).save(failOnError: true, flush: true)
				
				//進貨單
				def purchaseSheet1 = new PurchaseSheet(typeName:"B21",name:"98100900001",supplier:supplier1).save(failOnError: true, flush: true)
				def purchaseSheetDet11 = new PurchaseSheetDet(purchaseSheet:purchaseSheet1,typeName:"B21",name:"98100900001",sequence:1,item:item2,batch:batch2,qty:10000).save(failOnError: true, flush: true)
				def purchaseSheetDet12 = new PurchaseSheetDet(purchaseSheet:purchaseSheet1,typeName:"B21",name:"98100900001",sequence:2,item:item3,batch:batch3,qty:10000).save(failOnError: true, flush: true)
				def purchaseSheetDet13 = new PurchaseSheetDet(purchaseSheet:purchaseSheet1,typeName:"B21",name:"98100900001",sequence:3,item:item4,batch:batch4,qty:10000).save(failOnError: true, flush: true)
				def purchaseSheet2 = new PurchaseSheet(typeName:"B21",name:"98100900002",supplier:supplier2).save(failOnError: true, flush: true)
				def purchaseSheetDet21 = new PurchaseSheetDet(purchaseSheet:purchaseSheet2,typeName:"B21",name:"98100900002",sequence:1,item:item5,batch:batch5,qty:10000).save(failOnError: true, flush: true)		
				def purchaseSheetDet22 = new PurchaseSheetDet(purchaseSheet:purchaseSheet2,typeName:"B21",name:"98100900002",sequence:2,item:item6,batch:batch6,qty:10000).save(failOnError: true, flush: true)		
						
				//領料單
				def materialSheet1 = new MaterialSheet(typeName:"D11",name:"98100900001",workstation:workstation1).save(failOnError: true, flush: true)
				def materialSheetDet11 = new MaterialSheetDet(materialSheet:materialSheet1,typeName:"D11",name:"98100900001",sequence:1,item:item2,batch:batch2,manufactureOrder:manufactureOrder1).save(failOnError: true, flush: true)
				def materialSheetDet12 = new MaterialSheetDet(materialSheet:materialSheet1,typeName:"D11",name:"98100900001",sequence:2,item:item3,batch:batch3,manufactureOrder:manufactureOrder1).save(failOnError: true, flush: true)
				def materialSheetDet13 = new MaterialSheetDet(materialSheet:materialSheet1,typeName:"D11",name:"98100900001",sequence:3,item:item4,batch:batch4,manufactureOrder:manufactureOrder1).save(failOnError: true, flush: true)
				def materialSheet2 = new MaterialSheet(typeName:"D11",name:"98100900002",workstation:workstation2).save(failOnError: true, flush: true)
				def materialSheetDet21 = new MaterialSheetDet(materialSheet:materialSheet2,typeName:"D11",name:"98100900002",sequence:1,item:item5,batch:batch5,manufactureOrder:manufactureOrder2).save(failOnError: true, flush: true)
				def materialSheetDet22 = new MaterialSheetDet(materialSheet:materialSheet2,typeName:"D11",name:"98100900002",sequence:2,item:item6,batch:batch6,manufactureOrder:manufactureOrder2).save(failOnError: true, flush: true)
				
				//入庫單
				def stockInSheet1 = new StockInSheet(typeName:"BD31",name:"98100900001",workstation:workstation1).save(failOnError: true, flush: true)
				def stockInSheetDet11 = new StockInSheetDet(typeName:"BD31",name:"98100900001",sequence:1,stockInSheet:stockInSheet1,batch:batch1,item:item1,warehouse:"warehouse2",manufactureOrder:manufactureOrder1).save(failOnError: true, flush: true)
				def stockInSheet2 = new StockInSheet(typeName:"BD31",name:"98100900002",workstation:workstation1).save(failOnError: true, flush: true)
				def stockInSheetDet21 = new StockInSheetDet(typeName:"BD31",name:"98100900002",sequence:1,stockInSheet:stockInSheet2,batch:batch1,item:item1,warehouse:"warehouse3",manufactureOrder:manufactureOrder2).save(failOnError: true, flush: true)
				
				//託外進貨單
				def outSrcPurchaseSheet1 = new OutSrcPurchaseSheet(typeName:"BD32",name:"98100900001",supplier:supplier3).save(failOnError: true, flush: true)
				def outSrcPurchaseSheetDet11 = new OutSrcPurchaseSheetDet(outSrcPurchaseSheet:outSrcPurchaseSheet1,typeName:"BD32",name:"98100900001",sequence:1,item:item1,batch:batch1,qty:5000,manufactureOrder:manufactureOrder2).save(failOnError: true, flush: true)

				// //銷貨單	
				// def saleSheet1 = new SaleSheet(typeName:"A21",name:"98100900001",customer:customer1).save(failOnError: true, flush: true)
				// def saleSheetDet11 = new SaleSheetDet(saleSheet:saleSheet1,sequence:1,item:item1,batch:batch1,qty:3000,customerOrderDet:customerOrderDet11).save(failOnError: true, flush: true)
				
				

			}
		}

    }
    def destroy = {
    }

    private jsonParseDefine(){
        // JSON.registerObjectMarshaller(User) {
        //     convertService.userParseJson(it)
        // }
        JSON.registerObjectMarshaller(Customer) {
            convertService.customerParseJson(it)
        }
        JSON.registerObjectMarshaller(Batch) {
            convertService.batchParseJson(it)
        }
        JSON.registerObjectMarshaller(BatchRoute) {
            convertService.batchRouteParseJson(it)
        }
        JSON.registerObjectMarshaller(BatchSource) {
            convertService.batchSourceParseJson(it)
        }
        JSON.registerObjectMarshaller(Item) {
            convertService.itemParseJson(it)
        }
        JSON.registerObjectMarshaller(ItemRoute) {
            convertService.itemRouteParseJson(it)
        }
        JSON.registerObjectMarshaller(Workstation) {
            convertService.workstationParseJson(it)
        }
        JSON.registerObjectMarshaller(Supplier) {
            convertService.supplierParseJson(it)
        }
        JSON.registerObjectMarshaller(Operation) {
            convertService.operationParseJson(it)
        }
        //sheets
        JSON.registerObjectMarshaller(CustomerOrder) {
            convertService.customerOrderParseJson(it)
        }
        JSON.registerObjectMarshaller(CustomerOrderDet) {
            convertService.customerOrderDetParseJson(it)
        }
        JSON.registerObjectMarshaller(ManufactureOrder) {
            convertService.manufactureOrderParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialSheet) {
            convertService.materialSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(MaterialSheetDet) {
            convertService.materialSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(PurchaseSheet) {
            convertService.purchaseSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(PurchaseSheetDet) {
            convertService.purchaseSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(StockInSheet) {
            convertService.stockInSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(StockInSheetDet) {
            convertService.stockInSheetDetParseJson(it)
        }
        JSON.registerObjectMarshaller(OutSrcPurchaseSheet) {
            convertService.outSrcPurchaseSheetParseJson(it)
        }
        JSON.registerObjectMarshaller(OutSrcPurchaseSheetDet) {
            convertService.outSrcPurchaseSheetDetParseJson(it)
        }


    }
}
