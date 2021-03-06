package foodpaint


    /*
    * 製造命令
    */
class ManufactureOrder {
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
    * 訂單單身
    */
    CustomerOrderDet customerOrderDet

    static hasMany = [
        stockInSheetDets: StockInSheetDet,
        materialSheetDets: MaterialSheetDet
    ]

    /*
    * 品項編號
    */
    Item item

    /*
    * 生產量
    */
    Integer qty

    /*
    * 預計批號
    */
    Batch batch

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        name unique:'typeName'
        site nullable:true
        editor nullable:true
        creator nullable:true
        customerOrderDet nullable:true
        stockInSheetDets nullable:true
        materialSheetDets nullable:true
        batch nullable:true
    }
}
