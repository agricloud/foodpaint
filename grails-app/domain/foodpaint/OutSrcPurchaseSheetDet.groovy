package foodpaint

class OutSrcPurchaseSheetDet extends DefaultSheetDet{

    /*
    * 品項編號
    */
	Item item


    /*
    * 數量
    */
	Integer qty


    /*
    * 批號
    */
	Batch batch


    /*
    * 制令
    */
    static hasOne = [manufactureOrder: ManufactureOrder]



    static constraints = {
    }
}
