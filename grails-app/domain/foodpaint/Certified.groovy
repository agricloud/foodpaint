package foodpaint
/**
 * 文件單身
 */
class Cerified{

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
	 * 修改日期（自動欄位）
	 */
	String name

	String title

	
	String cerifiedType


	String description


	Date effectStartDate

	Date effectEndDate

     static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        		importFlag nullable:true
		site nullable:true
		editor nullable:true
		creator nullable:true
		cerifiedType nullable:true
		name nullable:true
		description nullable:true
		effectStartDate nullable:true
		effectEndDate nullable:true
	}

	def getGrailsApplication(){
	    return grailsApplication
	}

	def getMessageSource(){
	    return messageSource
	}

	public String toString(){
		def i18nType = getGrailsApplication().config.grails.i18nType
		Object[] args = [Cerified]
		"""
		${getMessageSource().getMessage("${i18nType}.cerified.label", args, Locale.getDefault())}: ${name}/${title}
		"""
    }
}