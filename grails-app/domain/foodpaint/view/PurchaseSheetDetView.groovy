package foodpaint.view

    /*
    * 進貨單身
    */
class PurchaseSheetDetView implements Serializable{
	
    Integer flag
    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    Integer sequence

    /*
    * 品項編號
    */
    String itemName


    /*
    * 批號
    */
    String batchName


    /*
    * 進貨數量
    */
    Integer qty


    static constraints = {

    }

    static mapping = {
        datasource 'erp'
        table 'PURTH'
        version false
        flag column: 'FLAG', sqlType:"numeric"

        id composite: ['typeName', 'name', 'sequence']
        typeName column: 'TH001', sqlType: "nchar"
        name column: 'TH002', sqlType: "nchar"
        sequence column: 'TH003', sqlType: "nchar"
        itemName  column: 'TH004', sqlType: "nchar"
        batchName column: 'TH010', sqlType: "nchar"
        qty column: 'TH007', sqlType: "numeric"

    }   


}
