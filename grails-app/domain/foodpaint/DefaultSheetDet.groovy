package foodpaint

class DefaultSheetDet extends DefaultTable{

	static belongsTo=[head:DefaultSheet]
    /*
    * 訂單項次，取訂單編號最大單身項次 +1
    */
	Integer sequence

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {

    	sequence unique:'head'
    }
}
