package foodpaint

    /*
    * 領退料單頭
    */
class MaterialSheet extends DefaultSheet{

	static hasMany=[materialSheetDet:MaterialSheetDet]
    /*
    * 生產線別，加工廠商
    */
    Workstation workstation


    static constraints = {
        materialSheetDet nullable:true
        
    }
}
