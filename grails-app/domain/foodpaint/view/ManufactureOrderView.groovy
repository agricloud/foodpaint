package foodpaint.view


    /*
    * 製造命令
    */
class ManufactureOrderView implements Serializable{

    /*
    * 訂單單身
    */
    //目前尚未使用，暫時不建
    //CustomerOrderDet customerOrderDet

    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name

    /*
    * 品項編號
    */
    String itemName

    /*
    * 生產量
    */
    //目前使用ERP預計產量欄位
    Integer qty


    static constraints = {
        //customerOrderDet nullable:true
    }

    static mapping = {
        datasource 'erp'
        table 'MOCTA'

        id composite: ['typeName','name']
        version false

        typeName column: 'TA001', sqlType: "nchar"
        name column: 'TA002', sqlType: "nchar"
        itemName  column: 'TA006', sqlType: "nchar"
        qty column: 'TA015', sqlType: "numeric"
        
    } 
}