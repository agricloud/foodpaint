/*
 * 類似多公司別的資料表，以往在進行多公司別處理時都為拆開的資料庫
 * 在此，我們使用資料表來區分，所以一旦涉及可能需要區分公司別的資料表，皆會有 site 作為識別
 */
package foodpaint

class Site {

	String importFlag = -1
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
	String address

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
    	importFlag nullable:true
    	name unique:true, blank: false
        editor nullable:true
        creator nullable:true
        description nullable:true
		address nullable:true
    }
}
