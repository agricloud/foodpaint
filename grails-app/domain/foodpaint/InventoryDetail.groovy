package foodpaint

class InventoryDetail {

    def grailsApplication
    def messageSource

    String importFlag = -1
    Site site
    String editor
    String creator
    Date dateCreated
    Date lastUpdated

    static belongsTo=[
        warehouse: Warehouse,
        item: Item,
        warehouseLocation: WarehouseLocation,
        batch: Batch
    ]

    double qty = 0.0d

    /**
     *  最近入庫日期
     */
    Date lastInDate = new Date()

    /**
     *  最近出庫日期
     */
    Date lastOutDate = new Date()

    static mapping = {
        importFlag  defaultValue: -1
    }
	static constraints = {
        importFlag nullable:true
        batch(unique:['warehouse','warehouseLocation','item','site'])
        site nullable:true
        editor nullable:true
		creator nullable:true
	}

    def getGrailsApplication(){
        return grailsApplication
    }

    def getMessageSource(){
        return messageSource
    }

	public String toString(){
        def i18nType = getGrailsApplication().config.grails.i18nType
        Object[] args = [InventoryDetail]
        """
        ${getMessageSource().getMessage("${i18nType}.inventoryDetail.label", args, Locale.getDefault())}: 
        ${warehouse.name}/${warehouse.title}/${warehouseLocation.name}/${warehouseLocation.title}/${item.name}/${item.title}/${batch.name}
        """
    }
}
