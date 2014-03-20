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
        storageLocation: StorageLocation,
        batch: Batch
    ]

    long qty = 0

	static constraints = {
        batch(unique:['warehouse','item'])
        site nullable:true
        editor nullable:true
		creator nullable:true
	}

	public String toString(){
    	"倉庫：${warehouse.name}, 品項：${item.name}, 批號：${batch.name}"
    }
}
