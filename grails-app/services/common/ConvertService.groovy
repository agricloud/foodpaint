package common
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class ConvertService {

	def enumService
	def dateService

    def getDomainFields(domainClassName) {
		def fields = []
		def d = new DefaultGrailsDomainClass(domainClassName)
	    d.persistentProperties.collect {
	        fields << it.name
	    }
	    fields
    }

    def domainParseMap(domainObject){
    	log.info domainObject.class
	    def result = [:]
	    getDomainFields(domainObject.class).each { field ->
	        result[field] = domainObject[field]
	    }
	    result.id=domainObject.id

	    result
    }

    def batchParseJson(batch){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(batch.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(batch.lastUpdated)
	    result.site = batch.site

	    result.id= batch.id
	    result.name=batch.name
	    result.item = batch.item
	    result.dueDate = dateService.formatWithISO8601(batch.dueDate)
	    result.expectQty = batch.expectQty
	    result.manufactureDate = dateService.formatWithISO8601(batch.manufactureDate)
	    // result.remark = batch.remark
	    if(batch.item){
	        result["item.id"] = batch.item.id
	        result["item.name"] = batch.item.name
	        result["item.title"] = batch.item.title
	        result["item.spec"] = batch.item.spec
	        result["item.unit"] = batch.item.unit
	        result["item.description"] = batch.item.description
	    }
        if(batch.supplier){
        	result.supplier = batch.supplier
            result["supplier.id"] = batch.supplier.id
            result["supplier.name"] = batch.supplier.name
            result["supplier.title"] = batch.supplier.title
        }
        if(batch.batchType){
        	def batchType=enumService.name(batch.batchType)
        	result["batchType"] = batchType.name
        	result["batchTypeTitle"] = batchType.title
        }
        if(batch.country){
        	def country=enumService.name(batch.country)
        	result["country"] = country.name
        	result["countryTitle"] = country.title
        }
	    
	    result
    }

    def batchSourceParseJson(batchSource){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(batchSource.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(batchSource.lastUpdated)
	    result.site = batchSource.site

	    result.id = batchSource.id

	    if(batchSource.batch){
		    result.batch = batchSource.batch
		    result["batch.id"]= batchSource.batch.id
		    result["batch.name"] =batchSource.batch.name
		    result["batch.dueDate"] = dateService.formatWithISO8601(batchSource.batch.dueDate)
		    result["batch.expectQty"] = batchSource.batch.expectQty
		    result["batch.manufactureDate"] = dateService.formatWithISO8601(batchSource.batch.manufactureDate)
		    // result["batch.remark"] = batchSource.batch.remark

		    if(batchSource.batch.item){
		        result["batch.item.id"] = batchSource.batch.item.id
		        result["batch.item.name"] = batchSource.batch.item.name
		        result["batch.item.title"] = batchSource.batch.item.title
		        result["batch.item.spec"] = batchSource.batch.item.spec
		        result["batch.item.unit"] = batchSource.batch.item.unit
		        result["batch.item.description"] = batchSource.batch.item.description
		    }
	        if(batchSource.batch.supplier){
	            result["batch.supplier.id"] = batchSource.batch.supplier.id
	            result["batch.batch.supplier.name"] = batchSource.batch.supplier.name
	            result["batch.supplier.title"] = batchSource.batch.supplier.title
	        }
	        if(batchSource.batch.country){
	        	def country=enumService.name(batchSource.batch.country)
	        	result["batch.country"] = country.name
	        	result["batch.countryTitle"] = country.title
	        }
	    }
	    if(batchSource.childBatch){
	     	result.childBatch = batchSource.childBatch
	        result["childBatch.id"]= batchSource.childBatch.id
		    result["childBatch.name"] =batchSource.childBatch.name
		    result["childBatch.dueDate"] = dateService.formatWithISO8601(batchSource.childBatch.dueDate)
		    result["childBatch.expectQty"] = batchSource.childBatch.expectQty
		    result["childBatch.manufactureDate"] = dateService.formatWithISO8601(batchSource.childBatch.manufactureDate)
		    // result["childBatch.remark"] = batchSource.childBatch.remark
		    if(batchSource.childBatch.item){
		        result["childBatch.item.id"] = batchSource.childBatch.item.id
		        result["childBatch.item.name"] = batchSource.childBatch.item.name
		        result["childBatch.item.title"] = batchSource.childBatch.item.title
		        result["childBatch.item.spec"] = batchSource.childBatch.item.spec
		        result["childBatch.item.unit"] = batchSource.childBatch.item.unit
		        result["childBatch.item.description"] = batchSource.childBatch.item.description
		    }
	        if(batchSource.childBatch.supplier){
	            result["childBatch.supplier.id"] = batchSource.childBatch.supplier.id
	            result["childBatch.supplier.name"] = batchSource.childBatch.supplier.name
	            result["childBatch.supplier.title"] = batchSource.childBatch.supplier.title
	        }
	        if(batchSource.childBatch.country){
	        	def country=enumService.name(batchSource.childBatch.country)
	        	result["childBatch.country"] = country.name
	        	result["childBatch.countryTitle"] = country.title
	        }
        }
	    
	    result
    }
    
    def itemParseJson(item){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(item.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(item.lastUpdated)
	    result.site = item.site

	    result.id = item.id
	    result.name = item.name
	    result.title = item.title
	    result.spec = item.spec
	    result.unit = item.unit
	    result.description = item.description

	    result
    }

    def itemRouteParseJson(itemRoute){
   		def result = [:]

   		result.dateCreated = dateService.formatWithISO8601(itemRoute.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(itemRoute.lastUpdated)
	    result.site = itemRoute.site

   		result.id=itemRoute.id
   		result.sequence=itemRoute.sequence
   		result.item=itemRoute.item
   		result["item.id"] = itemRoute.item.id
   		result["item.name"] = itemRoute.item.name
   		result["item.title"] = itemRoute.item.title
   		if(itemRoute.operation){
	   		result.operation=itemRoute.operation
	        result["operation.id"] = itemRoute.operation.id
	        result["operation.name"] = itemRoute.operation.name
	        result["operation.title"] = itemRoute.operation.title
	    }

        if(itemRoute.workstation){
        	result.workstation=itemRoute.workstation
            result["workstation.id"] = itemRoute.workstation.id
            result["workstation.name"] = itemRoute.workstation.name
            result["workstation.title"] = itemRoute.workstation.title
        }
        if(itemRoute.supplier){
        	result.supplier=itemRoute.supplier
            result["supplier.id"] = itemRoute.supplier.id
            result["supplier.name"] = itemRoute.supplier.name
            result["supplier.title"] = itemRoute.supplier.title
        }

        result
   	}

   	def warehouseParseJson(warehouse){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(warehouse.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(warehouse.lastUpdated)
	    result.site = warehouse.site

	    result.id = warehouse.id
	    result.name = warehouse.name
	    result.title = warehouse.title
	    if(warehouse.capacity == Double.POSITIVE_INFINITY)
	    	result.capacity = Double.MAX_VALUE
	    else
	    	result.capacity = warehouse.capacity
	    result.capacityUnit = warehouse.capacityUnit
	    result.remark = warehouse.remark

	    result
    }

    def warehouseLocationParseJson(warehouseLocation){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(warehouseLocation.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(warehouseLocation.lastUpdated)
	    result.site = warehouseLocation.site

	    result.id = warehouseLocation.id
	    result.name = warehouseLocation.name
	    result.title = warehouseLocation.title
	    if(warehouseLocation.capacity == Double.POSITIVE_INFINITY)
	    	result.capacity = Double.MAX_VALUE
	    else
	    	result.capacity = warehouseLocation.capacity
	    result.capacityUnit = warehouseLocation.capacityUnit
	    result.description = warehouseLocation.description
	    result.remark = warehouseLocation.remark

	    if(warehouseLocation.warehouse){
	    	result.warehouse = warehouseLocation.warehouse
		    result["warehouse.id"] = warehouseLocation.warehouse.id
		    result["warehouse.name"] = warehouseLocation.warehouse.name
		    result["warehouse.title"] = warehouseLocation.warehouse.title
		}

	    result
    }

   	def inventoryParseJson(inventory){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(inventory.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(inventory.lastUpdated)
	    result.site = inventory.site

	    result.id = inventory.id

	    if(inventory.warehouse){
	    	result.warehouse = inventory.warehouse
		    result["warehouse.id"] = inventory.warehouse.id
		    result["warehouse.name"] = inventory.warehouse.name
		    result["warehouse.title"] = inventory.warehouse.title
		}
		if(inventory.item){
			result.item = inventory.item
		    result["item.id"] = inventory.item.id
		    result["item.name"] = inventory.item.name
		    result["item.title"] = inventory.item.title
		    result["item.unit"] = inventory.item.unit
		}
	    result.qty = inventory.qty
	    result.lastInDate = dateService.formatWithISO8601(inventory.lastInDate)
	    result.lastOutDate = dateService.formatWithISO8601(inventory.lastOutDate)

	    result
    }

    def inventoryDetailParseJson(inventoryDetail){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(inventoryDetail.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(inventoryDetail.lastUpdated)
	    result.site = inventoryDetail.site

	    result.id = inventoryDetail.id

	    if(inventoryDetail.warehouse){
	    	result.warehouse = inventoryDetail.warehouse
		    result["warehouse.id"] = inventoryDetail.warehouse.id
		    result["warehouse.name"] = inventoryDetail.warehouse.name
		    result["warehouse.title"] = inventoryDetail.warehouse.title
		}
		if(inventoryDetail.warehouseLocation){
	    	result.warehouseLocation = inventoryDetail.warehouseLocation
		    result["warehouseLocation.id"] = inventoryDetail.warehouseLocation.id
		    result["warehouseLocation.name"] = inventoryDetail.warehouseLocation.name
		    result["warehouseLocation.title"] = inventoryDetail.warehouseLocation.title
		}
		if(inventoryDetail.item){
			result.item = inventoryDetail.item
		    result["item.id"] = inventoryDetail.item.id
		    result["item.name"] = inventoryDetail.item.name
		    result["item.title"] = inventoryDetail.item.title
		    result["item.unit"] = inventoryDetail.item.unit
		}
		if(inventoryDetail.batch){
			result.batch = inventoryDetail.batch
		    result["batch.id"] = inventoryDetail.batch.id
		    result["batch.name"] = inventoryDetail.batch.name
		}

	    result.qty = inventoryDetail.qty
	    result.lastInDate = dateService.formatWithISO8601(inventoryDetail.lastInDate)
	    result.lastOutDate = dateService.formatWithISO8601(inventoryDetail.lastOutDate)

	    result
    }


    def operationParseJson(operation){
	    def result = [:]

		result.dateCreated = dateService.formatWithISO8601(operation.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(operation.lastUpdated)
	    result.site = operation.site

	    result.id= operation.id
	    result.name = operation.name
	    result.title = operation.title
	    result.description = operation.description
	    
	    result
    }

    def workstationParseJson(workstation){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(workstation.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(workstation.lastUpdated)
	    result.site = workstation.site

	    result.id= workstation.id
	    result.name = workstation.name
	    result.title = workstation.title
	    result.description = workstation.description
	    
	    result
    }

    def supplierParseJson(supplier){
	    def result = [:]

	    result.dateCreated = dateService.formatWithISO8601(supplier.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(supplier.lastUpdated)
	    result.site = supplier.site

	    result.id= supplier.id
	    result.name = supplier.name
	    result.title = supplier.title
	    def country = enumService.name(supplier.country)
	    result.country = country.name
        result.countryTitle = country.title
	    result.tel = supplier.tel
	    result.fax = supplier.fax
	    result.contact = supplier.contact
	    result.email = supplier.email
	    result.address = supplier.address
	    
	    result
    }

   	def batchRouteParseJson(batchRoute){
   		def result = [:]

   		result.dateCreated = dateService.formatWithISO8601(batchRoute.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(batchRoute.lastUpdated)
	    result.site = batchRoute.site

   		result.id=batchRoute.id
   		result.sequence=batchRoute.sequence
   		result.startDate=dateService.formatWithISO8601(batchRoute.startDate)
   		result.endDate=dateService.formatWithISO8601(batchRoute.endDate)
   		result.batch=batchRoute.batch
   		result["batch.id"] = batchRoute.batch.id
   		result["batch.name"] = batchRoute.batch.name
   		if(batchRoute.operation){
	   		result.operation=batchRoute.operation
	        result["operation.id"] = batchRoute.operation.id
	        result["operation.name"] = batchRoute.operation.name
	        result["operation.title"] = batchRoute.operation.title
	    }

        if(batchRoute.workstation){
        	result.workstation=batchRoute.workstation
            result["workstation.id"] = batchRoute.workstation.id
            result["workstation.name"] = batchRoute.workstation.name
            result["workstation.title"] = batchRoute.workstation.title
        }
        if(batchRoute.supplier){
        	result.supplier=batchRoute.supplier
            result["supplier.id"] = batchRoute.supplier.id
            result["supplier.name"] = batchRoute.supplier.name
            result["supplier.title"] = batchRoute.supplier.title
        }

        result
   	}

    def customerParseJson(customer){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(customer.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(customer.lastUpdated)
	    result.site = customer.site

        result.id = customer.id
    	result.name = customer.name
		result.title = customer.title
		result.tel = customer.tel
		result.fax = customer.fax
		result.contact = customer.contact
		result.email = customer.email
		result.address = customer.address
		result.shippingAddress = customer.shippingAddress

		result
    }

    def customerOrderParseJson(customerOrder){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(customerOrder.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(customerOrder.lastUpdated)
	    result.site = customerOrder.site

        result.id = customerOrder.id
    	result.name = customerOrder.name
		result.typeName = customerOrder.typeName
		result.dueDate = dateService.formatWithISO8601(customerOrder.dueDate)
		result.shippingAddress = customerOrder.shippingAddress

		if(customerOrder.customer){
			result.customer = customerOrder.customer
			result["customer.id"] = customerOrder.customer.id
	        result["customer.name"] = customerOrder.customer.name
	        result["customer.title"] = customerOrder.customer.title
	    }
		
		result
    }

    def customerOrderDetParseJson(customerOrderDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(customerOrderDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(customerOrderDet.lastUpdated)
	    result.site = customerOrderDet.site

        result.id = customerOrderDet.id
    	result.name = customerOrderDet.name
		result.typeName = customerOrderDet.typeName
		result.sequence = customerOrderDet.sequence
		result.qty = customerOrderDet.qty
		
		result.customerOrder = customerOrderDet.customerOrder
		result["customerOrder.id"] = customerOrderDet.customerOrder.id
        result["customerOrder.name"] = customerOrderDet.customerOrder.name
        result["customerOrder.typeName"] = customerOrderDet.customerOrder.typeName

		if(customerOrderDet.item){
			result.item = customerOrderDet.item
			result["item.id"] = customerOrderDet.item.id
	        result["item.name"] = customerOrderDet.item.name
	        result["item.title"] = customerOrderDet.item.title
	        result["item.spec"] = customerOrderDet.item.spec
	        result["item.unit"] = customerOrderDet.item.unit
	        result["item.description"] = customerOrderDet.item.description
	    }

		result
    }

    def manufactureOrderParseJson(manufactureOrder){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(manufactureOrder.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(manufactureOrder.lastUpdated)
	    result.site = manufactureOrder.site

        result.id = manufactureOrder.id
    	result.name = manufactureOrder.name
		result.typeName = manufactureOrder.typeName
		result.qty = manufactureOrder.qty

		if(manufactureOrder.workstation){
			result.workstation = manufactureOrder.workstation
			result["workstation.id"] = manufactureOrder.workstation.id
	        result["workstation.name"] = manufactureOrder.workstation.name
	        result["workstation.title"] = manufactureOrder.workstation.title
	    }
	    if(manufactureOrder.supplier){
			result.supplier = manufactureOrder.supplier
			result["supplier.id"] = manufactureOrder.supplier.id
	        result["supplier.name"] = manufactureOrder.supplier.name
	        result["supplier.title"] = manufactureOrder.supplier.title
	    }

		if(manufactureOrder.item){
			result.item = manufactureOrder.item
			result["item.id"] = manufactureOrder.item.id
	        result["item.name"] = manufactureOrder.item.name
	        result["item.title"] = manufactureOrder.item.title
	        result["item.spec"] = manufactureOrder.item.spec
	        result["item.unit"] = manufactureOrder.item.unit
	        result["item.description"] = manufactureOrder.item.description
	    }
        if(manufactureOrder.batch){
        	result.batch = manufactureOrder.batch
	        result["batch.id"] = manufactureOrder.batch.id
	        result["batch.name"] = manufactureOrder.batch.name
	    }

		result
    }

    def materialSheetParseJson(materialSheet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(materialSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(materialSheet.lastUpdated)
	    result.site = materialSheet.site

        result.id = materialSheet.id
    	result.name = materialSheet.name
		result.typeName = materialSheet.typeName

		if(materialSheet.workstation){
			result.workstation = materialSheet.workstation
			result["workstation.id"] = materialSheet.workstation.id
	        result["workstation.name"] = materialSheet.workstation.name
	        result["workstation.title"] = materialSheet.workstation.title
	    }
	    if(materialSheet.supplier){
			result.supplier = materialSheet.supplier
			result["supplier.id"] = materialSheet.supplier.id
	        result["supplier.name"] = materialSheet.supplier.name
	        result["supplier.title"] = materialSheet.supplier.title
	    }

		result
    }

    def materialSheetDetParseJson(materialSheetDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(materialSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(materialSheetDet.lastUpdated)
	    result.site = materialSheetDet.site

        result.id = materialSheetDet.id
    	result.name = materialSheetDet.name
		result.typeName = materialSheetDet.typeName
		result.sequence = materialSheetDet.sequence
		result.qty = materialSheetDet.qty

		result.materialSheet = materialSheetDet.materialSheet
		result["materialSheet.id"] = materialSheetDet.materialSheet.id

		if(materialSheetDet.manufactureOrder){
			result.manufactureOrder = materialSheetDet.manufactureOrder
			result["manufactureOrder.id"] = materialSheetDet.manufactureOrder.id
	        result["manufactureOrder.name"] = materialSheetDet.manufactureOrder.name
	        result["manufactureOrder.typeName"] = materialSheetDet.manufactureOrder.typeName
	    }

	    if(materialSheetDet.batch){
			result.batch = materialSheetDet.batch
			result["batch.id"] = materialSheetDet.batch.id
	        result["batch.name"] = materialSheetDet.batch.name
	    }

		if(materialSheetDet.item){
			result.item = materialSheetDet.item
			result["item.id"] = materialSheetDet.item.id
	        result["item.name"] = materialSheetDet.item.name
	        result["item.title"] = materialSheetDet.item.title
	        result["item.spec"] = materialSheetDet.item.spec
	        result["item.unit"] = materialSheetDet.item.unit
	        result["item.description"] = materialSheetDet.item.description
	    }

	    if(materialSheetDet.warehouse){
			result.warehouse = materialSheetDet.warehouse
			result["warehouse.id"] = materialSheetDet.warehouse.id
	        result["warehouse.name"] = materialSheetDet.warehouse.name
	        result["warehouse.title"] = materialSheetDet.warehouse.title
	    }

	    if(materialSheetDet.warehouseLocation){
	    	result.warehouseLocation = materialSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = materialSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = materialSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = materialSheetDet.warehouseLocation.title
		}

		result
    }

    def purchaseSheetParseJson(purchaseSheet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(purchaseSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(purchaseSheet.lastUpdated)
	    result.site = purchaseSheet.site

        result.id = purchaseSheet.id
    	result.name = purchaseSheet.name
		result.typeName = purchaseSheet.typeName

		if(purchaseSheet.supplier){
			result.supplier = purchaseSheet.supplier
			result["supplier.id"] = purchaseSheet.supplier.id
	        result["supplier.name"] = purchaseSheet.supplier.name
	        result["supplier.title"] = purchaseSheet.supplier.title
	    }

		result
    }

    def purchaseSheetDetParseJson(purchaseSheetDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(purchaseSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(purchaseSheetDet.lastUpdated)
	    result.site = purchaseSheetDet.site

        result.id = purchaseSheetDet.id
    	result.name = purchaseSheetDet.name
		result.typeName = purchaseSheetDet.typeName
		result.sequence = purchaseSheetDet.sequence

		result.purchaseSheet = purchaseSheetDet.purchaseSheet
		result["purchaseSheet.id"] = purchaseSheetDet.purchaseSheet.id
		result.qty = purchaseSheetDet.qty

	    if(purchaseSheetDet.batch){
			result.batch = purchaseSheetDet.batch
			result["batch.id"] = purchaseSheetDet.batch.id
	        result["batch.name"] = purchaseSheetDet.batch.name
	    }


		if(purchaseSheetDet.item){
			result.item = purchaseSheetDet.item
			result["item.id"] = purchaseSheetDet.item.id
	        result["item.name"] = purchaseSheetDet.item.name
	        result["item.title"] = purchaseSheetDet.item.title
	        result["item.spec"] = purchaseSheetDet.item.spec
	        result["item.unit"] = purchaseSheetDet.item.unit
	        result["item.description"] = purchaseSheetDet.item.description
	    }

	    if(purchaseSheetDet.warehouse){
			result.warehouse = purchaseSheetDet.warehouse
			result["warehouse.id"] = purchaseSheetDet.warehouse.id
	        result["warehouse.name"] = purchaseSheetDet.warehouse.name
	        result["warehouse.title"] = purchaseSheetDet.warehouse.title
	    }

	    if(purchaseSheetDet.warehouseLocation){
	    	result.warehouseLocation = purchaseSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = purchaseSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = purchaseSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = purchaseSheetDet.warehouseLocation.title
		}

		result
    }

    def stockInSheetParseJson(stockInSheet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(stockInSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(stockInSheet.lastUpdated)
	    result.site = stockInSheet.site

        result.id = stockInSheet.id
    	result.name = stockInSheet.name
		result.typeName = stockInSheet.typeName

		if(stockInSheet.workstation){
			result.workstation = stockInSheet.workstation
			result["workstation.id"] = stockInSheet.workstation.id
	        result["workstation.name"] = stockInSheet.workstation.name
	        result["workstation.title"] = stockInSheet.workstation.title
	    }

		result
    }

    def stockInSheetDetParseJson(stockInSheetDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(stockInSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(stockInSheetDet.lastUpdated)
	    result.site = stockInSheetDet.site

        result.id = stockInSheetDet.id
    	result.name = stockInSheetDet.name
		result.typeName = stockInSheetDet.typeName
		result.sequence = stockInSheetDet.sequence
		result.qty = stockInSheetDet.qty

		result.stockInSheet = stockInSheetDet.stockInSheet
		result["stockInSheet.id"] = stockInSheetDet.stockInSheet.id

		if(stockInSheetDet.manufactureOrder){
			result.manufactureOrder = stockInSheetDet.manufactureOrder
			result["manufactureOrder.id"] = stockInSheetDet.manufactureOrder.id
	        result["manufactureOrder.name"] = stockInSheetDet.manufactureOrder.name
	        result["manufactureOrder.typeName"] = stockInSheetDet.manufactureOrder.typeName
	    }

	    if(stockInSheetDet.batch){
			result.batch = stockInSheetDet.batch
			result["batch.id"] = stockInSheetDet.batch.id
	        result["batch.name"] = stockInSheetDet.batch.name
	    }

		if(stockInSheetDet.item){
			result.item = stockInSheetDet.item
			result["item.id"] = stockInSheetDet.item.id
	        result["item.name"] = stockInSheetDet.item.name
	        result["item.title"] = stockInSheetDet.item.title
	        result["item.spec"] = stockInSheetDet.item.spec
	        result["item.unit"] = stockInSheetDet.item.unit
	        result["item.description"] = stockInSheetDet.item.description
	    }

	    if(stockInSheetDet.warehouse){
			result.warehouse = stockInSheetDet.warehouse
			result["warehouse.id"] = stockInSheetDet.warehouse.id
	        result["warehouse.name"] = stockInSheetDet.warehouse.name
	        result["warehouse.title"] = stockInSheetDet.warehouse.title
	    }
	    if(stockInSheetDet.warehouseLocation){
	    	result.warehouseLocation = stockInSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = stockInSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = stockInSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = stockInSheetDet.warehouseLocation.title
		}

		result
    }

    def outSrcPurchaseSheetParseJson(outSrcPurchaseSheet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(outSrcPurchaseSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(outSrcPurchaseSheet.lastUpdated)
	    result.site = outSrcPurchaseSheet.site

        result.id = outSrcPurchaseSheet.id
    	result.name = outSrcPurchaseSheet.name
		result.typeName = outSrcPurchaseSheet.typeName

		if(outSrcPurchaseSheet.supplier){
			result.supplier = outSrcPurchaseSheet.supplier
			result["supplier.id"] = outSrcPurchaseSheet.supplier.id
	        result["supplier.name"] = outSrcPurchaseSheet.supplier.name
	        result["supplier.title"] = outSrcPurchaseSheet.supplier.title
	    }

		result
    }

    def outSrcPurchaseSheetDetParseJson(outSrcPurchaseSheetDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(outSrcPurchaseSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(outSrcPurchaseSheetDet.lastUpdated)
	    result.site = outSrcPurchaseSheetDet.site

        result.id = outSrcPurchaseSheetDet.id
    	result.name = outSrcPurchaseSheetDet.name
		result.typeName = outSrcPurchaseSheetDet.typeName
		result.sequence = outSrcPurchaseSheetDet.sequence
		result.qty = outSrcPurchaseSheetDet.qty

		result.outSrcPurchaseSheet = outSrcPurchaseSheetDet.outSrcPurchaseSheet
		result["outSrcPurchaseSheet.id"] = outSrcPurchaseSheetDet.outSrcPurchaseSheet.id

		if(outSrcPurchaseSheetDet.manufactureOrder){
			result.manufactureOrder = outSrcPurchaseSheetDet.manufactureOrder
			result["manufactureOrder.id"] = outSrcPurchaseSheetDet.manufactureOrder.id
	        result["manufactureOrder.name"] = outSrcPurchaseSheetDet.manufactureOrder.name
	        result["manufactureOrder.typeName"] = outSrcPurchaseSheetDet.manufactureOrder.typeName
	    }

	    if(outSrcPurchaseSheetDet.batch){
			result.batch = outSrcPurchaseSheetDet.batch
			result["batch.id"] = outSrcPurchaseSheetDet.batch.id
	        result["batch.name"] = outSrcPurchaseSheetDet.batch.name
	    }

		if(outSrcPurchaseSheetDet.item){
			result.item = outSrcPurchaseSheetDet.item
			result["item.id"] = outSrcPurchaseSheetDet.item.id
	        result["item.name"] = outSrcPurchaseSheetDet.item.name
	        result["item.title"] = outSrcPurchaseSheetDet.item.title
	        result["item.spec"] = outSrcPurchaseSheetDet.item.spec
	        result["item.unit"] = outSrcPurchaseSheetDet.item.unit
	        result["item.description"] = outSrcPurchaseSheetDet.item.description
	    }

	    if(outSrcPurchaseSheetDet.warehouse){
			result.warehouse = outSrcPurchaseSheetDet.warehouse
			result["warehouse.id"] = outSrcPurchaseSheetDet.warehouse.id
	        result["warehouse.name"] = outSrcPurchaseSheetDet.warehouse.name
	        result["warehouse.title"] = outSrcPurchaseSheetDet.warehouse.title
	    }

	    if(outSrcPurchaseSheetDet.warehouseLocation){
	    	result.warehouseLocation = outSrcPurchaseSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = outSrcPurchaseSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = outSrcPurchaseSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = outSrcPurchaseSheetDet.warehouseLocation.title
		}

		result
    }

    def saleSheetParseJson(saleSheet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(saleSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(saleSheet.lastUpdated)
	    result.site = saleSheet.site

        result.id = saleSheet.id
    	result.name = saleSheet.name
		result.typeName = saleSheet.typeName
		result.shippingAddress = saleSheet.shippingAddress

		if(saleSheet.customer){
			result.customer = saleSheet.customer
			result["customer.id"] = saleSheet.customer.id
	        result["customer.name"] = saleSheet.customer.name
	        result["customer.title"] = saleSheet.customer.title
	        result["customer.tel"] = saleSheet.customer.tel
	        result["customer.fax"] = saleSheet.customer.fax
	    }

		result
    }

    def saleSheetDetParseJson(saleSheetDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(saleSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(saleSheetDet.lastUpdated)
	    result.site = saleSheetDet.site

        result.id = saleSheetDet.id
    	result.name = saleSheetDet.name
		result.typeName = saleSheetDet.typeName
		result.sequence = saleSheetDet.sequence
		result.qty = saleSheetDet.qty

		result.saleSheet = saleSheetDet.saleSheet
		result["saleSheet.id"] = saleSheetDet.saleSheet.id

		if(saleSheetDet.customerOrderDet){
			result.customerOrderDet = saleSheetDet.customerOrderDet
			result["customerOrderDet.id"] = saleSheetDet.customerOrderDet.id
	        result["customerOrderDet.name"] = saleSheetDet.customerOrderDet.name
	        result["customerOrderDet.typeName"] = saleSheetDet.customerOrderDet.typeName
	        result["customerOrderDet.sequence"] = saleSheetDet.customerOrderDet.sequence
	    }

	    if(saleSheetDet.batch){
			result.batch = saleSheetDet.batch
			result["batch.id"] = saleSheetDet.batch.id
	        result["batch.name"] = saleSheetDet.batch.name
	    }

		if(saleSheetDet.item){
			result.item = saleSheetDet.item
			result["item.id"] = saleSheetDet.item.id
	        result["item.name"] = saleSheetDet.item.name
	        result["item.title"] = saleSheetDet.item.title
	        result["item.spec"] = saleSheetDet.item.spec
	        result["item.unit"] = saleSheetDet.item.unit
	        result["item.description"] = saleSheetDet.item.description
	    }

	    if(saleSheetDet.warehouse){
			result.warehouse = saleSheetDet.warehouse
			result["warehouse.id"] = saleSheetDet.warehouse.id
	        result["warehouse.name"] = saleSheetDet.warehouse.name
	        result["warehouse.title"] = saleSheetDet.warehouse.title
	    }

	    if(saleSheetDet.warehouseLocation){
	    	result.warehouseLocation = saleSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = saleSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = saleSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = saleSheetDet.warehouseLocation.title
		}

		result
    }


    def materialReturnSheetParseJson(materialReturnSheet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(materialReturnSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(materialReturnSheet.lastUpdated)
	    result.site = materialReturnSheet.site

        result.id = materialReturnSheet.id
    	result.name = materialReturnSheet.name
		result.typeName = materialReturnSheet.typeName

		if(materialReturnSheet.workstation){
			result.workstation = materialReturnSheet.workstation
			result["workstation.id"] = materialReturnSheet.workstation.id
	        result["workstation.name"] = materialReturnSheet.workstation.name
	        result["workstation.title"] = materialReturnSheet.workstation.title
	    }
	    if(materialReturnSheet.supplier){
			result.supplier = materialReturnSheet.supplier
			result["supplier.id"] = materialReturnSheet.supplier.id
	        result["supplier.name"] = materialReturnSheet.supplier.name
	        result["supplier.title"] = materialReturnSheet.supplier.title
	    }

		result
    }

    def materialReturnSheetDetParseJson(materialReturnSheetDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(materialReturnSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(materialReturnSheetDet.lastUpdated)
	    result.site = materialReturnSheetDet.site

        result.id = materialReturnSheetDet.id
    	result.name = materialReturnSheetDet.name
		result.typeName = materialReturnSheetDet.typeName
		result.sequence = materialReturnSheetDet.sequence
		result.qty = materialReturnSheetDet.qty

		result.materialReturnSheet = materialReturnSheetDet.materialReturnSheet
		result["materialReturnSheet.id"] = materialReturnSheetDet.materialReturnSheet.id

		if(materialReturnSheetDet.materialSheetDet){
			result.materialSheetDet = materialReturnSheetDet.materialSheetDet
			result["materialSheetDet.id"] = materialReturnSheetDet.materialSheetDet.id
	        result["materialSheetDet.name"] = materialReturnSheetDet.materialSheetDet.name
	        result["materialSheetDet.typeName"] = materialReturnSheetDet.materialSheetDet.typeName
	        result["materialSheetDet.sequence"] = materialReturnSheetDet.materialSheetDet.sequence
	    }

		if(materialReturnSheetDet.manufactureOrder){
			result.manufactureOrder = materialReturnSheetDet.manufactureOrder
			result["manufactureOrder.id"] = materialReturnSheetDet.manufactureOrder.id
	        result["manufactureOrder.name"] = materialReturnSheetDet.manufactureOrder.name
	        result["manufactureOrder.typeName"] = materialReturnSheetDet.manufactureOrder.typeName
	    }

	    if(materialReturnSheetDet.batch){
			result.batch = materialReturnSheetDet.batch
			result["batch.id"] = materialReturnSheetDet.batch.id
	        result["batch.name"] = materialReturnSheetDet.batch.name
	    }

		if(materialReturnSheetDet.item){
			result.item = materialReturnSheetDet.item
			result["item.id"] = materialReturnSheetDet.item.id
	        result["item.name"] = materialReturnSheetDet.item.name
	        result["item.title"] = materialReturnSheetDet.item.title
	        result["item.spec"] = materialReturnSheetDet.item.spec
	        result["item.unit"] = materialReturnSheetDet.item.unit
	        result["item.description"] = materialReturnSheetDet.item.description
	    }

	    if(materialReturnSheetDet.warehouse){
			result.warehouse = materialReturnSheetDet.warehouse
			result["warehouse.id"] = materialReturnSheetDet.warehouse.id
	        result["warehouse.name"] = materialReturnSheetDet.warehouse.name
	        result["warehouse.title"] = materialReturnSheetDet.warehouse.title
	    }

	    if(materialReturnSheetDet.warehouseLocation){
	    	result.warehouseLocation = materialReturnSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = materialReturnSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = materialReturnSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = materialReturnSheetDet.warehouseLocation.title
		}

		result
    }

    def purchaseReturnSheetParseJson(purchaseReturnSheet){
  		def result = [:]

		result.dateCreated = dateService.formatWithISO8601(purchaseReturnSheet.dateCreated)
    	result.lastUpdated = dateService.formatWithISO8601(purchaseReturnSheet.lastUpdated)
    	result.site = purchaseReturnSheet.site

		result.id = purchaseReturnSheet.id
		result.name = purchaseReturnSheet.name
		result.typeName = purchaseReturnSheet.typeName

		if(purchaseReturnSheet.supplier){
			result.supplier = purchaseReturnSheet.supplier
			result["supplier.id"] = purchaseReturnSheet.supplier.id
    		result["supplier.name"] = purchaseReturnSheet.supplier.name
    		result["supplier.title"] = purchaseReturnSheet.supplier.title
	    }

		result
    }

    def purchaseReturnSheetDetParseJson(purchaseReturnSheetDet){
    	def result = [:]

		result.dateCreated = dateService.formatWithISO8601(purchaseReturnSheetDet.dateCreated)
	   	result.lastUpdated = dateService.formatWithISO8601(purchaseReturnSheetDet.lastUpdated)
    	result.site = purchaseReturnSheetDet.site

		result.id = purchaseReturnSheetDet.id
		result.name = purchaseReturnSheetDet.name
		result.typeName = purchaseReturnSheetDet.typeName
		result.sequence = purchaseReturnSheetDet.sequence

		result.qty = purchaseReturnSheetDet.qty

		result.purchaseReturnSheet = purchaseReturnSheetDet.purchaseReturnSheet
		result["purchaseReturnSheet.id"] = purchaseReturnSheetDet.purchaseReturnSheet.id

		if(purchaseReturnSheetDet.purchaseSheetDet){
			result.purchaseSheetDet = purchaseReturnSheetDet.purchaseSheetDet
			result["purchaseSheetDet.id"] = purchaseReturnSheetDet.purchaseSheetDet.id
	        result["purchaseSheetDet.typeName"] = purchaseReturnSheetDet.purchaseSheetDet.typeName
	        result["purchaseSheetDet.name"] = purchaseReturnSheetDet.purchaseSheetDet.name
	        result["purchaseSheetDet.sequence"] = purchaseReturnSheetDet.purchaseSheetDet.sequence
	    }

	    if(purchaseReturnSheetDet.batch){
			result.batch = purchaseReturnSheetDet.batch
			result["batch.id"] = purchaseReturnSheetDet.batch.id
	        result["batch.name"] = purchaseReturnSheetDet.batch.name
	    }


		if(purchaseReturnSheetDet.item){
			result.item = purchaseReturnSheetDet.item
			result["item.id"] = purchaseReturnSheetDet.item.id
    		result["item.name"] = purchaseReturnSheetDet.item.name
    		result["item.title"] = purchaseReturnSheetDet.item.title
    		result["item.spec"] = purchaseReturnSheetDet.item.spec
    		result["item.unit"] = purchaseReturnSheetDet.item.unit
    		result["item.description"] = purchaseReturnSheetDet.item.description
	    }

	    if(purchaseReturnSheetDet.warehouse){
			result.warehouse = purchaseReturnSheetDet.warehouse
			result["warehouse.id"] = purchaseReturnSheetDet.warehouse.id
    		result["warehouse.name"] = purchaseReturnSheetDet.warehouse.name
    		result["warehouse.title"] = purchaseReturnSheetDet.warehouse.title
	    }

	    if(purchaseReturnSheetDet.warehouseLocation){
	    	result.warehouseLocation = purchaseReturnSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = purchaseReturnSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = purchaseReturnSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = purchaseReturnSheetDet.warehouseLocation.title
		}

		result
    }

    def outSrcPurchaseReturnSheetParseJson(outSrcPurchaseReturnSheet){
		def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(outSrcPurchaseReturnSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(outSrcPurchaseReturnSheet.lastUpdated)
	    result.site = outSrcPurchaseReturnSheet.site

        result.id = outSrcPurchaseReturnSheet.id
    	result.name = outSrcPurchaseReturnSheet.name
		result.typeName = outSrcPurchaseReturnSheet.typeName

		if(outSrcPurchaseReturnSheet.supplier){
			result.supplier = outSrcPurchaseReturnSheet.supplier
			result["supplier.id"] = outSrcPurchaseReturnSheet.supplier.id
	        result["supplier.name"] = outSrcPurchaseReturnSheet.supplier.name
	        result["supplier.title"] = outSrcPurchaseReturnSheet.supplier.title
	    }

		result
    }

    def outSrcPurchaseReturnSheetDetParseJson(outSrcPurchaseReturnSheetDet){

    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(outSrcPurchaseReturnSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(outSrcPurchaseReturnSheetDet.lastUpdated)
	    result.site = outSrcPurchaseReturnSheetDet.site

        result.id = outSrcPurchaseReturnSheetDet.id
    	result.name = outSrcPurchaseReturnSheetDet.name
		result.typeName = outSrcPurchaseReturnSheetDet.typeName
		result.sequence = outSrcPurchaseReturnSheetDet.sequence
		result.qty = outSrcPurchaseReturnSheetDet.qty

		result.outSrcPurchaseReturnSheet = outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet
		result["outSrcPurchaseReturnSheet.id"] = outSrcPurchaseReturnSheetDet.outSrcPurchaseReturnSheet.id

		if(outSrcPurchaseReturnSheetDet.manufactureOrder){
			result.manufactureOrder = outSrcPurchaseReturnSheetDet.manufactureOrder	
			result["manufactureOrder.id"] = outSrcPurchaseReturnSheetDet.manufactureOrder.id
	        result["manufactureOrder.name"] = outSrcPurchaseReturnSheetDet.manufactureOrder.name
	        result["manufactureOrder.typeName"] = outSrcPurchaseReturnSheetDet.manufactureOrder.typeName
	    }

	    if(outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet){
			result.outSrcPurchaseSheetDet = outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet
			result["outSrcPurchaseSheetDet.id"] = outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.id
	        result["outSrcPurchaseSheetDet.name"] = outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.name
	        result["outSrcPurchaseSheetDet.typeName"] = outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.typeName
	        result["outSrcPurchaseSheetDet.sequence"] = outSrcPurchaseReturnSheetDet.outSrcPurchaseSheetDet.sequence
	    }

	    if(outSrcPurchaseReturnSheetDet.batch){
			result.batch = outSrcPurchaseReturnSheetDet.batch
			result["batch.id"] = outSrcPurchaseReturnSheetDet.batch.id
	        result["batch.name"] = outSrcPurchaseReturnSheetDet.batch.name
	    }

		if(outSrcPurchaseReturnSheetDet.item){
			result.item = outSrcPurchaseReturnSheetDet.item
			result["item.id"] = outSrcPurchaseReturnSheetDet.item.id
	        result["item.name"] = outSrcPurchaseReturnSheetDet.item.name
	        result["item.title"] = outSrcPurchaseReturnSheetDet.item.title
	        result["item.spec"] = outSrcPurchaseReturnSheetDet.item.spec
	        result["item.unit"] = outSrcPurchaseReturnSheetDet.item.unit
	        result["item.description"] = outSrcPurchaseReturnSheetDet.item.description
	    }

	    if(outSrcPurchaseReturnSheetDet.warehouse){
			result.warehouse = outSrcPurchaseReturnSheetDet.warehouse
			result["warehouse.id"] = outSrcPurchaseReturnSheetDet.warehouse.id
	        result["warehouse.name"] = outSrcPurchaseReturnSheetDet.warehouse.name
	        result["warehouse.title"] = outSrcPurchaseReturnSheetDet.warehouse.title
	    }

	    if(outSrcPurchaseReturnSheetDet.warehouseLocation){
	    	result.warehouseLocation = outSrcPurchaseReturnSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = outSrcPurchaseReturnSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = outSrcPurchaseReturnSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = outSrcPurchaseReturnSheetDet.warehouseLocation.title
		}

		result
    }

    def saleReturnSheetParseJson(saleReturnSheet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(saleReturnSheet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(saleReturnSheet.lastUpdated)
	    result.site = saleReturnSheet.site

        result.id = saleReturnSheet.id
    	result.name = saleReturnSheet.name
		result.typeName = saleReturnSheet.typeName
		result.pickUpAddress = saleReturnSheet.pickUpAddress

		if(saleReturnSheet.customer){
			result.customer = saleReturnSheet.customer
			result["customer.id"] = saleReturnSheet.customer.id
	        result["customer.name"] = saleReturnSheet.customer.name
	        result["customer.title"] = saleReturnSheet.customer.title
	        result["customer.tel"] = saleReturnSheet.customer.tel
	        result["customer.fax"] = saleReturnSheet.customer.fax
	    }

		result
    }

    def saleReturnSheetDetParseJson(saleReturnSheetDet){
    	def result = [:]

    	result.dateCreated = dateService.formatWithISO8601(saleReturnSheetDet.dateCreated)
	    result.lastUpdated = dateService.formatWithISO8601(saleReturnSheetDet.lastUpdated)
	    result.site = saleReturnSheetDet.site

        result.id = saleReturnSheetDet.id
    	result.name = saleReturnSheetDet.name
		result.typeName = saleReturnSheetDet.typeName
		result.sequence = saleReturnSheetDet.sequence
		result.qty = saleReturnSheetDet.qty

		result.saleReturnSheet = saleReturnSheetDet.saleReturnSheet
		result["saleReturnSheet.id"] = saleReturnSheetDet.saleReturnSheet.id

		if(saleReturnSheetDet.customerOrderDet){
			result.customerOrderDet = saleReturnSheetDet.customerOrderDet
			result["customerOrderDet.id"] = saleReturnSheetDet.customerOrderDet.id
	        result["customerOrderDet.name"] = saleReturnSheetDet.customerOrderDet.name
	        result["customerOrderDet.typeName"] = saleReturnSheetDet.customerOrderDet.typeName
	        result["customerOrderDet.sequence"] = saleReturnSheetDet.customerOrderDet.sequence
	    }

	    if(saleReturnSheetDet.saleSheetDet){
			result.saleSheetDet = saleReturnSheetDet.saleSheetDet
			result["saleSheetDet.id"] = saleReturnSheetDet.saleSheetDet.id
	        result["saleSheetDet.name"] = saleReturnSheetDet.saleSheetDet.name
	        result["saleSheetDet.typeName"] = saleReturnSheetDet.saleSheetDet.typeName
	        result["saleSheetDet.sequence"] = saleReturnSheetDet.saleSheetDet.sequence
	    }

	    if(saleReturnSheetDet.batch){
			result.batch = saleReturnSheetDet.batch
			result["batch.id"] = saleReturnSheetDet.batch.id
	        result["batch.name"] = saleReturnSheetDet.batch.name
	    }

		if(saleReturnSheetDet.item){
			result.item = saleReturnSheetDet.item
			result["item.id"] = saleReturnSheetDet.item.id
	        result["item.name"] = saleReturnSheetDet.item.name
	        result["item.title"] = saleReturnSheetDet.item.title
	        result["item.spec"] = saleReturnSheetDet.item.spec
	        result["item.unit"] = saleReturnSheetDet.item.unit
	        result["item.description"] = saleReturnSheetDet.item.description
	    }

	    if(saleReturnSheetDet.warehouse){
			result.warehouse = saleReturnSheetDet.warehouse
			result["warehouse.id"] = saleReturnSheetDet.warehouse.id
	        result["warehouse.name"] = saleReturnSheetDet.warehouse.name
	        result["warehouse.title"] = saleReturnSheetDet.warehouse.title
	    }

	    if(saleReturnSheetDet.warehouseLocation){
	    	result.warehouseLocation = saleReturnSheetDet.warehouseLocation
		    result["warehouseLocation.id"] = saleReturnSheetDet.warehouseLocation.id
		    result["warehouseLocation.name"] = saleReturnSheetDet.warehouseLocation.name
		    result["warehouseLocation.title"] = saleReturnSheetDet.warehouseLocation.title
		}

		result
    }


}
