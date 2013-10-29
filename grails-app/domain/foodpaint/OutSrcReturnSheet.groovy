package foodpaint


    /*
    * 託外退貨單
    */
class OutSrcReturnSheet extends DefaultSheet{

    static hasMany=[outSrcReturnSheetDet:OutSrcReturnSheetDet]
    /*
    * 退貨廠商
    */
	Supplier supplier



    static constraints = {
        outSrcReturnSheetDet nullable:true

    }
}
