package foodpaint



    /*
    * 領料單身
    */
class MaterialSheetDet{
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
    static belongsTo=[materialSheet:MaterialSheet]
    
    /*
    * 製令
    */
    static hasOne = [
        manufactureOrder: ManufactureOrder
    ]

    /*
    * 批號
    */
    Batch batch
    /*
    * 品項編號，材料編號
    */
    Item item
    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        sequence(unique:['name','typeName'])
        site nullable:true
        editor nullable:true
        creator nullable:true
        batch nullable:true
    }
}
