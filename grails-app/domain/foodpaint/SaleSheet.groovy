package foodpaint



    /*
    * 銷貨單單頭
    */
class SaleSheet extends DefaultSheet{

	static hasMany=[saleSheetDets:SaleSheetDet]
    /*
    * 客戶
    */
    Customer customer


    static constraints = {
        saleSheetDets nullable:true
    }
}
