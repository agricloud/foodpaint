package foodpaint

    /*
    * 入庫單
    */
class StockInSheet extends DefaultSheet{

	static hasMany=[stockInSheetDets:StockInSheetDet]
    /*
    * 生產線別
    */

    Workstation workstation

    static constraints = {
        stockInSheetDets nullable:true
    }
}
