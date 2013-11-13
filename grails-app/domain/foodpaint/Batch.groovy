package foodpaint

 public enum BatchType {
    PRODUCT
}
class Batch  {
	Integer importFlag = -1

    /**
     * 廠別
     */
	Site site

    /**
     * 修改者
     */
	String editor = ""

	/**
	 * 建立者
	 */
	String creator = ""

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

	String name
	Long expectQty = 0//ERP無此資料
	Date dueDate //與過期日期的區別？


    /*
    * 製造完成日期
    */
	Date manufactureDate


    /*
    * 過期日期
    */
	Date expirationDate


    /*
    * 類型，無 erp 時使用
    */
    BatchType batchType = foodpaint.BatchType.PRODUCT


    /*
    * 供應商，無 erp 時使用
    */
   	Supplier supplier

   	/*
    * 供應商所屬國家
    */
	String country = foodpaint.Country.TAIWAN

    static mapping = {
        importFlag  defaultValue: -1
    }

	static constraints = {
		site nullable:true
		name 				unique: true, blank: false

		dueDate 			nullable: true
		expectQty 			min: 0L
		

		manufactureDate 	nullable: true
		expirationDate 		nullable: true
		supplier 			nullable: true
	}
	
}
