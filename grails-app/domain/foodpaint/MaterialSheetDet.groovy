package foodpaint
/*
 * 領料單身
 */
class MaterialSheetDet{

    def grailsApplication
    def messageSource
    
    String importFlag = -1

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
    /**
     * 訂單項次，取訂單編號最大單身項次 +1
     */
    int sequence
    static belongsTo=[materialSheet:MaterialSheet]
    
    /**
     * 製令
     */
    static hasOne = [
        manufactureOrder: ManufactureOrder
    ]

    /**
     * 批號
     */
    Batch batch
    /**
     * 品項編號，材料編號
     */
    Item item

    /**
     * 庫別
     */
    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation
    /**
     * 領料數量
     */
    double qty = 0.0d

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
        sequence(unique:['name','typeName','site'])
        name blank: false
        typeName blank: false
        site nullable:true
        editor nullable:true
        creator nullable:true
        batch nullable:true
        qty min: 0.0d
    }
    
    public String toString(){
        def i18nType = grailsApplication.config.grails.i18nType
        Object[] args = [MaterialSheetDet]
        """
        ${messageSource.getMessage("${i18nType}.materialSheetDet.label", args, Locale.getDefault())}: ${typeName}-${name}-${sequence}
        """
    }
}
