package foodpaint
/*
 * 銷退單單頭
 */
class SaleReturnSheet {
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
    /*
    * 單別
    */
    String typeName

    /*
    * 單號
    */
    String name
	static hasMany=[saleReturnSheetDets:SaleReturnSheetDet]

    /*
    * 客戶
    */
    Customer customer

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        name unique:'typeName'
        site nullable:true
        editor nullable:true
        creator nullable:true
    }
    public String toString(){
        "銷退單：${typeName}-${name}"
    }
}
