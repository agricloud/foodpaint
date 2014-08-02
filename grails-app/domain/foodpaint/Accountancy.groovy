package foodpaint
import grails.converters.*


class Accountancy {
    int importFlag = -1

    /**
     * 會計科目級別
     */
    String  accountTitlelevel

    /**
     * 所屬會計科目編號
     */
    String belongAccountTitleNumber
    
    /**
     * 所屬會計科目名稱
     */
    String belongaccountTitleName
    /**
     * 會計科目編號
     */
    String accountTitleNumber


    /**
     * 會計科目名稱
     */
    String accountTitleName


    /**
     * 建立日期（自動欄位）
     */
    Date dateCreated

    /**
     * 修改日期（自動欄位）
     */
    Date lastUpdated


    /**
     *備註
     */
    String remark

    static mapping = {
        importFlag  defaultValue: -1
    }
 
    static constraints = {
        // accountTitlelevel nullable:false
        // belongAccountTitleNumber nullable:false
        // belongaccountTitleName nullable:false
        // accountTitleNumber unique: true, blank: false
        // accountTitleName nullable:false
        // remark unique: true, blank: false
	}
	
}
