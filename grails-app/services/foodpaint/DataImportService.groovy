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

    @XmlJavaTypeAdapter(GrailsCxfMapAdapter.class)
	Map doDataImport(@WebParam(name="xmlString")String xmlString){


		def result=[:]

		try {		
			def writer = new StringWriter()
		    def xml = new MarkupBuilder(writer)
			def records = new XmlParser().parseText(xmlString)

			def importClassList = [
				'itemView',
				'customerView',
				'customerOrderView',
				'customerOrderDetView'
			]

			def loopKey
			
		
			importClassList.each{

				if (records[it]){
					loopKey = it 
				}
			}

			def importClass = loopKey[0].toUpperCase() + loopKey[1..-1]
			def targetClass = importClass.replace('View','')


			def fields= grailsApplication.getDomainClass("foodpaint."+targetClass).persistentProperties.collect { it.name }

			// 動態實體化 domain class
			// GrailsDomainClass dc = grailsApplication.getDomainClass('foodpaint.'+importClass)
			
			// 以 item 為例， dc.clazz.FindByName == Item.FindByName
			// 建立物件：dc.clazz.newInstance() == new Item()
			// def newDomainObject = dc.clazz.newInstance()

			log.info "table: ${importClass} size:${records[loopKey].size()}"
			log.info "fields:${fields}"



			records[loopKey].eachWithIndex{ record, i ->

				if(i % 100 == 0 )log.info "import ${importClass}: ${i} / ${records[loopKey].size()}"

				//各 domain 需定義主鍵的索引
				def domain
				if(targetClass=='Item')
					domain=getItemInstance(record)
				if(targetClass=='Customer')
					domain=getCustomerInstance(record)
				if(targetClass=='CustomerOrder')
					domain=getCustomerOrderInstance(record)		
				if(targetClass=='CustomerOrderDet')
					domain=getCustomerOrderDetInstance(record)	

				// if(targetClass=='Batch')
				// 	domain=getBatchInstance(record)


				// 共用最後進行儲存
				if(domain){
					domain.properties=getDomainProperties(record, fields)
					// println domain as JSON
					domain.save(failOnError:true, flush: true)
				}

			}
			result.success=true
		}catch(e){
			log.error e.message
			result.success=false
			result.message=e.message
			throw e

		}

		return result





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
		// 	throw new Exception("batch.item.name:${itemName} is not exist")
		// }


		// batch

  //   }



    def private getDomainProperties(record, fields){
    	def props=[:]
    	fields.each{ field ->
			// println field+"====="+record[field].text()
			if(record[field] && record[field].text() )
				props[field]=record[field].text()
		}

		props

    }

}
