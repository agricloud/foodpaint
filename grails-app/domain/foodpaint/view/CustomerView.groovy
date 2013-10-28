package foodpaint.view

class CustomerView {
	String name
	String title
	String email=""
	String address=""


    static constraints = {
    	name unique:true
    	email nullable:true
    	address nullable:true
    }

    static mapping = {
        // datasource 'erp'
        table 'COPMA'
        version false
        
        id generator: 'assigned', name: 'name'

        name column: 'MA001', sqlType: "nchar"
        title column: 'MA003', sqlType: "nchar"
        email column: 'MA009', sqlType: "nchar"
        address column: 'MA023', sqlType: "nchar"
      
    }

}
