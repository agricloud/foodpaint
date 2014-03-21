package foodpaint.view

    /*
    * 進貨單身
    */
class PurchaseSheetDetView implements Serializable{
	
    int importFlag = -1
    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    int sequence

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
    long qty


    /*
    * 庫別
    */

    String warehouseName

    /*
    * 儲位
    */
    String storageLocationName

    static constraints = {
        storageLocationName nullable:true 
    }

    static mapping = {
        datasource 'erp'
        table 'PURTH'
        version false

        id composite: ['typeName', 'name', 'sequence']
        importFlag column: 'FLAG', sqlType:"numeric"
        typeName column: 'TH001', sqlType: "nchar"
        name column: 'TH002', sqlType: "nchar"
        sequence column: 'TH003', sqlType: "nchar"
        itemName  column: 'TH004', sqlType: "nchar"
        batchName column: 'TH010', sqlType: "nchar"
        warehouseName  column: 'TH009', sqlType: "nchar"
        storageLocationName column: 'TH063', sqlType: "nchar"
        qty column: 'TH007', sqlType: "numeric"

    }   


}
