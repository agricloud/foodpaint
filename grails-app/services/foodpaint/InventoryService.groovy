package foodpaint

import org.springframework.transaction.annotation.Transactional

class InventoryService {

	def messageSource
	def domainService

	@Transactional
	def replenish(warehouseId,itemId,qty){
		if(qty>=0){
			def warehouse = Warehouse.get(warehouseId)
			def item = Item.get(itemId)

			def inventory = Inventory.findByWarehouseAndItem(warehouse,item)

			if(!inventory){
				inventory = new Inventory()
				inventory.warehouse = warehouse
				inventory.item = item
				inventory.qty = qty
				domainService.save(inventory)
			}
			else{
				inventory.qty += qty
			}
			return  [success:true]
		}
		else
			return [success:false ,message: messageSource.getMessage("inventory.qty.must.be.more.than.zero", args, Locale.getDefault())]
	}

	@Transactional
	def consume(warehouseId,itemId,qty){
		Object[] args=[]

		if(qty>=0){

			def warehouse = Warehouse.get(warehouseId)
			def item = Item.get(itemId)

			def inventory = Inventory.findByWarehouseAndItem(warehouse,item)
			if(inventory && inventory.qty >= qty){
				inventory.qty -= qty
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
