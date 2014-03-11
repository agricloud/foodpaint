package foodpaint.view

    /*
    * 領退料單頭
    */
class MaterialSheetView implements Serializable{

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
    * 生產線別
    */
    String workstationName
    
    /*
    * 加工廠商
    */
    String supplierName


    static constraints = {
        
    }

    static mapping = {
        datasource 'erp'
        table 'MOCTC'
        version false

        id composite: ['typeName','name']
        importFlag column: 'FLAG', sqlType:"numeric"
        typeName column: 'TC001', sqlType: "nchar"
        name column: 'TC002', sqlType: "nchar"
        workstationName column: 'TC005', sqlType: "nchar"
        supplierName column: 'TC006', sqlType: "nchar"
        
    } 
}
