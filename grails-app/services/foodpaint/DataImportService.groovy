package foodpaint
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import javax.jws.*
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import org.grails.cxf.adapter.GrailsCxfMapAdapter
import groovy.xml.MarkupBuilder
import grails.converters.*
    /*
    資料匯入 api 以 domain 的結構來設計
    <?xml version="1.0" encoding="UTF-8"?>
		<root>
			<importTable>item</importTable>
			<item>
				<name></name>
				<title></title>
				...
				<unit></unit>
			</item>
			...
			<item>
				<name></name>
				<title></title>
				...
				<unit></unit>
			</item>
		</root>

	如果欄位屬於 doamin 
    <?xml version="1.0" encoding="UTF-8"?>
		<root>
			<importTable>batch</importTable>
			<batch>
				<item>
					<name>itemA<name>
				</item>
			</batch>
		</root>
    */

class DataImportService {
	static expose = ['cxf']
	def grailsApplication
	def messageSource

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
	
			// 動態實體化 domain class
			// GrailsDomainClass dc = grailsApplication.getDomainClass('foodpaint.'+importClass)
			
			// 以 item 為例， dc.clazz.FindByName == Item.FindByName
			// 建立物件：dc.clazz.newInstance() == new Item()
			// def newDomainObject = dc.clazz.newInstance()

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

					// if(targetClass=='Batch')
					// 	domain=getBatchInstance(record)


					// 共用最後進行儲存
					if(domain){

						domain.importFlag=record.importFlag.text().toInteger()
						domain.properties=getDomainProperties(record, fields)
						log.info domain as JSON

						if (!domain.validate() || !domain.save(flush: true)){
				            domain.errors.allErrors.each{ 
				                log.error messageSource.getMessage(it, Locale.getDefault())
				            }
						}

					}

				}catch(e){
					log.error e.message
				}

			}
			result.success=true


		return result

	}

	def private getBatchInstance(record, object){
		//如果批號欄位沒有資料則不新增batch
		//但單據仍會儲存
		if(record.batchName.text() || record.batchName.text().trim()){
			def batch = Batch.findByName(record.batchName.text())

			if(!batch){
				batch=new Batch(name:record.batchName.text())
				batch.importFlag = -1
			}

			batch.importFlag = batch.importFlag++
			batch.item = object.item

			if(object.instanceOf(PurchaseSheetDet)){
				batch.supplier = object.purchaseSheet.supplier
			}
			if(object.instanceOf(StockInSheetDet)){
				//batch.manufactureDate=//尚未定義此資料來源
			}
			if(object.instanceOf(OutSrcPurchaseSheetDet)){
				batch.supplier = object.outSrcPurchaseSheet.supplier
				//batch.manufactureDate=//尚未定義此資料來源
			}
			
			log.info batch as JSON

			if (!batch.validate() || !batch.save(flush: true)){
				batch.errors.allErrors.each{ 
					log.error messageSource.getMessage(it, Locale.getDefault())
				}
			}
			else
				return batch
		}
		else
			return null
	}

	def private getBatchRouteInstance(record){
		//從進貨單或託外進貨單中找出製令符合的
		//找出批號

		//log.info "製令製程單別單號"+record.typeName.text()+"/"+record.name.text()
		def sheets = StockInSheetDet.where{
			manufactureOrder.name == record.name.text() &&
			manufactureOrder.typeName == record.typeName.text()
		}

		if(!sheets || sheets.count()==0){
			sheets = OutSrcPurchaseSheetDet.where{
				manufactureOrder.name == record.name.text() &&
				manufactureOrder.typeName == record.typeName.text()
			}
		}

		if(sheets){
			sheets.each{
				//log.info "入庫單、託外進貨單單別單號"+it.typeName+"/"+it.name
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

					def startDate= Date.parse('yyyyMMdd',record.startDate.text())
					def endDate = Date.parse('yyyyMMdd',record.endDate.text())

					batchRoute.startDate = startDate
					batchRoute.endDate = endDate

					log.info batchRoute as JSON

					if (!batchRoute.validate() || !batchRoute.save(flush: true)){
						batchRoute.errors.allErrors.each{ 
							log.error messageSource.getMessage(it, Locale.getDefault())
						}
					}
					else
						return batchRoute
				}//end if
			}//end each
		}//end if

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

		//日期轉換
		// println "dateXML==="+record.incomingDate.text()
		def incomingDate= Date.parse('yyyyMMdd',record.incomingDate.text())
		// println "date==="+incomingDate
		def orderDate = Date.parse('yyyyMMdd',record.orderDate.text())

		object.incomingDate = incomingDate
		object.orderDate = orderDate

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

		
		object.item = item
		object.purchaseSheet = purchaseSheet

	
		//產生batch
		def batch = getBatchInstance(record,object)

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

		def manufactureOrder = ManufactureOrder.findByNameAndTypeName(
				record.manufactureOrderName.text(),record.manufactureOrderTypeName.text())

		
		object.item = item
		object.stockInSheet = stockInSheet
		object.manufactureOrder = manufactureOrder

		//產生batch
		def batch = getBatchInstance(record,object)


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

		def manufactureOrder = ManufactureOrder.findByNameAndTypeName(
				record.manufactureOrderName.text(),record.manufactureOrderTypeName.text())

		
		object.item = item
		object.outSrcPurchaseSheet = outSrcPurchaseSheet
		object.manufactureOrder = manufactureOrder

		//產生batch
		def batch = getBatchInstance(record,object)


		object.batch = batch

    	object

    }

    def private getManufactureOrderInstance(record) {

		def object = ManufactureOrder.findByNameAndTypeName(record.name.text(),record.typeName.text())

		if(!object){
			object=new ManufactureOrder(name:record.name.text(), typeName:record.typeName.text())
		}

		def item =Item.findByName(record.itemName.text())

		object.item = item

		def batch = Batch.findByName(record.batchName.text())

		if(!batch){
			batch = getBatchInstance(record,object)
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


		object.workstation = workstation

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
		def batch = Batch.findByName(record.batchName.text())
		def manufactureOrder = ManufactureOrder.findByNameAndTypeName(
				record.manufactureOrderName.text(),record.manufactureOrderTypeName.text())



		object.item = item
		object.batch = batch
		object.materialSheet = materialSheet
		object.manufactureOrder = manufactureOrder

    	object

    }

  //   def private getBatchInstance(record){



		// def batch = Batch.findByName(record.name.text())

		// if(!batch){
		// 	batch=new Batch(name:record.name.text())
		// }


		// def itemName=record.item.name.text();
		// def item=Item.findByName(itemName)
		
		// // 處理品項關連
		// if(item){
		// 	batch.item=item
		// }else {
		// }


		// batch

  //   }



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
