package foodpaint

    /*
    * 領退料單頭
    */
class MaterialSheet {
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
	static hasMany=[materialSheetDets:MaterialSheetDet]
    /*
    * 生產線別，加工廠商
    */
    Workstation workstation
    static mapping = {
        importFlag  defaultValue: -1
    }

    static constraints = {
        name unique:'typeName'
        site nullable:true
        editor nullable:true
        creator nullable:true
        materialSheetDets nullable:true
        
    }
}
