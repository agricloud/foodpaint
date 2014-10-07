package foodpaint
/*
 * 銷貨單單頭
 */
class SaleSheet {

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
	static hasMany=[saleSheetDets:SaleSheetDet]
    /**
     * 客戶
     */
    Customer customer
    /**
     * 送貨地址
     */
    String shippingAddress

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
        shippingAddress nullable:true
    }
    
    public String toString(){
        def i18nType = grailsApplication.config.grails.i18nType
        Object[] args = [SaleSheet]
        """
        ${messageSource.getMessage("${i18nType}.saleSheet.label", args, Locale.getDefault())}: ${typeName}-${name}
        """
    }
}
