package foodpaint
/*
 * 銷貨單單頭
 */
class SaleSheet {
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
    /*
    * 單別
    */
    String typeName
    // *
    //  * 銷貨日期
     
    // Date saleDate



    // // Currency currency
    //  /**
    //  * 匯率
    //  */
    // long rate
    
    //  /**
    //  * 幣別
    //  */
    // String currency

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
    // long  subamounts

     
    // * 本幣總稅額
    
    // long  tax

    // /*
    // * 本幣應收合計金額    
    // */
    // long  totalAmount

    /*
    * 單號
    */
    String name
	static hasMany=[saleSheetDets:SaleSheetDet]
    /*
    * 客戶
    */
    Customer customer

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        name unique:'typeName'
        site nullable:true
        editor nullable:true
        creator nullable:true
        // subamounts nullable:true
        // tax nullable:true
        // totalAmount  nullable:true
    }
}
