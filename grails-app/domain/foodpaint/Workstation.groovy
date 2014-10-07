package foodpaint

class Workstation {

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

	String name
	String title
	String description
	
    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
    	importFlag nullable:true
    	site nullable:true
        editor nullable:true
        creator nullable:true
    	name(unique:['site'])
        name blank: false
    	description nullable:true
    }
    
    public String toString(){
        def i18nType = grailsApplication.config.grails.i18nType
        Object[] args = [Workstation]
        """
        ${messageSource.getMessage("${i18nType}.workstation.label", args, Locale.getDefault())}: ${name}/${title}
        """
    }
}
