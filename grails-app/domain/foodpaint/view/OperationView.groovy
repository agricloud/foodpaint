package foodpaint.view

class OperationView {

    Integer flag
	String name //製程編號
	String title="" //製程名稱
	String description="" // 製程敘述

    static constraints = {
    	name unique:true
    }

    static mapping = {
        datasource 'erp'
        table 'CMSMW'
        version false
        
        id generator: 'assigned', name: 'name'
        flag column: 'FLAG', sqlType:"numeric"
        name column: 'MW001', sqlType: "nchar"
        title column: 'MW002', sqlType: "nchar"
        description column: 'MW003', sqlType: "nchar"

    }
}