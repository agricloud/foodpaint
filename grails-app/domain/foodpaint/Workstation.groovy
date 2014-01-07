package foodpaint

class Workstation {
	Integer importFlag = -1

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

	String name
	String title
	String description
	
    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
    	site nullable:true
        editor nullable:true
        creator nullable:true
    	name unique:true, blank: false
    	description nullable:true
    }
}
