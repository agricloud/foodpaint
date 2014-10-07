package foodpaint
/*
 * 客戶訂單單身
 */
class CustomerOrderDet{

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


    CustomerOrder customerOrder
    /**
     * 關連品項編號
     */
	Item item

    /**
     * 訂單數量
     */
	double qty

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
        site nullable:true
        editor nullable:true
        creator nullable:true
        sequence(unique:['name','typeName','site'])
        name blank: false
        typeName blank: false
        site nullable:true
        item nullable:true
        qty nullable:true
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
        Object[] args = [CustomerOrderDet]
        """
        ${getMessageSource().getMessage("${i18nType}.customerOrderDet.label", args, Locale.getDefault())}: ${typeName}-${name}-${sequence}
        """
    }
}
