package foodpaint

class DefaultSheetDet extends DefaultTable{
    /*
    * 單別
    */
    String typeName=""


    /*
    * 單號
    */
    String name=""
    /*
    * 訂單項次，取訂單編號最大單身項次 +1
    */
	Integer sequence

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {
    	sequence(unique:['name','typeName'])
    }
}
