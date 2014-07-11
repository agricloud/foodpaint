package foodpaint

class Inventory {

    int importFlag = -1
    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo=[
        warehouse:Warehouse,
        item:Item
    ]

    double qty = 0.0d

    /**
     *  最近入庫日期
     */
    Date lastInDate = new Date()

    /**
     *  最近出庫日期
     */
    Date lastOutDate = new Date()
    
    static mapping = {
        importFlag  defaultValue: -1
    }
	static constraints = {
        importFlag nullable:true
        item(unique:['warehouse','site'])
        site nullable:true
        editor nullable:true
		creator nullable:true
	}

	public String toString(){
    	"倉庫：${warehouse.name}, 品項：${item.name}"
    }
}
