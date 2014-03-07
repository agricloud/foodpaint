package foodpaint

class Inventory {

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo=[warehouse:Warehouse]
    static belongsTo=[item:Item]

    long qty = 0

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
