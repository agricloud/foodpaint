package foodpaint.view


    /*
    * 客戶訂單單身
    */
class CustomerOrderDetView implements Serializable{

    /*
    * 單別
    */
    String typeName=""


    /*
    * 單號
    */
    String name=""

    Integer sequence
    String itemName

    /*
    * 訂單數量
    */
    
	// Integer qty

    static constraints = {
        // qty nullable:true
    }
    static mapping = {
        datasource 'erp'
        table 'COPTD'

        id composite: ['typeName', 'name', 'sequence']
        version false
        name column: 'TD001', sqlType: "nchar"
        typeName column: 'TD002', sqlType: "nchar"
        sequence column: 'TD003', sqlType: "nchar"
        itemName column: 'TD004', sqlType: "nchar"

    }  

}
