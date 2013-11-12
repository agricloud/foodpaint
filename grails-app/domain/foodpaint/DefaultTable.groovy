package foodpaint

class DefaultTable {

	Integer importFlag = -1

    /**
     * 廠別
     */
	Site site

    /**
     * 修改者
     */
	String editor = ""

	/**
	 * 建立者
	 */
	String creator = ""

	/**
	 * 建立日期（自動欄位）
	 */
	Date dateCreated

	/**
	 * 修改日期（自動欄位）
	 */
	Date lastUpdated



    static mapping = {
        tablePerHierarchy false
        importFlag  defaultValue: -1
    }
	
    static constraints = {
    	site nullable:true
    	
    	//flag nullable:true
    }

}
