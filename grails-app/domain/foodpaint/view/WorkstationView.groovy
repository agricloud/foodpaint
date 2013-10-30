package foodpaint.view

class WorkstationView {

    Integer flag
    
	String name
	String title
	
    static constraints = {
    	name unique:true
    }

    static mapping = {
        datasource 'erp'
        table 'CMSMD'
        version false
        flag column: 'FLAG', sqlType:"numeric"
        
        id generator: 'assigned', name: 'name'
        name column: 'MD001', sqlType: "nchar"
        title column: 'MD002', sqlType: "nchar"

    }
}
