package foodpaint.view

    /*
    * 進貨單
    */
class PurchaseSheetView implements Serializable{


    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    /*
    * 供應商
    */
    String supplierName


    /*
    * 進貨日期
    */
    String incomingDate
    

    /*
    * 單據日期
    */
    String orderDate


    
    static constraints = {

    }
    

    static mapping = {
        // datasource 'erp'
        table 'PURTG'

        id composite: ['typeName','name']
        version false

        typeName column: 'TG001'
        name column: 'TG002'
        incomingDate column: 'TG003'
        supplierName column: 'TG005'
        orderDate column: 'TG014'
        
    } 
}
