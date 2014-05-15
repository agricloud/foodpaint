package foodpaint

class Warehouse {

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
    Long capacity
    /**
     *  容量單位
     */
    String capacityUnit

    String remark

    static constraints = {
        site nullable:true
        editor nullable:true
        creator nullable:true
        name unique: true, blank: false
        capacity nullable: true
        capacityUnit nullable: true
        remark nullable: true
        
    }

    public String toString(){
        "倉庫編號：${name}，倉庫名稱：${title}"
    }
}
