package foodpaint.view



    /*
    * 領料單身
    */
class MaterialSheetDetView implements Serializable{


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
    * 製令
    */
    String manufactureOrderTypeName
    String manufactureOrderName


    String batchName

    /*
    * 品項編號，材料編號
    */
    String itemName


    static constraints = {

    }

    static mapping = {
        // datasource 'erp'
        table 'MOCTE'

        id composite: ['typeName', 'name', 'sequence']
        version false

        typeName column: 'TE001'
        name column: 'TE002'
        sequence column: 'TE003'
        itemName  column: 'TE004'
        batchName column: 'TE010'
        manufactureOrderTypeName column: 'TE011'
        manufactureOrderName column: 'TE012'
    }
}
