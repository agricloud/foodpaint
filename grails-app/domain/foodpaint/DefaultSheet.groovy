package foodpaint

class DefaultSheet extends DefaultTable{

    /*
    * 單別
    */
	String typeName=""


    /*
    * 單號
    */
    String name=""

    Integer version

    static mapping = {
        tablePerHierarchy false
    }

    static constraints = {

    	name unique:'typeName'
    }
}
