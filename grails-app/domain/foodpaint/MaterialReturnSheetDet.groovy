package foodpaint
/*
 * 領退單身
 */
class MaterialReturnSheetDet{
    int importFlag = -1

    /**
     * 廠別
     */
    Site site

    /**
     * 修改者
     */
    String editor

    /**
     * 建立者
     */
    String creator

    /**
     * 建立日期（自動欄位）
     */
    Date dateCreated

    /**
     * 修改日期（自動欄位）
     */
    Date lastUpdated
    /*
    * 單別
    */
    String typeName


    /*
    * 單號
    */
    String name
    /*
    * 訂單項次，取訂單編號最大單身項次 +1
    */
    int sequence
    static belongsTo=[materialReturnSheet:MaterialReturnSheet]
    
    /*
    * 製令
    */
    static hasOne = [
        manufactureOrder: ManufactureOrder
    ]

    MateriaSheetDet materiaSheetDet

    /*
    * 批號
    */
    Batch batch
    /*
    * 品項編號，材料編號
    */
    Item item

    /*
    * 庫別
    */

    Warehouse warehouse

    /*
    * 儲位
    */
    StorageLocation storageLocation
    /*
    * 退料數量
    */
    long qty = 0

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        sequence(unique:['name','typeName'])
        site nullable:true
        editor nullable:true
        creator nullable:true
        batch nullable:true
        qty min: 0L
    }
    public String toString(){
        "領退單單身：${typeName}-${name}-${sequence}"
    }
}
