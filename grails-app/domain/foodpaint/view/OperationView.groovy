package foodpaint.view

class OperationView {
	
	String name //製程編號
	String title //製程名稱
	String description="" // 製程敘述

    static constraints = {
    	name unique:true
    }

    static mapping = {
        // datasource 'erp'
        table 'CMSMW'
        version false
        
        id generator: 'assigned', name: 'name'

        name column: 'MW001'
        title column: 'MW002'
        description column: 'MW003'

    }
}