package foodpaint.view


    /*
    * 客戶訂單
    */
class CustomerOrderView implements Serializable{


    Integer version
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
        datasource 'erp'
        table 'COPTC'
        version column: 'FLAG', sqlType:"numeric"

        id composite: ['name', 'typeName']
        name column: 'TC001', sqlType: "nchar"
        typeName column: 'TC002', sqlType: "nchar"
        customerName column: 'TC004', sqlType: "nchar"

    }    
}
