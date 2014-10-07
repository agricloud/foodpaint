package foodpaint
/**
 * 銷退單單身
 */
class SaleReturnSheetDet{

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
    static belongsTo=[saleReturnSheet:SaleReturnSheet]
    /**
     * 品項
     */
    Item item


    /**
     * 訂單單身
     */
    CustomerOrderDet customerOrderDet

    /**
     * 銷貨單單身
     */
    SaleSheetDet saleSheetDet

    /**
     * 批號
     */
    Batch batch

    /**
     * 庫別
     */

    Warehouse warehouse

    /**
     * 儲位
     */
    WarehouseLocation warehouseLocation
    /**
     * 數量
     */
    double qty
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
        customerOrderDet nullable:true
        qty min: 0.0d
    }

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }
    
    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [SaleReturnSheetDet]
        """
        ${getMessageSource().getMessage("${i18nType}.saleReturnSheetDet.label", args, Locale.getDefault())}: ${typeName}-${name}-${sequence}
        """
    }
}
