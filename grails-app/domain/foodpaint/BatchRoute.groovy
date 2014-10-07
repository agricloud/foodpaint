package foodpaint

class BatchRoute {
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

	static belongsTo=[batch:Batch]
	Workstation workstation
    Supplier supplier
	Operation operation
	int sequence

    /**
     * 開始時間
     */
	Date startDate


    /**
     * 結束時間
     */
	Date endDate

    static mapping = {
        importFlag  defaultValue: -1
    }	 

    static constraints = {
        importFlag nullable:true
        site nullable:true
        editor nullable:true
        creator nullable:true
        sequence(unique:['batch','site'])
    	startDate nullable:true
    	endDate nullable:true
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
        Object[] args = [BatchRoute]
        """
        ${getMessageSource().getMessage("${i18nType}.batchRoute.label", args, Locale.getDefault())}: ${batch.name}/${sequence}
        """
    }
}
