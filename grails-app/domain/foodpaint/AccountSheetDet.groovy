package foodpaint

/*
 * 結帳單身
 */
class AccountSheetDet{
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


     /**
     * 憑證日期（自動欄位）
     */
    Date documentDateCreated


    /*
    * 憑證單別
    */
    String typeName

 
    /*
    * 憑證單號
    */
    String name
  

   /*
    * 憑證項次
    */
    int   documentSequence
  

    /*
    * 項次
    */
    int sequence
    static belongsTo=[accountSheet: AccountSheet]

    /*
    * 來源銷貨銷退單
    */
    SaleSheetDet saleSheetDet

    SaleReturnSheetDet saleReturnSheetDet

    /*
    * 關聯發票
    */
    Inovice inovice
    
    /*
    * 原幣金額
    */
    Double originalAmounts

     /*
    * 原幣總稅額
    */
    Double originaltax

    /*
    * 原幣合計金額
    */
    Double originalTotalAmount


     /*
    * 本幣總金額
    */
    Double subamounts

     /*
    * 本幣總稅額
    */
    Double tax

    /*
    * 本幣合計金額
    */
    Double totalAmount

     /**
     * 狀態
     */
    String status

    /**
     *備註
     */
    String remark

    /**
     *是否簽核
     */
    String rauthorize
    

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        // sequence(unique:['name','typeName'])
        // site nullable:true
        // editor nullable:true
        // creator nullable:true
       
    }
}
