package foodpaint

class TestService {


    def createStdTestData = {
        def item = new Item(name:"item1",title:"華珍玉米1",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true)

        def batch = new Batch(name:"batch1",item:item,dueDate:new Date(), 
                manufactureDate: new Date(), expirationDate: new Date(),expectQty:100).save(failOnError: true)
        
        def supplier = new Supplier(name:"supplier1",title:"廠商1",country:Country.SPAIN).save(failOnError: true)
        def customer = new Customer(name:"customer1",title:"客戶1").save(failOnError: true)
        def warehouse = new Warehouse(name:"warehouse1",title:"倉庫1").save(failOnError: true)
        def warehouseLocation = new WarehouseLocation(name:"warehouseLocation1",warehouse:warehouse,title:"儲位1").save(failOnError: true)
        def workstation = new Workstation(name:"workstation1",title:"檢驗站01").save(failOnError: true)
        def operation = new Operation(name:"operation1",title:"施肥1").save(failOnError: true)

        def itemRoute = new ItemRoute(item:item,sequence:1,operation:operation,workstation:workstation)
        item.addToItemRoutes(itemRoute).save(failOnError: true)

        def batchRoute = new BatchRoute(batch:batch,workstation:workstation,sequence:1,operation:operation)
        batch.addToBatchRoutes(batchRoute).save(failOnError: true)
 
    }
    

    def createBatchSource = {
        def mainBatch = Batch.get(1)
        def item = new Item(name:"item2",title:"橘子").save(failOnError: true)
        def batch = new Batch(name:"batch2",item:item).save(failOnError: true)
        new BatchSource(batch:mainBatch,childBatch:batch).save(failOnError: true)

    }

    def createInventoryData = {
        def item = Item.get(1)
        def batch = Batch.get(1)
        def warehouse = Warehouse.get(1)
        def warehouseLocation = WarehouseLocation.get(1)

        new Inventory(warehouse:warehouse,item:item).save(failOnError: true)
        new InventoryDetail(warehouse:warehouse,warehouseLocation:warehouseLocation,item:item,batch:batch).save(failOnError: true)

        def item2 = new Item(name:"item2",title:"華珍玉米2",spec:"華珍甜玉米，高糖分、皮薄",unit:"kg",description:"非基因轉殖品種").save(failOnError: true)
        def batch2 = new Batch(name:"batch2",item:item2,dueDate:new Date(), manufactureDate: new Date(), expirationDate: new Date(), remark: '備註').save(failOnError: true)
        def warehouse2 = new Warehouse(name:"warehouse2",title:"倉庫2").save(failOnError: true)
        def warehouseLocation2 = new WarehouseLocation(name:"warehouseLocation2",warehouse:warehouse2,title:"儲位2").save(failOnError: true)

        new Inventory(warehouse:warehouse2,item:item2).save(failOnError: true)
        new InventoryDetail(warehouse:warehouse2,warehouseLocation:warehouseLocation2,item:item2,batch:batch2).save(failOnError: true)

    }

    def createOilCaseData = {
        def site1 = new Site(name:"innovate",title:"創新有限公司").save(failOnError: true)

        def item1 = new Item(name:"olive",title:"橄欖果",spec:"",unit:"kg",description:"油橄欖樹果實").save(failOnError: true)
        def item2 = new Item(name:"extravirgin",title:"特級初榨橄欖油",spec:"特級初榨Extra Virgin",unit:"g",description:"頂級橄欖油").save(failOnError: true)
        def item3 = new Item(name:"c6h14",title:"己烷",spec:"有機己烷",unit:"ml",description:"用於萃取橄欖油").save(failOnError: true)
        def item4 = new Item(name:"c7h16",title:"庚烷",spec:"有機庚烷",unit:"ml",description:"用於萃取橄欖油").save(failOnError: true)
        def item5 = new Item(name:"chlorophyllin",title:"銅葉綠素",spec:"",unit:"g",description:"增色粉末").save(failOnError: true)        
        def item11 = new Item(name:"pure",title:"純橄欖油",spec:"調和初榨",unit:"g",description:"純橄欖油").save(failOnError: true)
        //def item13 = new Item(name:"item13",title:"精煉橄欖油",spec:"精煉油",unit:"g",description:"精煉橄欖油").save(failOnError: true)
        
        def supplier1 = new Supplier(name:"coniaciapaty",title:"CONIA CIAPATY橄欖園",description:"西班牙橄欖園",country:Country.SPAIN).save(failOnError: true)
        def supplier2 = new Supplier(name:"pinchia",title:"品佳化工",description:"",country:Country.TAIWAN).save(failOnError: true)
        def workstation1 = new Workstation(name:"oilworkstation",title:"橄欖油加工線",description:"橄欖油加工線").save(failOnError: true)
        def customer1 = new Customer(name:"freshmarket",title:"新鮮超市").save(failOnError: true)
        def warehouse1 = new Warehouse(name:"oilwarehouse1",title:"oil倉庫").save(failOnError: true)
        def warehouseLocation1 = new WarehouseLocation(name:"oilwarehouseLocation1",warehouse:warehouse1,title:"儲位1").save(failOnError: true)
        def warehouseLocation2 = new WarehouseLocation(name:"oilwarehouseLocation2",warehouse:warehouse1,title:"儲位2").save(failOnError: true)

        def batch1 = new Batch(name:"olive_0211",item:item1, manufactureDate: new Date(114,02,11), supplier:supplier1,country:Country.SPAIN, remark: '').save(failOnError: true)
        def batch2 = new Batch(name:"extravirgin_0215",item:item2, manufactureDate: new Date(114,02,15), supplier:supplier1,country:Country.SPAIN, remark: '').save(failOnError: true)
        def batch3 = new Batch(name:"c6h14_0213",item:item3, supplier:supplier2,country:Country.TAIWAN, remark: '').save(failOnError: true)
        def batch4 = new Batch(name:"c7h16_0213",item:item4, supplier:supplier2,country:Country.TAIWAN, remark: '').save(failOnError: true)
        def batch5 = new Batch(name:"chlorophyllin_0213",item:item5, supplier:supplier2,country:Country.TAIWAN, remark: '').save(failOnError: true)
        def batch11 = new Batch(name:"pure_0220",item:item11, manufactureDate: new Date(114,02,20), country:Country.TAIWAN, remark: '').save(failOnError: true)

        new BatchSource(batch:batch2,childBatch:batch1).save(failOnError: true)
        new BatchSource(batch:batch11,childBatch:batch2).save(failOnError: true)
        new BatchSource(batch:batch11,childBatch:batch3).save(failOnError: true)
        new BatchSource(batch:batch11,childBatch:batch4).save(failOnError: true)
        new BatchSource(batch:batch11,childBatch:batch5).save(failOnError: true)
    
        def operation1 = new Operation(name:"oiloperation1",title:"採收",description:"採收橄欖果實。").save(failOnError: true)
        def operation2 = new Operation(name:"oiloperation2",title:"清洗",description:"清洗橄欖果實，剔除樹枝、樹葉及損壞果實。").save(failOnError: true)
        def operation3 = new Operation(name:"oiloperation3",title:"研磨",description:"即冷壓法，溫控低於27度以下，將橄欖果實磨碎。").save(failOnError: true)
        def operation4 = new Operation(name:"oiloperation4",title:"攪拌",description:"攪拌碾碎後的果實，取出油脂。").save(failOnError: true)
        def operation5 = new Operation(name:"oiloperation5",title:"油水分離",description:"分離水、果渣等雜質，保留高純度橄欖油汁。").save(failOnError: true)
        def operation6 = new Operation(name:"oiloperation6",title:"二次溶劑萃取",description:"二次萃取橄欖油。").save(failOnError: true)
        def operation7 = new Operation(name:"oiloperation7",title:"脫色脫臭",description:"加熱混合活性白土，吸附處理消除色素；低壓脫臭，去除油脂中不好的氣味。").save(failOnError: true)
        def operation8 = new Operation(name:"oiloperation8",title:"增色",description:"添加染色劑。").save(failOnError: true)
        def operation9 = new Operation(name:"oiloperation9",title:"檢驗",description:"檢驗是否殘留化學毒素。").save(failOnError: true)
        def operation10 = new Operation(name:"oiloperation10",title:"濾淨",description:"於封裝前在恆溫恆濕環境控制下，除去更細小的雜質。").save(failOnError: true)
        def operation11 = new Operation(name:"oiloperation11",title:"封裝",description:"真空瓶裝封口。").save(failOnError: true)

        def batchRoute1 = new BatchRoute(batch:batch1,supplier:supplier1,sequence:1,operation:operation1,startDate:new Date(114,02,2),endDate:new Date(114,02,11))
        def batchRoute2 = new BatchRoute(batch:batch2,supplier:supplier1,sequence:1,operation:operation2,startDate:new Date(114,02,12),endDate:new Date(114,02,12))
        def batchRoute3 = new BatchRoute(batch:batch2,supplier:supplier1,sequence:2,operation:operation3,startDate:new Date(114,02,12),endDate:new Date(114,02,13))
        def batchRoute4 = new BatchRoute(batch:batch2,supplier:supplier1,sequence:3,operation:operation4,startDate:new Date(114,02,13),endDate:new Date(114,02,14))
        def batchRoute5 = new BatchRoute(batch:batch2,supplier:supplier1,sequence:4,operation:operation5,startDate:new Date(114,02,14),endDate:new Date(114,02,15))
        def batchRoute6 = new BatchRoute(batch:batch11,workstation:workstation1,sequence:1,operation:operation6,startDate:new Date(114,02,15),endDate:new Date(114,02,15))
        def batchRoute7 = new BatchRoute(batch:batch11,workstation:workstation1,sequence:2,operation:operation7,startDate:new Date(114,02,16),endDate:new Date(114,02,16))
        def batchRoute8 = new BatchRoute(batch:batch11,workstation:workstation1,sequence:3,operation:operation8,startDate:new Date(114,02,17),endDate:new Date(114,02,17))
        def batchRoute9 = new BatchRoute(batch:batch11,workstation:workstation1,sequence:4,operation:operation9,startDate:new Date(114,02,18),endDate:new Date(114,02,18))
        def batchRoute10 = new BatchRoute(batch:batch11,workstation:workstation1,sequence:5,operation:operation10,startDate:new Date(114,02,19),endDate:new Date(114,02,19))
        def batchRoute11 = new BatchRoute(batch:batch11,workstation:workstation1,sequence:6,operation:operation11,startDate:new Date(114,02,20),endDate:new Date(114,02,20))
        // batch1.addToBatchRoutes(batchRoute1).save(failOnError: true)
        // batch2.addToBatchRoutes(batchRoute2).save(failOnError: true)
        // batch2.addToBatchRoutes(batchRoute3).save(failOnError: true)
        // batch2.addToBatchRoutes(batchRoute4).save(failOnError: true)
        // batch2.addToBatchRoutes(batchRoute5).save(failOnError: true)
        // batch11.addToBatchRoutes(batchRoute6).save(failOnError: true)
        // batch11.addToBatchRoutes(batchRoute7).save(failOnError: true)
        // batch11.addToBatchRoutes(batchRoute8).save(failOnError: true)
        // batch11.addToBatchRoutes(batchRoute9).save(failOnError: true)
        // batch11.addToBatchRoutes(batchRoute10).save(failOnError: true)
        // batch11.addToBatchRoutes(batchRoute11).save(failOnError: true)

        //假設庫存 與案例單據無關
        def inventory11 = new Inventory(warehouse:warehouse1,item:item1,qty:1000).save(failOnError: true, flush: true)
        def inventoryDetail1111 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item1,batch:batch1,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1211 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation2,item:item1,batch:batch1,qty:500).save(failOnError: true, flush: true)
        def warehouse2=Warehouse.get(1)
        def inventoryDetail2211 = new InventoryDetail(warehouse:warehouse2,warehouseLocation:warehouseLocation2,item:item1,batch:batch1,qty:500).save(failOnError: true, flush: true)
        def inventory12 = new Inventory(warehouse:warehouse1,item:item2,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1122 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item2,batch:batch2,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1222 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation2,item:item2,batch:batch2,qty:100).save(failOnError: true, flush: true)
        def inventory13 = new Inventory(warehouse:warehouse1,item:item3,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1133 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item3,batch:batch3,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1233 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation2,item:item3,batch:batch3,qty:200).save(failOnError: true, flush: true)
        def inventory14 = new Inventory(warehouse:warehouse1,item:item4,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1144 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item4,batch:batch4,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1244 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation2,item:item4,batch:batch4,qty:300).save(failOnError: true, flush: true)
        def inventory15 = new Inventory(warehouse:warehouse1,item:item5,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1155 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item5,batch:batch5,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail1255 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation2,item:item5,batch:batch5,qty:400).save(failOnError: true, flush: true)
        def inventory111 = new Inventory(warehouse:warehouse1,item:item11,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail111111 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation1,item:item11,batch:batch11,qty:500).save(failOnError: true, flush: true)
        def inventoryDetail121111 = new InventoryDetail(warehouse:warehouse1,warehouseLocation:warehouseLocation2,item:item11,batch:batch11,qty:1000).save(failOnError: true, flush: true)

        //訂單
        def customerOrder1 = new CustomerOrder(typeName:"A11",name:"98100900001",customer:customer1).save(failOnError: true, flush: true)
        def customerOrderDet11 = new CustomerOrderDet(customerOrder:customerOrder1,typeName:"A11",name:"98100900001",sequence:1,item:item11,qty:3000).save(failOnError: true, flush: true)

        //製令
        def manufactureOrder1 = new ManufactureOrder(typeName:"C11",name:"98100900001",supplier:supplier1,item:item1,qty:1000000,batch:batch1).save(failOnError: true, flush: true)
        def manufactureOrder2 = new ManufactureOrder(typeName:"C11",name:"98100900002",supplier:supplier1,item:item2,qty:1000,batch:batch2).save(failOnError: true, flush: true)
        def manufactureOrder3 = new ManufactureOrder(typeName:"C11",name:"98100900003",workstation:workstation1,item:item11,qty:3000,customerOrderDet:customerOrderDet11,batch:batch11).save(failOnError: true, flush: true)
        
        //進貨單
        def purchaseSheet2 = new PurchaseSheet(typeName:"B21",name:"98100900002",supplier:supplier2).save(failOnError: true, flush: true)
        def purchaseSheetDet21 = new PurchaseSheetDet(purchaseSheet:purchaseSheet2,typeName:"B21",name:"98100900002",sequence:1,item:item3,batch:batch3,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1500).save(failOnError: true, flush: true)     
        def purchaseSheetDet22 = new PurchaseSheetDet(purchaseSheet:purchaseSheet2,typeName:"B21",name:"98100900002",sequence:2,item:item4,batch:batch4,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1500).save(failOnError: true, flush: true)     
        def purchaseSheetDet23 = new PurchaseSheetDet(purchaseSheet:purchaseSheet2,typeName:"B21",name:"98100900002",sequence:3,item:item5,batch:batch5,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:500).save(failOnError: true, flush: true)     

        //退貨單
        def purchaseReturnSheet2 = new PurchaseReturnSheet(typeName:"B22",name:"98100900002",supplier:supplier2).save(failOnError: true, flush: true)
        def purchaseReturnSheetDet21 = new PurchaseReturnSheetDet(purchaseReturnSheet:purchaseReturnSheet2,typeName:"B22",name:"98100900002",sequence:1,purchaseSheetDet:purchaseSheetDet21,item:item3,batch:batch3,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1500).save(failOnError: true, flush: true)     
        def purchaseReturnSheetDet22 = new PurchaseReturnSheetDet(purchaseReturnSheet:purchaseReturnSheet2,typeName:"B22",name:"98100900002",sequence:2,purchaseSheetDet:purchaseSheetDet22,item:item4,batch:batch4,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1500).save(failOnError: true, flush: true)     
                
        //領料單
        def materialSheet2 = new MaterialSheet(typeName:"D11",name:"98100900002",supplier:supplier1).save(failOnError: true, flush: true)
        def materialSheetDet21 = new MaterialSheetDet(materialSheet:materialSheet2,typeName:"D11",name:"98100900002",sequence:1,item:item1,batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1000000,manufactureOrder:manufactureOrder2).save(failOnError: true, flush: true)

        def materialSheet3 = new MaterialSheet(typeName:"D11",name:"98100900003",workstation:workstation1).save(failOnError: true, flush: true)
        def materialSheetDet31 = new MaterialSheetDet(materialSheet:materialSheet3,typeName:"D11",name:"98100900003",sequence:1,item:item2,batch:batch2,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1000,manufactureOrder:manufactureOrder3).save(failOnError: true, flush: true)
        def materialSheetDet32 = new MaterialSheetDet(materialSheet:materialSheet3,typeName:"D11",name:"98100900003",sequence:2,item:item3,batch:batch3,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1500,manufactureOrder:manufactureOrder3).save(failOnError: true, flush: true)
        def materialSheetDet33 = new MaterialSheetDet(materialSheet:materialSheet3,typeName:"D11",name:"98100900003",sequence:3,item:item4,batch:batch4,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1500,manufactureOrder:manufactureOrder3).save(failOnError: true, flush: true)
        def materialSheetDet34 = new MaterialSheetDet(materialSheet:materialSheet3,typeName:"D11",name:"98100900003",sequence:4,item:item5,batch:batch5,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:500,manufactureOrder:manufactureOrder3).save(failOnError: true, flush: true)

        //領退單
        def materialReturnSheet2 = new MaterialReturnSheet(typeName:"D12",name:"98100900002",supplier:supplier1).save(failOnError: true, flush: true)
        def materialReturnSheetDet21 = new MaterialReturnSheetDet(materialReturnSheet:materialReturnSheet2,typeName:"D12",name:"98100900002",sequence:1,materialSheetDet:materialSheetDet21,item:item1,batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1000000,manufactureOrder:manufactureOrder2).save(failOnError: true, flush: true)

        //入庫單
        def stockInSheet1 = new StockInSheet(typeName:"BD31",name:"98100900001",workstation:workstation1).save(failOnError: true, flush: true)
        def stockInSheetDet11 = new StockInSheetDet(typeName:"BD31",name:"98100900001",sequence:1,stockInSheet:stockInSheet1,batch:batch11,item:item11,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:3000,manufactureOrder:manufactureOrder3).save(failOnError: true, flush: true)
        for(int i=2;i<102;i++)
            new StockInSheetDet(typeName:"BD31",name:"98100900001",sequence:i,stockInSheet:stockInSheet1,item:item11,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:50*i,manufactureOrder:manufactureOrder3).save(failOnError: true, flush: true)
        
        //託外進貨單
        def outSrcPurchaseSheet1 = new OutSrcPurchaseSheet(typeName:"BD32",name:"98100900001",supplier:supplier1).save(failOnError: true, flush: true)
        def outSrcPurchaseSheetDet11 = new OutSrcPurchaseSheetDet(outSrcPurchaseSheet:outSrcPurchaseSheet1,typeName:"BD32",name:"98100900001",sequence:1,item:item1,batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1000000,manufactureOrder:manufactureOrder1).save(failOnError: true, flush: true)
        def outSrcPurchaseSheet2 = new OutSrcPurchaseSheet(typeName:"BD32",name:"98100900002",supplier:supplier1).save(failOnError: true, flush: true)
        def outSrcPurchaseSheetDet21 = new OutSrcPurchaseSheetDet(outSrcPurchaseSheet:outSrcPurchaseSheet2,typeName:"BD32",name:"98100900002",sequence:1,item:item2,batch:batch2,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1000,manufactureOrder:manufactureOrder2).save(failOnError: true, flush: true)

        //託外退貨單
        def outSrcPurchaseReturnSheet1 = new OutSrcPurchaseReturnSheet(typeName:"BDR32",name:"98100900001",supplier:supplier1).save(failOnError: true, flush: true)
        def outSrcPurchaseReturnSheetDet11 = new OutSrcPurchaseReturnSheetDet(outSrcPurchaseReturnSheet:outSrcPurchaseReturnSheet1,typeName:"BDR32",name:"98100900001",sequence:1,outSrcPurchaseSheetDet:outSrcPurchaseSheetDet11,item:item1,batch:batch1,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:1000000,outSrcPurchaseDate:new Date(114,02,20),manufactureOrder:manufactureOrder1).save(failOnError: true, flush: true)

        //銷貨單    
        def saleSheet1 = new SaleSheet(typeName:"A21",name:"98100900001",customer:customer1).save(failOnError: true, flush: true)
        def saleSheetDet11 = new SaleSheetDet(saleSheet:saleSheet1,typeName:"A21",name:"98100900001",sequence:1,item:item11,batch:batch11,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:3000,customerOrderDet:customerOrderDet11).save(failOnError: true, flush: true)
        
        //銷退單
        def saleReturnSheet1 = new SaleReturnSheet(typeName:"A22",name:"98100900001",customer:customer1).save(failOnError: true, flush: true)
        def saleReturnSheetDet11 = new SaleReturnSheetDet(saleReturnSheet:saleReturnSheet1,typeName:"A22",name:"98100900001",sequence:1,saleSheetDet:saleSheetDet11,item:item11,batch:batch11,warehouse:warehouse1,warehouseLocation:warehouseLocation1,qty:3000,customerOrderDet:customerOrderDet11).save(failOnError: true, flush: true)

    }

    def createTestMessage = { messageSource ->
        messageSource.addMessage("default.message.save.success", Locale.getDefault(), "儲存成功")
        messageSource.addMessage("default.message.save.failed", Locale.getDefault(), "儲存失敗")
        messageSource.addMessage("default.message.delete.success", Locale.getDefault(), "刪除成功")
        messageSource.addMessage("default.message.update.failed", Locale.getDefault(), "更新失敗")
        messageSource.addMessage("default.message.notfound", Locale.getDefault(), "查無資料") 

        messageSource.addMessage("batch.name.params.notfound", Locale.getDefault(), "批號未輸入")
        messageSource.addMessage("sheet.item.batch.item.not.equal", Locale.getDefault(), " {0} 品項與批號品項不符")
        messageSource.addMessage("sheet.item.manufactureOrder.item.batch.item.not.equal", Locale.getDefault(), " {0} 品項與製令品項、批號品項不符")
        messageSource.addMessage("inventory.quantity.not.enough", Locale.getDefault(),"{0}、{1} 庫存數量不足")
        messageSource.addMessage("inventoryDetail.quantity.not.enough", Locale.getDefault(), "{0}、{1}、{2}、{3} 庫存數量不足")
        
        messageSource.addMessage("country.TAIWAN.label", Locale.getDefault(), "台灣")
		messageSource.addMessage("country.JAPAN.label", Locale.getDefault(), "日本")
		messageSource.addMessage("country.CHINA.label", Locale.getDefault(), "中國")
		messageSource.addMessage("country.HONGKONG.label", Locale.getDefault(), "香港")
        messageSource.addMessage("country.KOREA.label", Locale.getDefault(), "韓國") 
        messageSource.addMessage("country.PHILIPPINES.label", Locale.getDefault(), "菲律賓") 
        messageSource.addMessage("country.AMERICA.label", Locale.getDefault(), "美國") 
        messageSource.addMessage("country.AUSTRALIA.label", Locale.getDefault(), "澳洲") 
        messageSource.addMessage("country.FRENCE.label", Locale.getDefault(), "法國") 
        messageSource.addMessage("country.BRITAIN.label", Locale.getDefault(), "英國") 
	
    }


}
