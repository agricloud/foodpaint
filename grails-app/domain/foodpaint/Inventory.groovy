package foodpaint

class Inventory {

    def grailsApplication
    def messageSource

    String importFlag = -1
    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo=[
        warehouse:Warehouse,
        item:Item
    ]

    double qty = 0.0d

    /**
     *  最近入庫日期
     */
    Date lastInDate = new Date()

    /**
     *  最近出庫日期
     */
    Date lastOutDate = new Date()
    
    static mapping = {
        importFlag  defaultValue: -1
    }
	static constraints = {
        importFlag nullable:true
        item(unique:['warehouse','site'])
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
        Object[] args = [Inventory]
        """
        ${getMessageSource().getMessage("${i18nType}.inventory.label", args, Locale.getDefault())}: ${warehouse.name}/${warehouse.title}/${item.name}/${item.title}
        """
    }
}
