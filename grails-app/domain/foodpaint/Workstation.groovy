package foodpaint

class Workstation extends DefaultTable{

	String name
	String title

    static constraints = {
    	name unique:true
    }
}
