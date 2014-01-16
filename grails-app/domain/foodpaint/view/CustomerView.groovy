package foodpaint.view

class CustomerView {

    int importFlag = -1
	String name
	String title = ""
	String email=""
	String address=""


    static constraints = {
    	name unique:true
    	email nullable:true
    	address nullable:true
    }

    static mapping = {
        datasource 'erp'
        table 'COPMA'
        version false
        
        id generator: 'assigned', name: 'name'
        importFlag column: 'FLAG', sqlType:"numeric"
        name column: 'MA001', sqlType: "nchar"
        title column: 'MA003', sqlType: "nchar"
        email column: 'MA009', sqlType: "nchar"
        address column: 'MA023', sqlType: "nchar"
      
    }

}
