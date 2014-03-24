package foodpaint.view

class StockInSheetDetView implements Serializable{

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
    * 庫別
    */

    String warehouseName

    /*
    * 儲位
    */
    String warehouseLocationName

    /*
    * 製令
    */
    String manufactureOrderTypeName
    String manufactureOrderName

    // long qty

    static constraints = {
        warehouseLocationName nullable:true      

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
        warehouseName  column: 'TG010', sqlType: "nchar"
        warehouseLocationName column: 'TG034', sqlType: "nchar"
        manufactureOrderTypeName column: 'TG014', sqlType: "nchar"
        manufactureOrderName column: 'TG015', sqlType: "nchar"
    }    
}
