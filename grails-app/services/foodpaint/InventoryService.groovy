package foodpaint

import org.springframework.transaction.annotation.Transactional

class InventoryService {

	def messageSource
	def domainService

	@Transactional
	def replenish(params,warehouseId,itemId,qty,date){
		Object[] args=[]
		
		if(qty>=0){
			def warehouse = Warehouse.get(warehouseId)
			def item = Item.get(itemId)
			def site
			if(params["site.id"] && params["site.id"] != "null")
				site = Site.findById(params["site.id"])

			def inventory = Inventory.findByWarehouseAndItemAndSite(warehouse,item,site)

			if(!inventory){
				inventory = new Inventory()
				inventory.warehouse = warehouse
				inventory.item = item
				inventory.qty = qty
				inventory.site = site
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
	def consume(params,warehouseId,itemId,qty,date){
		Object[] args=[]

		if(qty>=0){

			def warehouse = Warehouse.get(warehouseId)
			def item = Item.get(itemId)
			def site
			if(params["site.id"] && params["site.id"] != "null")
				site = Site.findById(params["site.id"])

			def inventory = Inventory.findByWarehouseAndItemAndSite(warehouse,item,site)
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
