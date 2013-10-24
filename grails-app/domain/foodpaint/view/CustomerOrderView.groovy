package foodpaint.view


    /*
    * 客戶訂單
    */
class CustomerOrderView implements Serializable{

    /*
    * 單別
    */
	String typeName=""


    /*
    * 單號
    */
    String name=""


	/*
	* 客戶編號
	*/
	String customerName

	/*
	* 到期日
	*/
	// String dueDate



    static constraints = {
    	// dueDate nullable:true
    	customerName nullable:true
    }

    static mapping = {
        // datasource 'erp'
        table 'COPTC'

        id composite: ['name', 'typeName']
        version false

        name column: 'TC001'
        typeName column: 'TC002'
        customerName column: 'TC004'

    }    
}
