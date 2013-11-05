package foodpaint

class StockInSheetDet extends DefaultSheetDet{


    static belongsTo=[stockInSheet:StockInSheet]
    /*
    * 品項編號
    */

    Item item


    /*
    * 批號
    */
    Batch batch


    /*
    * 庫別
    */

    String warehouse

    /*
    * 儲位
    */
    String stockLocation

    static hasOne = [manufactureOrder: ManufactureOrder]

    static constraints = {
        batch nullable:true
        stockLocation nullable:true      
    }
}
