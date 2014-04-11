package foodpaint

import org.springframework.transaction.annotation.Transactional

class InventoryDetailService {

	def messageSource
	def domainService
	def inventoryService

	@Transactional
	def replenish(warehouseId,warehouseLocationId,itemId,batchName,qty){
		Object[] args=[]
		if(qty>=0){

			inventoryService.replenish(warehouseId,itemId,qty)

			def warehouse = Warehouse.get(warehouseId)
			def warehouseLocation = WarehouseLocation.get(warehouseLocationId)
			def item = Item.get(itemId)
			def batch = Batch.findByName(batchName)

			def inventoryDetail = InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse,warehouseLocation,item,batch)

			if(!inventoryDetail){
				if(warehouseLocation.warehouse == warehouse){
					inventoryDetail = new InventoryDetail()
					inventoryDetail.warehouse = warehouse
					inventoryDetail.warehouseLocation = warehouseLocation
					inventoryDetail.item = item
					inventoryDetail.batch = batch
					inventoryDetail.qty = qty
					domainService.save(inventoryDetail)
				}
				else{
					args = [warehouse, warehouseLocation]
					return [success:false, message: messageSource.getMessage("inventoryDetail.warehouseLocation.not.belong.to.warehouse", args, Locale.getDefault())]
				}
			}
			else{
				inventoryDetail.qty += qty
			}
			return [success:true, inventoryDetail:inventoryDetail]
		}
		else
			return [success:false ,message: messageSource.getMessage("inventoryDetail.qty.must.be.more.than.zero", args, Locale.getDefault())]
	}

	@Transactional
	def consume(warehouseId,warehouseLocationId,itemId,batchName,qty){
		Object[] args=[]
		if(qty>=0){

			inventoryService.consume(warehouseId,itemId,qty)

			def warehouse = Warehouse.get(warehouseId)
			def warehouseLocation = WarehouseLocation.get(warehouseLocationId)
			def item = Item.get(itemId)
			def batch = Batch.findByName(batchName)

			def inventoryDetail = InventoryDetail.findByWarehouseAndWarehouseLocationAndItemAndBatch(warehouse,warehouseLocation,item,batch)
			if(inventoryDetail && inventoryDetail.qty >= qty){
				inventoryDetail.qty -= qty
				return  [success:true]
			}
			else{
				args = [warehouse, warehouseLocation, item, batch]
				return [success:false, message: messageSource.getMessage("inventoryDetail.quantity.not.enough", args, Locale.getDefault())]
			}
		}
		else
			return [success:false ,message: messageSource.getMessage("inventoryDetail.qty.must.be.more.than.zero", args, Locale.getDefault())]
	}

	def indexByBatchAndGroupByWarehouse(batchName){
		def batch=Batch.findByName(batchName)

		def inventoryDetails = InventoryDetail.executeQuery("SELECT warehouse.id,warehouse.name,warehouse.title,item.id,item.name,item.title,batch.id,batch.name,SUM(qty) FROM InventoryDetail WHERE batch.id = ? GROUP BY warehouse.id",[batch.id])

		return inventoryDetails
	}
	

}
