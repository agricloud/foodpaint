package foodpaint.view


class SupplierView {

    Integer flag
    
    /*
    * 編號
    */
	String name


    /*
    * 名稱
    */
	String title = ""


    /*
    * 供應商所屬國家
    */
    //暫時預設為台灣，稍後決定如何與ＥＲＰ關聯
	// String country="TAIWAN"



    static constraints = {
    	name unique: true
    }

    static mapping = {
        datasource 'erp'
        table 'PURMA'
        version false
        
        id generator: 'assigned', name: 'name'
        flag column: 'FLAG', sqlType:"numeric"
        name column: 'MA001', sqlType: "nchar"
        title column: 'MA003', sqlType: "nchar"
        //country column: 'MA006', sqlType: "nchar"
    }
}
