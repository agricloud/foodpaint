package foodpaint.view

class OutSrcPurchaseSheetDetView implements Serializable{

    Integer flag

    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    Integer sequence

    /*
    * 品項編號
    */
	String itemName


    /*
    * 數量
    */
    //目前對應ERP單據中的驗收數量
	Integer qty


    /*
    * 批號
    */
	String batchName


    /*
    * 製令
    */
    String manufactureOrderTypeName
    String manufactureOrderName



    static constraints = {

    }

    static mapping = {
        datasource 'erp'
        table 'MOCTI'
        version false
        flag column: 'FLAG', sqlType:"numeric"

        id composite: ['typeName', 'name', 'sequence']
        typeName column: 'TI001', sqlType: "nchar"
        name column: 'TI002', sqlType: "nchar"
        sequence column: 'TI003', sqlType: "nchar"
        itemName  column: 'TI004', sqlType: "nchar"
        batchName column: 'TI010', sqlType: "nchar"
        qty column: 'TI019', sqlType: "numeric"
        manufactureOrderTypeName column: 'TI013', sqlType: "nchar"
        manufactureOrderName column: 'TI014', sqlType: "nchar"
    } 
}
