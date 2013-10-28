package foodpaint.view

class WorkstationView {

	String name
	String title
	
    static constraints = {
    	name unique:true
    }

    static mapping = {
        datasource 'erp'
        table 'CMSMD'
        version false
        
        id generator: 'assigned', name: 'name'

        name column: 'MD001', sqlType: "nchar"
        title column: 'MD002', sqlType: "nchar"

    }
}
