package foodpaint

class ItemRoute {
    int importFlag = -1

    /**
     * 廠別
     */
    Site site

    /**
     * 修改者
     */
    String editor

    /**
     * 建立者
     */
    String creator

    /**
     * 建立日期（自動欄位）
     */
    Date dateCreated

    /**
     * 修改日期（自動欄位）
     */
    Date lastUpdated

	static belongsTo = [item: Item]
	int sequence
	Operation operation
	Workstation workstation

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        site nullable:true
        editor nullable:true
        creator nullable:true
    	sequence unique:true
    }

    public String toString(){
    	"品項：${item.title}，途程項次:${sequence}"
    }
}
