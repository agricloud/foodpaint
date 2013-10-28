package foodpaint


    /*
    * 退貨單頭
    */
class ReturnSheet extends DefaultSheet{

	static hasMany=[returnSheetDet:ReturnSheetDet]
    /*
    * 供應商
    */

    Supplier supplier

    static constraints = {
        returnSheetDet nullable:true
    }
}
