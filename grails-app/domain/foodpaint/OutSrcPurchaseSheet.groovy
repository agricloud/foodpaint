package foodpaint



    /*
    * 託外進貨單
    */
class OutSrcPurchaseSheet extends DefaultSheet{

	static hasMany=[outSrcPurchaseSheetDet:OutSrcPurchaseSheetDet]
    /*
    * 進貨廠商
    */
	Supplier supplier


    static constraints = {
        outSrcPurchaseSheetDet nullable:true

    }
}
