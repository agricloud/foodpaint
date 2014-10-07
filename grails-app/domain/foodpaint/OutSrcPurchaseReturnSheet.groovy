package foodpaint
/*
 * 託外退貨單
 */
class OutSrcPurchaseReturnSheet {

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
    static hasMany=[outSrcPurchaseReturnSheetDets:OutSrcPurchaseReturnSheetDet]
    /**
     * 退貨廠商
     */
	Supplier supplier


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
    }

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }
    
    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [OutSrcPurchaseReturnSheet]
        """
        ${getMessageSource().getMessage("${i18nType}.outSrcPurchaseReturnSheet.label", args, Locale.getDefault())}: ${typeName}-${name}
        """
    }
}
