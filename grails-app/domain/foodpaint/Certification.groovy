package foodpaint

class Certification {
    String importFlag = -1
    def grailsApplication
    def messageSource


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



static hasMany=[certificationDocuments:CertificationDocuments]

    // static hasMany = [
    //     batchRoutes: BatchRoute,
    //     batchSources: BatchSource,
    //     inventoryDetails: InventoryDetail
    // ]
       /**
     * 證別
     */
    String typeName

    /**
     * 證號
     */
    String name
    double expectQty = 0.0d//ERP無此資料
    Date dueDate //失效日


    /**
     * 完成日期
     */
    Date certificateDate


    /**
     * 供應商
     */
    Supplier supplier

    /**
     * 供應商所屬國家
     */
    Country country = foodpaint.Country.TAIWAN

    static mapping = {
        importFlag  defaultValue: -1
    }

    static constraints = {
        importFlag nullable:true
        site nullable:true
        editor nullable:true
        creator nullable:true
        typeName nullable: true       
        name unique:true, blank: false
        expectQty min: 0.0d
        dueDate nullable: true
        certificateDate nullable: true
        supplier nullable: true
        
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
