package foodpaint

class Warehouse {

    int importFlag = -1
    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    String name
    String title
    static hasMany=[
        warehouseLocations: WarehouseLocation,
        inventorys: Inventory,
        inventoryDetails: InventoryDetail
    ]

    /**
     *  容量
     */
    double capacity
    /**
     *  容量單位
     */
    String capacityUnit

    String remark

    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        importFlag nullable:true
        site nullable:true
        editor nullable:true
        creator nullable:true
        name(unique:['site'])
        name blank: false
        capacity nullable: true
        capacityUnit nullable: true
        remark nullable: true
        
    }

    public String toString(){
        "倉庫編號：${name}，倉庫名稱：${title}"
    }
}
