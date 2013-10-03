package foodpaint


    /*
    * 製造命令
    */
class ManufactureOrder extends DefaultSheet{

    /*
    * 訂單單身
    */
    CustomerOrderDet customerOrderDet

    static hasMany = [
        stockInSheetDet: StockInSheetDet,
        materialSheetDet: MaterialSheetDet
    ]

    /*
    * 品項編號
    */
    Item item

    /*
    * 生產量
    */
    Integer qty


    static constraints = {
        customerOrderDet nullable:true
        stockInSheetDet nullable:true
        materialSheetDet nullable:true
    }
}
