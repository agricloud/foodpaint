package foodpaint

class Warehouse {

    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    String name
	String title
	String remark

	static constraints = {
        site nullable:true
        editor nullable:true
		creator nullable:true
		name unique: true, blank: false
		remark nullable: true
	}

	public String toString(){
    	"倉庫編號：${name}，倉庫名稱：${title}"
    }
}
