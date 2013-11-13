package foodpaint


    /*
    * 託外退貨單
    */
class OutSrcReturnSheet {
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
    static hasMany=[outSrcReturnSheetDets:OutSrcReturnSheetDet]
    /*
    * 退貨廠商
    */
	Supplier supplier


    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        name unique:'typeName'
        site nullable:true
        outSrcReturnSheetDets nullable:true

    }
}
