package foodpaint


class TaxDet {
    int importFlag = -1
   
      /**
     *備註
     */
    String remark

    static mapping = {
        importFlag  defaultValue: -1
    }
 
    static constraints = {
      
	}
	
}
