package foodpaint



    /*
    * 託外進貨單
    */
class OutSrcPurchaseSheet {
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
    String typeName=""


    /*
    * 單號
    */
    String name=""
	static hasMany=[outSrcPurchaseSheetDets:OutSrcPurchaseSheetDet]
    /*
    * 進貨廠商
    */
	Supplier supplier

    /*
    * 託外進貨日期
    */
    Date outSrcPurchaseDate

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        name unique:'typeName'
        site nullable:true
        editor nullable:true
        creator nullable:true
        outSrcPurchaseSheetDets nullable:true

    }
}
