package foodpaint

class Warehouse {

    def grailsApplication
    def messageSource

    String importFlag = -1
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
    double capacity = Double.MAX_VALUE
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
        capacity min: 0.0d
        capacityUnit nullable: true
        remark nullable: true
        
    }

    public String toString(){
        def i18nType = grailsApplication.config.grails.i18nType
        Object[] args = [Warehouse]
        """
        ${messageSource.getMessage("${i18nType}.warehouse.label", args, Locale.getDefault())}: ${warehouse.name}/${warehouse.title}
        """
    }
}
