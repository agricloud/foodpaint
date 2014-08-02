// package foodpaint

// /*
//  * 收款單
//  */
// class ReceivableSheet{
//     int importFlag = -1

//     /**
//      * 廠別
//      */
//     Site site

//     /**
//      * 修改者
//      */
//     String editor

//     /**
//      * 建立者
//      */
//     String creator

//     /**
//      * 單據建立日期（自動欄位）
//      */
//     Date dateCreated

//     /**
//      * 修改日期（自動欄位）
//      */
//     Date lastUpdated

//     /**
//      * 收款日期
//      */
//     Date receivableDate


//      /**
//      * 收款方式
//      */
//     String receivables


//     // Currency currency
//      /**
//      * 匯率
//      */
//     long rate
    
//      /**
//      * 幣別
//      */
//     String currency


//    //  /*
//    //  * 業務員之後會新增class clerk
//    //  */
//    //  String clerk
 
   
//    /*
//     * 單別
//     */
//     String typeName


//     /*
//     * 單號
//     */
//     String name
//     static hasMany=[receivableSheetDets:ReceivableSheetDet]
    
//     /*
//     * 客戶
//     */
//     Customer customer


//     //  /*
//     // * 原幣應收金額
//     // */
//     // Double originalAmounts

//     //  /*
//     // * 原幣總稅額
//     // */
//     // Double originaltax

//     // /*
//     // * 原幣應收總計
//     // */
//     // Double originalTotalAmount


//      /*
//     * 本幣應收合計
//     */
//     long  subamounts

//      /*
//     * 本幣總稅額
//     */
//     long  tax

//     /*
//     * 本幣應收合計金額    
//     */
//     long  totalAmount
  
//     /*
//     * 差額    
//     */
//     long   balance
  
//     // /*
//     // * 原幣差額    
//     // */
//     // long  originalBalance
  


//     // /*
//     // * 原幣已收金額
//     // */
//     // Double originalReceived

//     // /*
//     // * 本幣已收金額
//     // */
//     // Double received

//     // /**
//     //  *備註
//     //  */
//     // String remark

//     // String  signoff
//     static mapping = {
//         importFlag  defaultValue: -1
//     }
//     static constraints = {
//         name unique:'typeName'
//         site nullable:true
//         editor nullable:true
//         creator nullable:true
//     }
// }
