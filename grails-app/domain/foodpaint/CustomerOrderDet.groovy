package foodpaint


    /*
    * 客戶訂單單身
    */
class CustomerOrderDet{
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

    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name
    /*
    * 訂單項次，取訂單編號最大單身項次 +1
    */
    Integer sequence


    CustomerOrder customerOrder
    /*
    * 關連品項編號
    */
	Item item

    /*
    * 訂單數量
    */
	Integer qty
    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        site nullable:true
        editor nullable:true
        creator nullable:true
        sequence(unique:['name','typeName'])
        site nullable:true
        item nullable:true
        qty nullable:true
        
    }
}
