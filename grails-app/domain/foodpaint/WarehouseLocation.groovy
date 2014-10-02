package foodpaint

class WarehouseLocation {

    String importFlag = -1
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
    
    /**
     *  容量
     */
    double capacity = Double.MAX_VALUE
    /**
     *  容量單位
     */
    String capacityUnit

    String remark

    static mapping = {
        importFlag  defaultValue: -1
    }
	static constraints = {
        importFlag nullable:true
        site nullable:true
        editor nullable:true
		creator nullable:true
		name(unique:['warehouse','site'])
        name blank: false
        description nullable: true
        capacity min: 0.0d
        capacityUnit nullable: true
		remark nullable: true
	}

	public String toString(){
    	"儲位編號：${name}，儲位名稱：${title}"
    }
}
