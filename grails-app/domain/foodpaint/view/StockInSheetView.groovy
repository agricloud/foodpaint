package foodpaint.view

    /*
    * 入庫單
    */
class StockInSheetView implements Serializable{

    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    /*
    * 生產線別
    */
    String workstationName


    static constraints = {

    }

    static mapping = {
        datasource 'erp'
        table 'MOCTF'

        id composite: ['typeName','name']
        version false

        typeName column: 'TF001', sqlType: "nchar"
        name column: 'TF002', sqlType: "nchar"
        workstationName column: 'TF011', sqlType: "nchar"
        
    }    
}