package foodpaint

import org.springframework.transaction.annotation.Transactional

class InventoryDetailService {

	def grailsApplication
	def messageSource
	def domainService
	def inventoryService

	@Transactional
	def replenish(params,warehouseId,warehouseLocationId,itemId,batchName,qty,date){
		def i18nType = grailsApplication.config.grails.i18nType
		Object[] args=[]
		if(qty>=0){
			inventoryService.replenish(params,warehouseId,itemId,qty,date)

			def warehouse = Warehouse.get(warehouseId)
			def warehouseLocation = WarehouseLocation.get(warehouseLocationId)
			def item = Item.get(itemId)
			def batch = Batch.findByName(batchName)
			def site
			if(params["site.id"] && params["site.id"] != "null")
				site = Site.findById(params["site.id"])

			def inventoryDetail = InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatchAndSite(warehouse,warehouseLocation,item,batch,site)

			if(!inventoryDetail){
				if(warehouseLocation.warehouse == warehouse){
					inventoryDetail = new InventoryDetail()
					inventoryDetail.warehouse = warehouse
					inventoryDetail.warehouseLocation = warehouseLocation
					inventoryDetail.item = item
					inventoryDetail.batch = batch
					inventoryDetail.qty = qty	
					inventoryDetail.site = site
					
				}
				else{
					args = [warehouse, warehouseLocation]
					return [success:false, message: messageSource.getMessage("${i18nType}.inventoryDetail.warehouseLocation.not.belong.to.warehouse", args, Locale.getDefault())]
				}
			}
			else{
				inventoryDetail.qty += qty
	}
			if(date && date > inventoryDetail.lastInDate)
				inventoryDetail.lastInDate = date
			domainService.save(inventoryDetail)

			return [success:true, inventoryDetail:inventoryDetail]
		}
		else
			return [success:false ,message: messageSource.getMessage("${i18nType}.inventoryDetail.qty.must.be.more.than.zero", args, Locale.getDefault())]
	}

	@Transactional
	def consume(params,warehouseId,warehouseLocationId,itemId,batchName,qty,date){
		def i18nType = grailsApplication.config.grails.i18nType
		Object[] args=[]
		if(qty>=0){

			inventoryService.consume(params,warehouseId,itemId,qty,date)

			def warehouse = Warehouse.get(warehouseId)
			def warehouseLocation = WarehouseLocation.get(warehouseLocationId)
			def item = Item.get(itemId)
			def batch = Batch.findByName(batchName)
			def site
			if(params["site.id"] && params["site.id"] != "null")
				site = Site.findById(params["site.id"])

			def inventoryDetail = InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatchAndSite(warehouse,warehouseLocation,item,batch,site)
			if(inventoryDetail && inventoryDetail.qty >= qty){
				inventoryDetail.qty -= qty
				if(date && date > inventoryDetail.lastOutDate)
					inventoryDetail.lastOutDate = date
				domainService.save(inventoryDetail)
				return  [success:true]
			}
			else{
				args = [warehouse, warehouseLocation, item, batch]
				return [success:false, message: messageSource.getMessage("${i18nType}.inventoryDetail.quantity.not.enough", args, Locale.getDefault())]
			}
		}
		else
			return [success:false ,message: messageSource.getMessage("${i18nType}.inventoryDetail.qty.must.be.more.than.zero", args, Locale.getDefault())]
	}

	def indexByBatchAndGroupByWarehouse(params,batchName){
		def batch=Batch.findByName(batchName)
		def site
		if(params["site.id"] && params["site.id"] != "null")
				site = Site.findById(params["site.id"])

		def inventoryDetails = InventoryDetail.executeQuery("SELECT warehouse.id,warehouse.name,warehouse.title,item.id,item.name,item.title,batch.id,batch.name,SUM(qty) FROM InventoryDetail WHERE batch.id = ? AND site.id = ? GROUP BY warehouse.id",[batch.id,site.id])

		return inventoryDetails
	}
}
