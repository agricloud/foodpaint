package foodpaint

    /*
    * 領退料單頭
    */
class MaterialSheet extends DefaultSheet{

	static hasMany=[materialSheetDets:MaterialSheetDet]
    /*
    * 生產線別，加工廠商
    */
    Workstation workstation


    static constraints = {
        materialSheetDets nullable:true
        
    }
}
