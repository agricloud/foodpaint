package foodpaint
/*
 * 銷貨單單頭
 */
class SaleSheet {
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
	static hasMany=[saleSheetDets:SaleSheetDet]
    /**
     * 客戶
     */
    Customer customer
    /**
     * 送貨地址
     */
    String shippingAddress

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        name unique:'typeName'
        site nullable:true
        editor nullable:true
        creator nullable:true
        shippingAddress nullable:true
    }
    public String toString(){
        "銷貨單：${typeName}-${name}"
    }
}
