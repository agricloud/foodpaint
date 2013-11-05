package foodpaint.view


    /*
    * 製令製程
    */
class ManufactureOrderRouteView implements Serializable{

    Integer importFlag

    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    /*
    *  加工順序
    */
    int sequence

    /*
    * 製程代號
    */
    String operationName

    /*
    * 製程性質 1.廠內 2.託外
    */
    String makerType

    /*
    * 工作站/廠商代號
    */
    String makerName

    /*
    * 實際開工日
    */

    String startDate


    /*
    * 實際完工日
    */
    String endDate


    static constraints = {

    }

    static mapping = {
        datasource 'erp'
        table 'SFCTA'
        version false

        id composite: ['typeName','name','sequence']
        importFlag column: 'FLAG', sqlType:"numeric"
        typeName column: 'TA001', sqlType: "nchar"
        name column: 'TA002', sqlType: "nchar"
        sequence  column: 'TA003', sqlType: "nchar"
        operationName column: 'TA004', sqlType: "nchar"
        makerType column: 'TA005', sqlType: "nchar"
        makerName column: 'TA006', sqlType: "nchar"
        startDate column: 'TA030', sqlType: "nchar"
        endDate column: 'TA031', sqlType: "nchar"
        
    } 
}
