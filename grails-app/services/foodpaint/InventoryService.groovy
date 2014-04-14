package foodpaint

import org.springframework.transaction.annotation.Transactional

class InventoryService {

	def messageSource
	def domainService

	@Transactional
	def replenish(warehouseId,itemId,qty,date){
		if(qty>=0){
			def warehouse = Warehouse.get(warehouseId)
			def item = Item.get(itemId)

			def inventory = Inventory.findByWarehouseAndItem(warehouse,item)

			if(!inventory){
				inventory = new Inventory()
				inventory.warehouse = warehouse
				inventory.item = item
				inventory.qty = qty	
			}
			else{
				inventory.qty += qty
			}
			if(date && date > inventory.lastInDate)
				inventory.lastInDate = date
			domainService.save(inventory)
			return  [success:true]
		}
		else
			return [success:false ,message: messageSource.getMessage("inventory.qty.must.be.more.than.zero", args, Locale.getDefault())]
	}

	@Transactional
	def consume(warehouseId,itemId,qty,date){
		Object[] args=[]

		if(qty>=0){

			def warehouse = Warehouse.get(warehouseId)
			def item = Item.get(itemId)

			def inventory = Inventory.findByWarehouseAndItem(warehouse,item)
			if(inventory && inventory.qty >= qty){
				inventory.qty -= qty
				if(date && date > inventory.lastOutDate)
					inventory.lastOutDate = date
				domainService.save(inventory)
				return  [success:true]
			}
			else{
				args = [warehouse, item]
				return [success:false, message: messageSource.getMessage("inventory.quantity.not.enough", args, Locale.getDefault())]
			}
		}
		else
			return [success:false ,message: messageSource.getMessage("inventory.qty.must.be.more.than.zero", args, Locale.getDefault())]
	}
	

}
