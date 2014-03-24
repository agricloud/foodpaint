package foodpaint

class WarehouseLocation {

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo = [warehouse: Warehouse]
    static hasMany = [inventoryDetails: InventoryDetail]
    String name
	String title
    String description
    String remark

	static constraints = {
        site nullable:true
        editor nullable:true
		creator nullable:true
		name unique: true, blank: false
        description nullable: true
		remark nullable: true
	}

	public String toString(){
    	"儲位編號：${name}，儲位名稱：${title}"
    }
}
