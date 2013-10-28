package foodpaint


    /*
    * 客戶訂單單身
    */
class CustomerOrderDet extends DefaultSheetDet{

    CustomerOrder customerOrder
    /*
    * 關連品項編號
    */
	Item item

    /*
    * 訂單數量
    */
	Integer qty

    static constraints = {
        item nullable:true
        qty nullable:true
        sequence unique:'customerOrder'
    }
}
