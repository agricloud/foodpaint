package foodpaint.view

class StockInSheetDetView implements Serializable{

    Integer importFlag = -1
    
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
    * 庫別
    */

    String warehouse

    /*
    * 儲位
    */
    String stockLocationName

    /*
    * 製令
    */
    String manufactureOrderTypeName
    String manufactureOrderName

    static constraints = {
        stockLocationName nullable:true      

    }

    static mapping = {
        datasource 'erp'
        table 'MOCTG'
        version false

        id composite: ['typeName', 'name', 'sequence']
        importFlag column: 'FLAG', sqlType:"numeric"
        typeName column: 'TG001', sqlType: "nchar"
        name column: 'TG002', sqlType: "nchar"
        sequence column: 'TG003', sqlType: "nchar"
        itemName  column: 'TG004', sqlType: "nchar"
        batchName column: 'TG017', sqlType: "nchar"
        warehouse  column: 'TG010', sqlType: "nchar"
        stockLocationName column: 'TG034', sqlType: "nchar"
        manufactureOrderTypeName column: 'TG014', sqlType: "nchar"
        manufactureOrderName column: 'TG015', sqlType: "nchar"
    }    
}
