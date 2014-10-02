package foodpaint

class Customer {
	String importFlag = -1

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
    String tel
    String fax
    /**
     * 連絡人
     */
    String contact
	String email
	String address
    /**
     * 送貨地址
     */
    String shippingAddress

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
    	site nullable:true
        editor nullable:true
        creator nullable:true
    	name(unique:['site'])
        name blank: false
        tel nullable:true
        fax nullable:true
        contact nullable:true
    	email nullable:true
        address nullable:true
    	shippingAddress nullable:true
    }
    
    public String toString(){
        "客戶：${name}，名稱：${title}"
    }

}
