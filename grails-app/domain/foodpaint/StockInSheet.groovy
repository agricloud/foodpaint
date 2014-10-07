package foodpaint
/**
 * 入庫單
 */
class StockInSheet {

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
	static hasMany=[stockInSheetDets:StockInSheetDet]
    /**
     * 生產線別
     */
    Workstation workstation

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
    
    public String toString(){
        def i18nType = grailsApplication.config.grails.i18nType
        Object[] args = [StockInSheet]
        """
        ${messageSource.getMessage("${i18nType}.stockInSheet.label", args, Locale.getDefault())}: ${typeName}-${name}
        """
    }
}
