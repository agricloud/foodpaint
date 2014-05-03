package foodpaint

/*
 * 結帳單
 */
class AccountSheet{
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
     * 單據建立日期（自動欄位）
     */
    Date dateCreated

    /**
     * 修改日期（自動欄位）
     */
    Date lastUpdated

    /**
     * 結帳日期
     */
    Date accountDate

   /**
     * 預計收款日期
     */
    Date anticipationDate


     /**
     * 收款方式
     */
    String receivables


    // Currency currency
     /**
     * 匯率
     */
    Double rate
    
     /**
     * 幣別
     */
    String currency
    // /*
    // * 關聯發票
    // */
    // Inovice inovice
    
     /**
     * 憑證日期（自動欄位）
     */
   // Date documentDateCreated

    //  *
    //  * 狀態
     
    // String status

   //   /**
   //   * 結案碼 Y/N 當累積收款金額等於應收總金額時或已處理完結案碼為Y 否等於則為N
   //   */
   //  String  closedCode

   //  /*
   //  * 業務員之後會新增class clerk
   //  */
   //  String clerk
 
   
   /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name
    static hasMany=[accountSheetDets:AccountSheetDet]
    /*
    * 客戶
    */
    Customer customer

    /*
    * 折扣率
    */
    //Double discount
    
    //  /*
    // * 原幣應收金額
    // */
    // Double originalAmounts

    //  /*
    // * 原幣總稅額
    // */
    // Double originaltax

    // /*
    // * 原幣應收總計
    // */
    // Double originalTotalAmount


    //  /*
    // * 本幣應收合計
    // */
    // Double subamounts

    //  /*
    // * 本幣總稅額
    // */
    // Double tax

    // /*
    // * 本幣應收合計金額    long 
    // */
    // Double totalAmount

    // /*
    // * 原幣已收金額
    // */
    // Double originalReceived

    // /*
    // * 本幣已收金額
    // */
    // Double received

    // /**
    //  *備註
    //  */
    // String remark

    // String  signoff
    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        name unique:'typeName'
        site nullable:true
        editor nullable:true
        creator nullable:true
    }
}
