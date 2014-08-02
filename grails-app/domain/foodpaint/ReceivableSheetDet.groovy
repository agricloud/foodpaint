// package foodpaint
// /*
//  * 收款單單身
//  */
// class ReceivableSheetDet{
//     int importFlag = -1

//     /**
//      * 修改者
//      */
//     String editor

//     /**
//      * 建立者
//      */
//     String creator

//     /**
//      * 建立日期（自動欄位）
//      */
//     Date dateCreated

//     /**
//      * 修改日期（自動欄位）
//      */
//     Date lastUpdated
//     /*
//     * 單別
//     */
//     String typeName


//     /*
//     * 單號
//     */
//     String name
//     /*
//     * 訂單項次，取訂單編號最大單身項次 +1
//     */
//     int sequence
//     static belongsTo=[receivableSheet:ReceivableSheet]



//     /*
//     * 批號
//     */
//     Batch batch

//     /*
//     * 庫別
//     */

//     Warehouse warehouse

//     /*
//     * 儲位
//     */
//     WarehouseLocation warehouseLocation
//     /*
//     * 數量
//     */
//     long qty

//      /*
//     * 單價
//     */
//     long price

//      /*
//     * 金額
//     */
//     long subamounts

//      /*
//     * 稅額
//     */
//     long tax

//     /*
//     * 合計金額
//     */
//     long totalAmount

//     static mapping = {
//         importFlag  defaultValue: -1
//     }
//     static constraints = {
//         sequence(unique:['name','typeName'])
//         site nullable:true
//         editor nullable:true
//         creator nullable:true
//         batch nullable:true
// \
//     }
// }
