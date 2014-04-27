package foodpaint


class Inovice {
    int importFlag = -1

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

    /**
     * 起始日期
     */
    Date  startDay

    /**
     * 截止日期
     */
    Date  endDay

    /**
     * 發票聯數
     */
    String invoicenumber

    /**
     * 使用序號碼
     */
    int sequence
    

    /**
     * 起始編號
     */
    String startingnumber
    
    /**
     * 截止編號
     */
    String endingnumber
    

    /**
     * 已使用編號
     */
    String usednumber

    

    /**
     * 關聯tax
     */
    Tax tax


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
