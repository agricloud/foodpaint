package foodpaint

class DefaultTable {

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

	Integer version

    static mapping = {
        tablePerHierarchy false
    }
	
    static constraints = {
    	site nullable:true
    }

    transient beforeUpdate = {
        throw new RuntimeException('update not allowed')
    }

}
