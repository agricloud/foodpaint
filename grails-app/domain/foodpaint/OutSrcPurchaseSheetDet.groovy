package foodpaint

class OutSrcPurchaseSheetDet{

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
    static belongsTo=[outSrcPurchaseSheet:OutSrcPurchaseSheet]
    /**
     * 品項編號
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
     * 進貨數量
     */
	double qty

    /**
     * 批號
     */
	Batch batch

    /**
     * 製令
     */
    ManufactureOrder manufactureOrder


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
        Object[] args = [OutSrcPurchaseSheetDet]
        """
        ${messageSource.getMessage("${i18nType}.outSrcPurchaseSheetDet.label", args, Locale.getDefault())}: ${typeName}-${name}-${sequence}
        """
    }
}
