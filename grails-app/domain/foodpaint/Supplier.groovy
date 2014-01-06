package foodpaint


public enum Country {
    TAIWAN,
    JAPAN,
    CHINA
}


class Supplier {
    Integer importFlag = -1

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
    /*
    * 編號
    */
	String name


    /*
    * 名稱
    */
	String title = ""


    /*
    * 供應商所屬國家
    */
	Country country=Country.TAIWAN


    static mapping = {
        importFlag  defaultValue: -1
    }
    static constraints = {
        site nullable:true
        editor nullable:true
        creator nullable:true
    	name unique: true, blank: false
    }
}
