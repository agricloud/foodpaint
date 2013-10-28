package foodpaint.view

class OutSrcPurchaseSheetDetView implements Serializable{


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

        id composite: ['typeName', 'name', 'sequence']
        version false

        typeName column: 'TI001'
        name column: 'TI002'
        sequence column: 'TI003'
        itemName  column: 'TI004'
        batchName column: 'TI010'
        manufactureOrderTypeName column: 'TI013'
        manufactureOrderName column: 'TI014'
    } 
}
