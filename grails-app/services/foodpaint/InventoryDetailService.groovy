package foodpaint

import org.springframework.transaction.annotation.Transactional

class InventoryDetailService {

	def messageSource
	def domainService
	def inventoryService

	@Transactional
	def replenish(warehouseId,storageLocationId,itemId,batchName,qty){
		if(qty>0){

			inventoryService.replenish(warehouseId,itemId,qty)

			def warehouse = Warehouse.get(warehouseId)
			def storageLocation = StorageLocation.get(storageLocationId)
			def item = Item.get(itemId)
			def batch = Batch.findByName(batchName)

			def inventoryDetail = InventoryDetail.findByWarehouseAndStorageLocationAndItemAndBatch(warehouse,storageLocation,item,batch)

			if(!inventoryDetail){
				inventoryDetail = new InventoryDetail()
				inventoryDetail.warehouse = warehouse
				inventoryDetail.storageLocation = storageLocation
				inventoryDetail.item = item
				inventoryDetail.batch = batch
				inventoryDetail.qty = qty
				domainService.save(inventoryDetail)
			}
			else{
				inventoryDetail.qty += qty
			}
			return [success:true]
		}
	}

	def consume(warehouseId,storageLocationId,itemId,batchName,qty){
		Object[] args=[]
		if(qty>0){

			inventoryService.consume(warehouseId,itemId,qty)

			def warehouse = Warehouse.get(warehouseId)
			def storageLocation = StorageLocation.get(storageLocationId)
			def item = Item.get(itemId)
			def batch = Batch.findByName(batchName)

			def inventoryDetail = InventoryDetail.findByWarehouseAndStorageLocationAndItemAndBatch(warehouse,storageLocation,item,batch)
			if(inventoryDetail && inventoryDetail.qty >= qty){
				inventoryDetail.qty -= qty
				return  [success:true]
			}
			else{
				args = [warehouse, item, batch]
				return [success:false, message: messageSource.getMessage("inventoryDetail.quantity.not.enough", args, Locale.getDefault())]
			}
		}
	}

}
