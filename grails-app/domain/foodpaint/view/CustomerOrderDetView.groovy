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

    /*
    * 訂單數量
    */
    
	// Integer qty

    static constraints = {
        // qty nullable:true
    }
    static mapping = {
        // datasource 'erp'
        table 'COPTD'

        id composite: ['name', 'typeName']
        version false
        name column: 'TD001'
        typeName column: 'TD002'
        sequence column: 'TD003'

    }  

}
