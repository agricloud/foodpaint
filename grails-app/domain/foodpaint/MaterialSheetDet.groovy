package foodpaint



    /*
    * 領料單身
    */
class MaterialSheetDet extends DefaultSheetDet{

    static belongsTo=[materialSheet:MaterialSheet]
    
    /*
    * 製令
    */
    static hasOne = [
        manufactureOrder: ManufactureOrder
    ]

    /*
    * 批號
    */
    Batch batch
    /*
    * 品項編號，材料編號
    */
    Item item

    static constraints = {
        batch nullable:true
    }
}
