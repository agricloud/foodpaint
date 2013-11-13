package foodpaint

    /*
    * 進貨單
    */
class PurchaseSheet {
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
    /*
    * 單別
    */
    String typeName=""


    /*
    * 單號
    */
    String name=""

    static hasMany=[purchaseSheetDet:PurchaseSheetDet]
    /*
    * 供應商
    */

    Supplier supplier


    /*
    * 進貨日期
    */
    Date incomingDate=new Date()
    

    /*
    * 單據日期
    */
    Date orderDate=new Date()



    static mapping = {
        importFlag  defaultValue: -1
    }    
    static constraints = {
        name unique:'typeName'
        site nullable:true
        purchaseSheetDet nullable:true
        supplier  nullable:true
    }
}
