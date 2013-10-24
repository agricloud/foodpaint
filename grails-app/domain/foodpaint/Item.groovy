package foodpaint

/**
 * 履歷明細項目（這邊打類別說明，暫定請蹤影做修改）
 * 
 * @author smlsun@gmail.com, ...
 * @version 1.0
 */
class Item extends DefaultTable{


    /**
     * 項目名稱，必填欄位
     */
    String name

    /**
     * 項目標題
     */
	String title

	/**
	 * 項目描述說明文字
	 */
	String description


    /*
    * 規格敘述
    */
    // String spec=""


    /*
    * 單位
    */
    String unit

    /**
     * 記錄期限多少天數
     */
	// Long dueDays

	/**
	 * 有效起始日期
	 */
	// Date effectStartDate

	/**
	 * 有效結束日期
	 */
	// Date effectEndDate



    /**
     * 廠別可以不設定<br/>
     * 有效起始與結束日期可以不設定
     */
	static constraints = {
		name unique: true, nullable: false
        title nullable: true
        description nullable: true
        unit nullable: true
		// dueDays nullable: true
		// effectStartDate nullable: true
		// effectEndDate nullable: true
	}
	public String toString(){
    	"品項編號：${name},品項名稱：${title}"
    }
    static mapping = {

    }

}
