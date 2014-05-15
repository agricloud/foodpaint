package foodpaint
/**
 * 客戶訂單
 */
class CustomerOrder {
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
    /**
     * 單別
     */
    String typeName


    /**
     * 單號
     */
    String name
	static hasMany=[customerOrderDets:CustomerOrderDet]
	/**
	 * 客戶編號
	 */
	Customer customer

	/**
	 * 到期日
	 */
	Date dueDate=new Date()


    static mapping = {
        importFlag  defaultValue: -1
    }

    static constraints = {
        site nullable:true
        editor nullable:true
        creator nullable:true
    	name unique:'typeName'
    	site nullable:true
    	dueDate nullable:true
    	customer nullable:true
    }
    public String toString(){
        "訂單：${typeName}-${name}"
    }
}
