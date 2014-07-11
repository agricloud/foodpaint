package foodpaint
/**
 * 記錄生產的 batch 是由哪些原物料的 batch 所組成
 */
class BatchSource {
	int importFlag = -1

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
        "批號：${batch}，來源批號：${childBatch}"
    }
}