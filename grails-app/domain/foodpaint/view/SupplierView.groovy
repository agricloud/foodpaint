package foodpaint.view


class SupplierView {

    /*
    * 編號
    */
	String name


    /*
    * 名稱
    */
	String title


    /*
    * 供應商所屬國家
    */
	String country



    static constraints = {
    	name unique: true
    }

    static mapping = {
        // datasource 'erp'
        table 'PURMA'
        version false
        
        id generator: 'assigned', name: 'name'

        name column: 'MA001'
        title column: 'MA003'
        country column: 'MA006'
    }
}
