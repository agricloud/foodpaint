package foodpaint
 /*
 * 製造命令
 */
class ManufactureOrder {
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

    Workstation workstation
    Supplier supplier

    /**
     * 訂單單身
     */
    CustomerOrderDet customerOrderDet

    static hasMany = [
        stockInSheetDets: StockInSheetDet,
        outSrcPurchaseSheetDets: OutSrcPurchaseSheetDet,
        outSrcPurchaseReturnSheetDets: OutSrcPurchaseReturnSheetDet,
        materialSheetDets: MaterialSheetDet,
        materialSheetReturnDets: MaterialReturnSheetDet
    ]

    /**
     * 品項編號
     */
    Item item

    /**
     * 生產量
     */
    double qty

    /**
     * 預計批號
     */
    Batch batch

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
        name(unique:['typeName','site'])
        name blank: false
        typeName blank: false
        site nullable:true
        editor nullable:true
        creator nullable:true
        batch nullable:true
        workstation nullable:true
        supplier nullable:true
        customerOrderDet nullable:true
        qty min: 0.0d
    }
    public String toString(){
        "製令：${typeName}-${name}"
    }
}
