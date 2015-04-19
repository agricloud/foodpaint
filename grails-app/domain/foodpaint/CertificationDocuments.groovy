package foodpaint
/*
 * 單身
 */
class CertificationDocuments{
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
    /**
     * 
     */
    int sequence    

    static belongsTo=[certification:Certification]


    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
        sequence(unique:['name','typeName','site'])
        name blank: false
        typeName blank: false
        site nullable:true
        editor nullable:true
        creator nullable:true
        qty min: 0.0d
    }

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }
    
    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [CertificationDocuments]
        """
        ${getMessageSource().getMessage("${i18nType}.certificationDocuments.label", args, Locale.getDefault())}: ${typeName}-${name}-${sequence}
        """
    }
}

