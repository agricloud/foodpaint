package foodpaint
import grails.converters.*


class AccountancyDet {
    int importFlag = -1

    /**
     * 所屬會計科目編號
     */
    String belongAccountTitleNumber
    
    /**
     * 所屬會計科目名稱
     */
    String belongAccountTitleName


    /**
     * 會計科目級別
     */
    String  accountTitlelevel

    /**
     * 會計科目編號
     */
    String accountTitleNumber

    /**
     * 會計科目名稱
     */ 
    String accountTitleName

     /**
     * 會計科目別名
     */ 
    String accountTitleAlias

     /**
     * 會計科目性質
     */ 
    String accountNature

    /**
     * 資產損益別
     */ 
    String  balanceorIncomeType

     /**
     * 餘額借貸別
     */ 
    String remainofdebitcreditType

     /**
     * 報表借貸別
     */ 
    String debitcreditofTatementsType

     /**
     * 會計科目類別
     */ 
    String accountType

    /**
     *  建立日期（自動欄位）
     */ 
     
    Date dateCreated

    /**
     * 修改日期（自動欄位）
     */
    Date lastUpdated

    int sequence
    static belongsTo=[accountancy :Accountancy ]
    /*

    /**
     *備註
     */
    String remark

    static mapping = {
        importFlag  defaultValue: -1
    }
 
    static constraints = {
        accountTitlelevel nullable:false
        belongAccountTitleNumber nullable:false
        belongaccountTitleName nullable:false
        accountTitleNumber unique: true, blank: false
        accountTitleName nullable:false
        accountTitleAlias nullable:true
        accountNature nullable:true
        balanceorIncomeType nullable:true
        remainofdebitcreditType nullable:true
        debitcreditofTatementsType nullable:true
        remark unique: true, blank: false

	}
	
}
