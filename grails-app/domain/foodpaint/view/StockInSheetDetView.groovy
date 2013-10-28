package foodpaint.view

class StockInSheetDetView implements Serializable{

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

    String warehouseName

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

        id composite: ['typeName', 'name', 'sequence']
        version false

        typeName column: 'TG001'
        name column: 'TG002'
        sequence column: 'TG003'
        itemName  column: 'TG004'
        batchName column: 'TG017'
        warehouseName  column: 'TG010'
        stockLocationName column: 'TG034'
        manufactureOrderTypeName column: 'TG014'
        manufactureOrderName column: 'TG015'
    }    
}
