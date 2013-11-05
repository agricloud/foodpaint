package foodpaint

 public enum BatchType {
    PRODUCT
}
class Batch extends DefaultTable {

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



	static constraints = {
		name 				unique: true, blank: false

		dueDate 			nullable: true
		expectQty 			min: 0L
		

		manufactureDate 	nullable: true
		expirationDate 		nullable: true
		supplier 			nullable: true
	}
	
}
