package foodpaint

class OutSrcPurchaseSheetDet{
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
    static belongsTo=[outSrcPurchaseSheet:OutSrcPurchaseSheet]
    /*
    * 品項編號
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
    * 進貨數量
    */
	long qty


    /*
    * 批號
    */
	Batch batch


    /*
    * 製令
    */
    ManufactureOrder manufactureOrder


    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        sequence(unique:['name','typeName'])
        site nullable:true
        editor nullable:true
        creator nullable:true
        batch nullable:true
    }
}
