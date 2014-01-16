package foodpaint.view
    /*
    * 託外進貨單
    */
class OutSrcPurchaseSheetView implements Serializable{

    int importFlag = -1
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

    /*
    * 託外進貨日期
    */
    String outSrcPurchaseDate


    static constraints = {

    }


    static mapping = {
        datasource 'erp'
        table 'MOCTH'
        version false

        id composite: ['typeName','name']
        importFlag column: 'FLAG', sqlType:"numeric"
        typeName column: 'TH001', sqlType: "nchar"
        name column: 'TH002', sqlType: "nchar"
        supplierName column: 'TH005', sqlType: "nchar"
        outSrcPurchaseDate column: 'TH003', sqlType: "nchar"
        
    }      
}
