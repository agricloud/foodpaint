package foodpaint
/**
 * 客戶訂單
 */
class CustomerOrder {

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
	static hasMany=[customerOrderDets:CustomerOrderDet]
	/**
	 * 客戶編號
	 */
	Customer customer
    /**
     * 送貨地址
     */
    String shippingAddress

	/**
	 * 到期日
	 */
	Date dueDate=new Date()


    static mapping = {
        importFlag  defaultValue: -1
    }

    static constraints = {
        importFlag nullable:true
        site nullable:true
        editor nullable:true
        creator nullable:true
    	name(unique:['typeName','site'])
        name blank: false
        typeName blank: false
    	site nullable:true
    	dueDate nullable:true
    	customer nullable:true
        shippingAddress nullable:true
    }

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }

    public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [CustomerOrder]

        """
        ${getMessageSource().getMessage("${i18nType}.customerOrder.label", args, Locale.getDefault())}: ${typeName}-${name}
        """
    }
}
