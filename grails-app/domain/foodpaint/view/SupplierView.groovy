package foodpaint.view


class SupplierView {

    Integer importFlag = -1
    
    /*
    * 編號
    */
	String name


    /*
    * 名稱
    */
	String title

    String tel
    String email
    String address


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
        importFlag column: 'FLAG', sqlType:"numeric"
        name column: 'MA001', sqlType: "nchar"
        title column: 'MA003', sqlType: "nchar"
        //country column: 'MA006', sqlType: "nchar"
        tel column: 'MA008', sqlType: "nchar"
        email column: 'MA011', sqlType: "nchar"
        address column: 'MA014', sqlType: "nchar"
    }
}
