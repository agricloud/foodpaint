package foodpaint


    /*
    * 退貨單頭
    */
class ReturnSheet extends DefaultSheet{

	static hasMany=[returnSheetDets:ReturnSheetDet]
    /*
    * 供應商
    */

    Supplier supplier

    static constraints = {
        returnSheetDets nullable:true
    }
}
