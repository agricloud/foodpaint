package foodpaint.view



    /*
    * 託外進貨單
    */
class OutSrcPurchaseSheetView implements Serializable{

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

        id composite: ['typeName','name']
        version false

        typeName column: 'TH001'
        name column: 'TH002'
        supplierName column: 'TH005'
        
    }      
}
