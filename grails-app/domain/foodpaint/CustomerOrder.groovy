package foodpaint


    /*
    * 客戶訂單
    */
class CustomerOrder extends DefaultSheet{



	/*
	* 客戶編號
	*/
	Customer customer

	/*
	* 到期日
	*/
	Date dueDate=new Date()

    static constraints = {
    	dueDate nullable:true
    }
}
