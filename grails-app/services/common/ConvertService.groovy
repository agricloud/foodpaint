package common
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class ConvertService {

	def enumService

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

	    result.dateCreated = batch.dateCreated
	    result.lastUpdated = batch.lastUpdated
	    result.site = batch.site

	    result.id= batch.id
	    result.name=batch.name
	    result.item = batch.item
	    result.dueDate = batch.dueDate
	    result.expectQty = batch.expectQty
	    result.manufactureDate = batch.manufactureDate
	    result.expirationDate = batch.expirationDate
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
        if(batch.country){
        	def country=enumService.name(batch.country)
        	result["country"] = country.name
        	result["countryTitle"] = country.title
        }
	    
	    result
    }

    def batchSourceParseJson(batchSource){
	    def result = [:]

	    result.dateCreated = batchSource.dateCreated
	    result.lastUpdated = batchSource.lastUpdated
	    result.site = batchSource.site

	    result.id = batchSource.id

	    if(batchSource.batch){
		    result.batch = batchSource.batch
		    result["batch.id"]= batchSource.batch.id
		    result["batch.name"] =batchSource.batch.name
		    result["batch.dueDate"] = batchSource.batch.dueDate
		    result["batch.expectQty"] = batchSource.batch.expectQty
		    result["batch.manufactureDate"] = batchSource.batch.manufactureDate
		    result["batch.expirationDate"] = batchSource.batch.expirationDate
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
		    result["childBatch.dueDate"] = batchSource.childBatch.dueDate
		    result["childBatch.expectQty"] = batchSource.childBatch.expectQty
		    result["childBatch.manufactureDate"] = batchSource.childBatch.manufactureDate
		    result["childBatch.expirationDate"] = batchSource.childBatch.expirationDate
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
    def warehouseParseJson(warehouse){
	    def result = [:]

	    result.dateCreated = warehouse.dateCreated
	    result.lastUpdated = warehouse.lastUpdated
	    result.site = warehouse.site

	    result.id = warehouse.id
	    result.name = warehouse.name
	    result.title = warehouse.title
	    result.remark = warehouse.remark

	    result
    }
    def itemParseJson(item){
	    def result = [:]

	    result.dateCreated = item.dateCreated
	    result.lastUpdated = item.lastUpdated
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

   		result.dateCreated = itemRoute.dateCreated
	    result.lastUpdated = itemRoute.lastUpdated
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

   	def inventoryParseJson(inventory){
	    def result = [:]

	    result.dateCreated = inventory.dateCreated
	    result.lastUpdated = inventory.lastUpdated
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
		}
	    result.qty = inventory.qty

	    result
    }

    def inventoryDetailParseJson(inventoryDetail){
	    def result = [:]

	    result.dateCreated = inventoryDetail.dateCreated
	    result.lastUpdated = inventoryDetail.lastUpdated
	    result.site = inventoryDetail.site

	    result.id = inventoryDetail.id

	    if(inventoryDetail.warehouse){
	    	result.warehouse = inventoryDetail.warehouse
		    result["warehouse.id"] = inventoryDetail.warehouse.id
		    result["warehouse.name"] = inventoryDetail.warehouse.name
		    result["warehouse.title"] = inventoryDetail.warehouse.title
		}
		if(inventoryDetail.item){
			result.item = inventoryDetail.item
		    result["item.id"] = inventoryDetail.item.id
		    result["item.name"] = inventoryDetail.item.name
		    result["item.title"] = inventoryDetail.item.title
		}
		if(inventoryDetail.batch){
			result.batch = inventoryDetail.batch
		    result["batch.id"] = inventoryDetail.batch.id
		    result["batch.name"] = inventoryDetail.batch.name
		}

	    result.qty = inventoryDetail.qty

	    result
    }


    def operationParseJson(operation){
	    def result = [:]

		result.dateCreated = operation.dateCreated
	    result.lastUpdated = operation.lastUpdated
	    result.site = operation.site

	    result.id= operation.id
	    result.name = operation.name
	    result.title = operation.title
	    result.description = operation.description
	    
	    result
    }

    def workstationParseJson(workstation){
	    def result = [:]

	    result.dateCreated = workstation.dateCreated
	    result.lastUpdated = workstation.lastUpdated
	    result.site = workstation.site

	    result.id= workstation.id
	    result.name = workstation.name
	    result.title = workstation.title
	    result.description = workstation.description
	    
	    result
    }

    def supplierParseJson(supplier){
	    def result = [:]

	    result.dateCreated = supplier.dateCreated
	    result.lastUpdated = supplier.lastUpdated
	    result.site = supplier.site

	    result.id= supplier.id
	    result.name = supplier.name
	    result.title = supplier.title
	    def country = enumService.name(supplier.country)
	    result.country = country.name
        result.countryTitle = country.title
	    result.tel = supplier.tel
	    result.email = supplier.email
	    result.address = supplier.address
	    
	    result
    }

   	def batchRouteParseJson(batchRoute){
   		def result = [:]

   		result.dateCreated = batchRoute.dateCreated
	    result.lastUpdated = batchRoute.lastUpdated
	    result.site = batchRoute.site

   		result.id=batchRoute.id
   		result.sequence=batchRoute.sequence
   		result.startDate=batchRoute.startDate
   		result.endDate=batchRoute.endDate
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

    // def userParseJson(user){
    // 	def result = [:]
    //     result.id= user.id
    //     result.username = user.username
    //     result.password = user.password
    //     result.enabled= user.enabled
    //     result.accountExpired = user.accountExpired
    //     result.accountLocked = user.accountLocked
    //     result.passwordExpired = user.passwordExpired
    //     result.fullName = user.fullName
    //     result.email = user.email
    //     if(user.site){
	   //      result.site = user.site
	   //      result["site.id"] = user.site.id
	   //      result["site.name"] = user.site.name
	   //      result["site.title"] = user.site.title
	   //  }

    //     result
    // }

    def customerParseJson(customer){
    	def result = [:]

    	result.dateCreated = customer.dateCreated
	    result.lastUpdated = customer.lastUpdated
	    result.site = customer.site

        result.id = customer.id
    	result.name = customer.name
		result.title = customer.title
		result.email = customer.email
		result.address = customer.address

		result
    }

    def customerOrderParseJson(customerOrder){
    	def result = [:]

    	result.dateCreated = customerOrder.dateCreated
	    result.lastUpdated = customerOrder.lastUpdated
	    result.site = customerOrder.site

        result.id = customerOrder.id
    	result.name = customerOrder.name
		result.typeName = customerOrder.typeName
		result.dueDate = customerOrder.dueDate

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

    	result.dateCreated = customerOrderDet.dateCreated
	    result.lastUpdated = customerOrderDet.lastUpdated
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

    	result.dateCreated = manufactureOrder.dateCreated
	    result.lastUpdated = manufactureOrder.lastUpdated
	    result.site = manufactureOrder.site

        result.id = manufactureOrder.id
    	result.name = manufactureOrder.name
		result.typeName = manufactureOrder.typeName
		result.qty = manufactureOrder.qty

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

    	result.dateCreated = materialSheet.dateCreated
	    result.lastUpdated = materialSheet.lastUpdated
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

    	result.dateCreated = materialSheetDet.dateCreated
	    result.lastUpdated = materialSheetDet.lastUpdated
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

		result
    }

    def purchaseSheetParseJson(purchaseSheet){
    	def result = [:]

    	result.dateCreated = purchaseSheet.dateCreated
	    result.lastUpdated = purchaseSheet.lastUpdated
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

    	result.dateCreated = purchaseSheetDet.dateCreated
	    result.lastUpdated = purchaseSheetDet.lastUpdated
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

		result
    }

    def stockInSheetParseJson(stockInSheet){
    	def result = [:]

    	result.dateCreated = stockInSheet.dateCreated
	    result.lastUpdated = stockInSheet.lastUpdated
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

    	result.dateCreated = stockInSheetDet.dateCreated
	    result.lastUpdated = stockInSheetDet.lastUpdated
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

		result
    }

    def outSrcPurchaseSheetParseJson(outSrcPurchaseSheet){
    	def result = [:]

    	result.dateCreated = outSrcPurchaseSheet.dateCreated
	    result.lastUpdated = outSrcPurchaseSheet.lastUpdated
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

    	result.dateCreated = outSrcPurchaseSheetDet.dateCreated
	    result.lastUpdated = outSrcPurchaseSheetDet.lastUpdated
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

		result
    }

    def saleSheetParseJson(saleSheet){
    	def result = [:]

    	result.dateCreated = saleSheet.dateCreated
	    result.lastUpdated = saleSheet.lastUpdated
	    result.site = saleSheet.site

        result.id = saleSheet.id
    	result.name = saleSheet.name
		result.typeName = saleSheet.typeName

		if(saleSheet.customer){
			result.customer = saleSheet.customer
			result["customer.id"] = saleSheet.customer.id
	        result["customer.name"] = saleSheet.customer.name
	        result["customer.title"] = saleSheet.customer.title
	    }

		result
    }

    def saleSheetDetParseJson(saleSheetDet){
    	def result = [:]

    	result.dateCreated = saleSheetDet.dateCreated
	    result.lastUpdated = saleSheetDet.lastUpdated
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
	        result.customerOrder = saleSheetDet.customerOrderDet.customerOrder
	        result["customerOrder.id"] = saleSheetDet.customerOrderDet.customerOrder.id
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

		result
    }

}
