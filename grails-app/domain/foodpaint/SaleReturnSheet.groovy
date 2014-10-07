package foodpaint
/*
 * 銷退單單頭
 */
class SaleReturnSheet {

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
	static hasMany=[saleReturnSheetDets:SaleReturnSheetDet]

    /**
     * 客戶
     */
    Customer customer
    /**
     * 取件地址
     */
    String pickUpAddress

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
        pickUpAddress nullable:true
    }

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }
    
    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [SaleReturnSheet]
        """
        ${getMessageSource().getMessage("${i18nType}.saleReturnSheet.label", args, Locale.getDefault())}: ${typeName}-${name}
        """
    }
}
