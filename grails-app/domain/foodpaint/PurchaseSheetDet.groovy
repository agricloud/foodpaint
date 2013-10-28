package foodpaint

    /*
    * 進貨單身
    */
class PurchaseSheetDet extends DefaultSheetDet{
	

    static belongsTo=[purchaseSheet:PurchaseSheet]
    /*
    * 品項編號
    */
    Item item


    /*
    * 批號
    */
    Batch batch


    /*
    * 進貨數量
    */
    Integer qty


    static constraints = {

    }
}
