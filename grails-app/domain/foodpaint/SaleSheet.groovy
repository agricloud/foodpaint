package foodpaint



    /*
    * 銷貨單單頭
    */
class SaleSheet extends DefaultSheet{

	static hasMany=[saleSheetDet:SaleSheetDet]
    /*
    * 客戶
    */
    Customer customer


    static constraints = {
        saleSheetDet nullable:true
    }
}
