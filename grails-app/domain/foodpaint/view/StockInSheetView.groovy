package foodpaint.view
    /*
    * 入庫單
    */
class StockInSheetView implements Serializable{

    Integer flag
    
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
        version false

        id composite: ['typeName','name']
        flag column: 'FLAG', sqlType:"numeric"
        typeName column: 'TF001', sqlType: "nchar"
        name column: 'TF002', sqlType: "nchar"
        workstationName column: 'TF011', sqlType: "nchar"
        
    }    
}
