package foodpaint


    /*
    * 客戶訂單單身
    */
class CustomerOrderDet extends DefaultSheetDet{

    /*
    * 關連品項編號
    */
	Item item

    /*
    * 訂單數量
    */
	Integer qty

    static belongsTo=[head:CustomerOrderDet]

    static constraints = {
        item nullable:true
        qty nullable:true
    }
}
