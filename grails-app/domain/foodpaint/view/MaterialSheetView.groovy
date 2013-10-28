package foodpaint.view

    /*
    * 領退料單頭
    */
class MaterialSheetView implements Serializable{



    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    /*
    * 生產線別，加工廠商
    */
    String workstationName


    static constraints = {
        
    }

    static mapping = {
        datasource 'erp'
        table 'MOCTC'

        id composite: ['typeName','name']
        version false

        typeName column: 'TC001', sqlType: "nchar"
        name column: 'TC002', sqlType: "nchar"
        workstationName column: 'TC005', sqlType: "nchar"
        
    } 
}
