package foodpaint

class InventoryDetail {

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo=[
        warehouse: Warehouse,
        item: Item,
        warehouseLocation: WarehouseLocation,
        batch: Batch
    ]

    double qty = 0

    /**
     *  最近入庫日期
     */
    Date lastInDate = new Date()

    /**
     *  最近出庫日期
     */
    Date lastOutDate = new Date()

	static constraints = {
        batch(unique:['warehouse','warehouseLocation','item'])
        site nullable:true
        editor nullable:true
		creator nullable:true
	}

	public String toString(){
    	"倉庫：${warehouse.name}, 儲位：${warehouseLocation.name}, 品項：${item.name}, 批號：${batch.name}"
    }
}
