package foodpaint
/**
 * 記錄生產的 batch 是由哪些原物料的 batch 所組成
 */
class BatchSource {

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

	static belongsTo = [batch: Batch]
	Batch childBatch
    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
    	site nullable:true
        editor nullable:true
        creator nullable:true
    	childBatch(unique:['batch','site'])
    }

    public String toString(){
        def i18nType = grailsApplication.config.grails.i18nType
        Object[] args = [BatchSource]
        """
        ${messageSource.getMessage("${i18nType}.batchSource.batchSource.label", args, Locale.getDefault())}: ${batch.name}/
        ${messageSource.getMessage("${i18nType}.batchSource.childBatch.name.label", args, Locale.getDefault())}: ${childBatch.name}
        """
    }
}