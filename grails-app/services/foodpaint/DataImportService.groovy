package foodpaint
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import javax.jws.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import org.grails.cxf.adapter.GrailsCxfMapAdapter
import groovy.xml.MarkupBuilder
import grails.converters.*

class DataImportService {
	static expose = ['cxf']
	
	def grailsApplication
	def messageSource
	def batchService

    @XmlJavaTypeAdapter(GrailsCxfMapAdapter.class)
	Map doDataImport(@WebParam(name="xmlString")String xmlString){

		def result=[:]
			
			def writer = new StringWriter()
		    def xml = new MarkupBuilder(writer)
			def records = new XmlParser().parseText(xmlString)

			def importClassList = [
				'itemView',
				'customerView',
				'workstationView',
				'operationView',
				'supplierView',
				'customerOrderView',
				'customerOrderDetView',
				'purchaseSheetView',
				'purchaseSheetDetView',
				'stockInSheetView',
				'stockInSheetDetView',
				'outSrcPurchaseSheetView',
				'outSrcPurchaseSheetDetView',
				'manufactureOrderView',
				'manufactureOrderRouteView',
				'materialSheetView',
				'materialSheetDetView'

			]

			def loopKey
			
		
			importClassList.each{

				if (records[it]){
					loopKey = it 
				}
			}

			def importClass = loopKey[0].toUpperCase() + loopKey[1..-1]
			def targetClass

			if(importClass!='ManufactureOrderRouteView'){
				targetClass = importClass.replace('View','')
			}
			else{
				targetClass = "BatchRoute"
			}

			def fields = grailsApplication.getDomainClass("foodpaint."+targetClass).persistentProperties.collect { it.name }

			log.info "table: ${importClass} size:${records[loopKey].size()}"
			log.info "fields:${fields}"



			records[loopKey].eachWithIndex{ record, i ->
				try {

					if(i % 100 == 0 )log.info "import ${importClass}: ${i} / ${records[loopKey].size()}"

					//各 domain 需定義主鍵的索引
					def domain
					if(targetClass=='Item')
						domain=getItemInstance(record)
					if(targetClass=='Customer')
						domain=getCustomerInstance(record)
					if(targetClass=='Workstation')
						domain=getWorkstationInstance(record)
					if(targetClass=='Operation')
						domain=getOperationInstance(record)
					if(targetClass=='Supplier')
						domain=getSupplierInstance(record)
					if(targetClass=='CustomerOrder')
						domain=getCustomerOrderInstance(record)		
					if(targetClass=='CustomerOrderDet')
						domain=getCustomerOrderDetInstance(record)
					if(targetClass=='PurchaseSheet')
						domain=getPurchaseSheetInstance(record)		
					if(targetClass=='PurchaseSheetDet')
						domain=getPurchaseSheetDetInstance(record)
					if(targetClass=='StockInSheet')
						domain=getStockInSheetInstance(record)		
					if(targetClass=='StockInSheetDet')
						domain=getStockInSheetDetInstance(record)
					if(targetClass=='OutSrcPurchaseSheet')
						domain=getOutSrcPurchaseSheetInstance(record)		
					if(targetClass=='OutSrcPurchaseSheetDet')
						domain=getOutSrcPurchaseSheetDetInstance(record)
					if(targetClass=='ManufactureOrder')
						domain=getManufactureOrderInstance(record)
					if(targetClass=='BatchRoute')
						domain=getBatchRouteInstance(record)	
					if(targetClass=='MaterialSheet')
						domain=getMaterialSheetInstance(record)		
					if(targetClass=='MaterialSheetDet')
						domain=getMaterialSheetDetInstance(record)	


					// 共用最後進行儲存
					if(domain){

						domain.importFlag=record.importFlag.text().toInteger()
						domain.properties=getDomainProperties(record, fields)
						// log.info domain as JSON

						if (!domain.validate() || !domain.save(flush: true)){
				            domain.errors.allErrors.each{ 
				                log.error messageSource.getMessage(it, Locale.getDefault())
				            }
						}

					}

				}catch(e){
					log.error e.message
				}

			}//end records
			result.success=true


		return result

	}



	def private getBatchRouteInstance(record){
		//從進貨單或託外進貨單中找出製令符合的
		//找出批號

		//log.info "製令製程單別單號"+record.typeName.text()+"/"+record.name.text()

		def sheets = StockInSheetDet.where{
			manufactureOrder.name == record.name.text() &&
			manufactureOrder.typeName == record.typeName.text()
		}

		

		if(!sheets.exists() || !sheets.list()[0].batch){
			sheets = OutSrcPurchaseSheetDet.where{
				manufactureOrder.name == record.name.text() &&
				manufactureOrder.typeName == record.typeName.text()
			}
		}

		//預計批號情況，無入庫單、託外進貨單應改查製令
		if(!sheets.exists()|| !sheets.list()[0].batch){
			sheets = ManufactureOrder.where{
				name == record.name.text() &&
				typeName == record.typeName.text()
			}
		}


		if(sheets){
			//之後可能有多張進貨單，應加入處理方式
			sheets.each{
				if(it.batch){
					def batch = it.batch
					def batchRoute = BatchRoute.findByBatchAndSequence(batch,record.sequence.text().toInteger())
					if(!batchRoute){
						batchRoute = new BatchRoute(batch:batch,sequence:record.sequence.text().toInteger())
					}

					batchRoute.importFlag = record.importFlag.text().toInteger()

					def operation = Operation.findByName(record.operationName.text())
					batchRoute.operation = operation

					//廠內製程
					if(record.makerType.text()=='1'){
						def workstation = Workstation.findByName(record.makerName.text())
						batchRoute.workstation = workstation
					}
					//託外製程
					if(record.makerType.text()=='2'){
						def supplier = Supplier.findByName(record.makerName.text())
						batchRoute.supplier = supplier
					}
					if(record.startDate.text().trim()){
						def startDate= Date.parse('yyyyMMdd',record.startDate.text())
						batchRoute.startDate = startDate
					}
					if(record.endDate.text().trim()){
						def endDate = Date.parse('yyyyMMdd',record.endDate.text())
						batchRoute.endDate = endDate
					}

					if (!batchRoute.validate() || !batchRoute.save(flush: true)){
						batchRoute.errors.allErrors.each{ err ->
							log.error messageSource.getMessage(err, Locale.getDefault())
						}
					}
					else
						return batchRoute
				}//end if
			}//end each
		}//end if
		else{
			log.error "製令製程："+record.typeName.text()+record.name.text()+record.sequence.text()+"查無相關單據批號"
		}

		return null
	}

	//基本資料
    def private getItemInstance(record) {

		def item = Item.findByName(record.name.text())

		if(!item){
			item=new Item(name:record.name.text())
		}

    	item

    }

    def private getCustomerInstance(record) {

		def customer = Customer.findByName(record.name.text())

		if(!customer){
			customer=new Customer(name:record.name.text())
		}

    	customer

    }

    def private getWorkstationInstance(record) {

		def object = Workstation.findByName(record.name.text())

		if(!object){
			object=new Workstation(name:record.name.text())
		}

    	object
    }

    def private getOperationInstance(record) {

		def object = Operation.findByName(record.name.text())

		if(!object){
			object=new Operation(name:record.name.text())
		}
		
    	object
    }

    def private getSupplierInstance(record) {

		def object = Supplier.findByName(record.name.text())

		if(!object){
			object=new Supplier(name:record.name.text())
		}
		
    	object
    }

    //單據
    def private getCustomerOrderInstance(record) {

		def customerOrder = CustomerOrder.findByNameAndTypeName(record.name.text(),record.typeName.text())

		if(!customerOrder){
			customerOrder=new CustomerOrder(name:record.name.text(), typeName:record.typeName.text())
		}

    	customerOrder

    }
    def private getCustomerOrderDetInstance(record) {

		def customerOrderDet = CustomerOrderDet.findByTypeNameAndNameAndSequence(
			record.typeName.text(), record.name.text(), record.sequence.text())


		if(!customerOrderDet){
			customerOrderDet=new CustomerOrderDet(
				typeName: record.typeName.text(), name: record.name.text(), sequence: record.sequence.text())

		}

		def customerOrder = CustomerOrder.findByTypeNameAndName(
			record.typeName.text(), record.name.text())

		def item = Item.findByName(record.itemName.text())


		customerOrderDet.item = item
		customerOrderDet.customerOrder = customerOrder

    	customerOrderDet

    }

    def private getPurchaseSheetInstance(record) {

		def object = PurchaseSheet.findByNameAndTypeName(record.name.text(),record.typeName.text())

		if(!object){
			object=new PurchaseSheet(name:record.name.text(), typeName:record.typeName.text())
		}

		def supplier =Supplier.findByName(record.supplierName.text())


		object.supplier = supplier

		//進貨日期、單據日期欄位 暫不匯入
		//日期轉換
		// println "dateXML==="+record.incomingDate.text()
		// def incomingDate= Date.parse('yyyyMMdd',record.incomingDate.text())
		// println "date==="+incomingDate
		// def orderDate = Date.parse('yyyyMMdd',record.orderDate.text())
		// object.incomingDate = incomingDate
		// object.orderDate = orderDate

    	object

    }

    def private getPurchaseSheetDetInstance(record) {

		def object = PurchaseSheetDet.findByTypeNameAndNameAndSequence(
			record.typeName.text(), record.name.text(), record.sequence.text())


		if(!object){
			object = new PurchaseSheetDet(
				typeName: record.typeName.text(), name: record.name.text(), sequence: record.sequence.text())

		}

		def purchaseSheet = PurchaseSheet.findByTypeNameAndName(
			record.typeName.text(), record.name.text())

		def item = Item.findByName(record.itemName.text())
		def warehouse = Warehouse.findByName(record.warehouseName.text())

		object.item = item
		object.warehouse = warehouse
		object.purchaseSheet = purchaseSheet
		

		//產生batch
		def batch = batchService.findOrCreateBatchInstanceByXml(record,object)


		object.batch = batch

    	object

    }

    def private getStockInSheetInstance(record) {

		def object = StockInSheet.findByNameAndTypeName(record.name.text(),record.typeName.text())

		if(!object){
			object=new StockInSheet(name:record.name.text(), typeName:record.typeName.text())
		}

		def workstation =Workstation.findByName(record.workstationName.text())

		object.workstation = workstation

		//入庫日期欄位 暫不匯入
		// if(record.stockInDate.text().trim()){
		// 	def stockInDate = Date.parse('yyyyMMdd',record.stockInDate.text())
		// 	object.stockInDate = stockInDate
		// }

    	object

    }

    def private getStockInSheetDetInstance(record) {

		def object = StockInSheetDet.findByTypeNameAndNameAndSequence(
			record.typeName.text(), record.name.text(), record.sequence.text())


		if(!object){
			object = new StockInSheetDet(
				typeName: record.typeName.text(), name: record.name.text(), sequence: record.sequence.text())

		}

		def stockInSheet = StockInSheet.findByTypeNameAndName(
			record.typeName.text(), record.name.text())

		def item = Item.findByName(record.itemName.text())
		def warehouse = Warehouse.findByName(record.warehouseName.text())

		def manufactureOrder = ManufactureOrder.findByNameAndTypeName(
				record.manufactureOrderName.text(),record.manufactureOrderTypeName.text())

		
		object.item = item
		object.warehouse = warehouse
		object.stockInSheet = stockInSheet
		object.manufactureOrder = manufactureOrder

		//產生batch
		def batch = batchService.findOrCreateBatchInstanceByXml(record,object)

		object.batch = batch

    	object

    }

    def private getOutSrcPurchaseSheetInstance(record) {
    	
		def object = OutSrcPurchaseSheet.findByNameAndTypeName(record.name.text(),record.typeName.text())

		if(!object){
			object=new OutSrcPurchaseSheet(name:record.name.text(), typeName:record.typeName.text())
		}

		def supplier =Supplier.findByName(record.supplierName.text())

		object.supplier = supplier
		
		//託外進貨日期欄位 暫不匯入
		// if(record.outSrcPurchaseDate.text().trim()){
		// 	def outSrcPurchaseDate = Date.parse('yyyyMMdd',record.outSrcPurchaseDate.text())
		// 	object.outSrcPurchaseDate = outSrcPurchaseDate
		// }

    	object

    }

    def private getOutSrcPurchaseSheetDetInstance(record) {

		def object = OutSrcPurchaseSheetDet.findByTypeNameAndNameAndSequence(
			record.typeName.text(), record.name.text(), record.sequence.text())


		if(!object){
			object = new OutSrcPurchaseSheetDet(
				typeName: record.typeName.text(), name: record.name.text(), sequence: record.sequence.text())

		}

		def outSrcPurchaseSheet = OutSrcPurchaseSheet.findByTypeNameAndName(
			record.typeName.text(), record.name.text())

		def item = Item.findByName(record.itemName.text())
		def warehouse = Warehouse.findByName(record.warehouseName.text())
		def manufactureOrder = ManufactureOrder.findByNameAndTypeName(
				record.manufactureOrderName.text(),record.manufactureOrderTypeName.text())

		
		object.item = item
		object.warehouse = warehouse
		object.outSrcPurchaseSheet = outSrcPurchaseSheet
		object.manufactureOrder = manufactureOrder

		//產生batch
		def batch = batchService.findOrCreateBatchInstanceByXml(record,object)


		object.batch = batch

    	object

    }

    def private getManufactureOrderInstance(record) {

		def object = ManufactureOrder.findByNameAndTypeName(record.name.text(),record.typeName.text())

		if(!object){
			object=new ManufactureOrder(name:record.name.text(), typeName:record.typeName.text())
		}

		object.qty = record.qty.text().toInteger();

		def item =Item.findByName(record.itemName.text())

		object.item = item

		def batch = Batch.findByName(record.batchName.text())

		if(!batch){
			batch = batchService.findOrCreateBatchInstanceByXml(record,object)
		}

		object.batch = batch

    	object

    }

    def private getMaterialSheetInstance(record) {

		def object = MaterialSheet.findByNameAndTypeName(record.name.text(),record.typeName.text())

		if(!object){
			object=new MaterialSheet(name:record.name.text(), typeName:record.typeName.text())
		}

		def workstation =Workstation.findByName(record.workstationName.text())
		def supplier =Supplier.findByName(record.supplierName.text())

		object.workstation = workstation
		object.supplier = supplier

    	object

    }

    def private getMaterialSheetDetInstance(record) {

		def object = MaterialSheetDet.findByTypeNameAndNameAndSequence(
			record.typeName.text(), record.name.text(), record.sequence.text())


		if(!object){
			object = new MaterialSheetDet(
				typeName: record.typeName.text(), name: record.name.text(), sequence: record.sequence.text())

		}

		def materialSheet = MaterialSheet.findByTypeNameAndName(
			record.typeName.text(), record.name.text())

		def item = Item.findByName(record.itemName.text())
		def warehouse = Warehouse.findByName(record.warehouseName.text())
		def batch = Batch.findByName(record.batchName.text())
		def manufactureOrder = ManufactureOrder.findByNameAndTypeName(
				record.manufactureOrderName.text(),record.manufactureOrderTypeName.text())

		//由於Job匯入階段Grails尚未自動產生關聯，在此處指定關聯。
		manufactureOrder.addToMaterialSheetDets(object).save()

		object.item = item
		object.warehouse = warehouse
		object.batch = batch
		object.materialSheet = materialSheet
		object.manufactureOrder = manufactureOrder

    	object

    }



    def private getDomainProperties(record, fields){
    	def props=[:]
    	fields.each{ field ->
			// println field+"====="+record[field].text()
			if(record[field] && record[field].text() && !field.contains("Date") && !field.contains("importFlag")){
				props[field]=record[field].text()
			}
		}

		props

    }

}
