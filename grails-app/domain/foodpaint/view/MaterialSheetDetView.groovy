package foodpaint.view



    /*
    * 領料單身
    */
class MaterialSheetDetView implements Serializable{

    Integer importFlag

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
        datasource 'erp'
        table 'MOCTE'
        version false

        id composite: ['typeName', 'name', 'sequence']
        importFlag column: 'FLAG', sqlType:"numeric"
        typeName column: 'TE001', sqlType: "nchar"
        name column: 'TE002', sqlType: "nchar"
        sequence column: 'TE003', sqlType: "nchar"
        itemName  column: 'TE004', sqlType: "nchar"
        batchName column: 'TE010', sqlType: "nchar"
        manufactureOrderTypeName column: 'TE011', sqlType: "nchar"
        manufactureOrderName column: 'TE012', sqlType: "nchar"
    }
}
