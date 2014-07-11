package foodpaint

public enum BatchType {
    PRODUCT
}
class Batch  {
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

	static belongsTo = [
		item: Item,
		
	]

	static hasMany = [
		batchRoutes: BatchRoute,
		batchSources: BatchSource,
        inventoryDetails: InventoryDetail
	]

	String name
	double expectQty = 0.0d//ERP無此資料
	Date dueDate //失效日

    /**
     * 製造完成日期
     */
	Date manufactureDate

    /**
     * 類型，無 erp 時使用
     */
    BatchType batchType = foodpaint.BatchType.PRODUCT


    /**
     * 供應商，無 erp 時使用
     */
   	Supplier supplier

   	/**
     * 供應商所屬國家
     */
	Country country = foodpaint.Country.TAIWAN

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
		expectQty min: 0.0d
		dueDate nullable: true
		manufactureDate nullable: true
		supplier nullable: true
		
	}
	public String toString(){
    	"批號：${name}"
    }
	
}
