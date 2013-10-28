package foodpaint

    /*
    * 入庫單
    */
class StockInSheet extends DefaultSheet{

	static hasMany=[stockInSheetDet:StockInSheetDet]
    /*
    * 生產線別
    */

    Workstation workstation

    static constraints = {
        stockInSheetDet nullable:true
    }
}
