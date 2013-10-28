package foodpaint.view

    /*
    * 進貨單身
    */
class PurchaseSheetDetView implements Serializable{
	

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

        id composite: ['typeName', 'name', 'sequence']
        version false

        typeName column: 'TH001'
        name column: 'TH002'
        sequence column: 'TH003'
        itemName  column: 'TH004'
        batchName column: 'TH010'
        qty column: 'TH007'

    }   


}
