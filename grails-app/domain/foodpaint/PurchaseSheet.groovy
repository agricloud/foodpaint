package foodpaint

    /*
    * 進貨單
    */
class PurchaseSheet extends DefaultSheet{


    static hasMany=[purchaseSheetDets:PurchaseSheetDet]
    /*
    * 供應商
    */

    Supplier supplier


    /*
    * 進貨日期
    */
    Date incomingDate=new Date()
    

    /*
    * 單據日期
    */
    Date orderDate=new Date()



    
    static constraints = {
        purchaseSheetDets nullable:true
    }
}
