package foodpaint

class ItemRoute {

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

	static belongsTo = [item: Item]
	int sequence
	Operation operation
	Workstation workstation
    Supplier supplier

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
        site nullable:true
        editor nullable:true
        creator nullable:true
        sequence(unique:['item','site'])
        workstation nullable:true
        supplier nullable:true
    }

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }
    
    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [ItemRoute]
        """
        ${getMessageSource().getMessage("${i18nType}.itemRoute.label", args, Locale.getDefault())}: ${item.name}/${item.title}/${sequence}
        """
    }
}
