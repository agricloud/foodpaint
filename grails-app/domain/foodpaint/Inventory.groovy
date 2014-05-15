package foodpaint

class Inventory {

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo=[
        warehouse:Warehouse,
        item:Item
    ]

    long qty = 0

    /**
     *  最近入庫日期
     */
    Date lastInDate = new Date()

    /**
     *  最近出庫日期
     */
    Date lastOutDate = new Date()

	static constraints = {
        item unique:'warehouse'
        site nullable:true
        editor nullable:true
		creator nullable:true
	}

	public String toString(){
    	"倉庫：${warehouse.name}, 品項：${item.name}"
    }
}
