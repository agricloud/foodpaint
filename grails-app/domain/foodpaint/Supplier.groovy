
package foodpaint

class Supplier {

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
     * 編號
     */
    String name

    /**
     * 名稱
     */
    String title
    
    /**
     * 供應商所屬國家
     */
    Country country

    String tel
    String fax
    /**
     * 連絡人
     */
    String contact
    String email
    String address

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
        country nullable:true
        tel nullable:true
        fax nullable:true
        contact nullable:true
        email nullable:true, email: true
        address nullable:true

    }
    
    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }
    
    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [Supplier]
        """
        ${getMessageSource().getMessage("${i18nType}.supplier.label", args, Locale.getDefault())}: ${name}/${title}
        """
    }
}