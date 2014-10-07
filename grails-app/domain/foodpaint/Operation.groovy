package foodpaint

class Operation {

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
	
	String name //製程編號
	String title //製程名稱
	String description // 製程敘述
    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
    	importFlag nullable:true
    	name(unique:['site'])
        name blank: false
    	site nullable:true
        editor nullable:true
        creator nullable:true
        description nullable:true
    }

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }
    
    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Operation]
        """
        ${getMessageSource().getMessage("${i18nType}.operation.label", args, Locale.getDefault())}: ${name}/${title}
        """
    }
}