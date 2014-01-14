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
		if(customerOrder.customer){
			result.customer = customerOrder.customer
			result["customer.id"] = customerOrder.customer.id
	        result["customer.name"] = customerOrder.customer.name
	        result["customer.title"] = customerOrder.customer.title
	    }
		result.dueDate = customerOrder.dueDate

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

	    result.qty = customerOrderDet.qty

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
		result.qty = manufactureOrder.qty

		result
    }

}
