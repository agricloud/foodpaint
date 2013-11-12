package foodpaint


    /*
    * 記錄生產的 batch 是由哪些元物料的 batch 所組成
    */
class BatchSource extends DefaultTable{

	static belongsTo = [batch: Batch]
	Batch childBatch

    static constraints = {
    	childBatch unique: 'batch' 
    }
}