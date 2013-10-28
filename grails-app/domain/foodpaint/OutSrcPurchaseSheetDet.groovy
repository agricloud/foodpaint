package foodpaint

class OutSrcPurchaseSheetDet extends DefaultSheetDet{

    static belongsTo=[outSrcPurchaseSheet:OutSrcPurchaseSheet]
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
    * 製令
    */
    ManufactureOrder manufactureOrder



    static constraints = {

    }
}
