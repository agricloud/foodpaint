package foodpaint.view
    /*
    * 託外進貨單
    */
class OutSrcPurchaseSheetView implements Serializable{

    Integer flag
    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    /*
    * 進貨廠商
    */
	String supplierName


    static constraints = {

    }


    static mapping = {
        datasource 'erp'
        table 'MOCTH'
        version false
        flag column: 'FLAG', sqlType:"numeric"

        id composite: ['typeName','name']
        typeName column: 'TH001', sqlType: "nchar"
        name column: 'TH002', sqlType: "nchar"
        supplierName column: 'TH005', sqlType: "nchar"
        
    }      
}
