package foodpaint

class BatchRoute extends DefaultTable{

	static belongsTo=[batch:Batch]
	Workstation workstation
    Supplier supplier
	Operation operation
	int sequence

    /*
    * 開始時間
    */
	Date startDate


    /*
    * 結束時間
    */
	Date endDate

	 

    static constraints = {
    	sequence unique:'batch'
    	startDate nullable:true
    	endDate nullable:true
        workstation nullable:true
        supplier nullable:true
    }
}
