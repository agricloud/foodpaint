// package foodpaint
// import grails.converters.*


// class AccountancyPeriod {
//     int importFlag = -1


//     /**
//      * 年度
//      */
//     Year  year 
    
//     /**
//      * 會計期間
//      */
//     int period

//      /**
//      * 年度起始日期
//      */
//     Date startDayofYear

//     /**
//      * 建立日期（自動欄位）
//      */
//     Date dateCreated

//     /**
//      * 修改日期（自動欄位）
//      */
//     Date lastUpdated


//     /**
//      *備註
//      */
//     String remark

//     static mapping = {
//         importFlag  defaultValue: -1
//     }
 
//     static constraints = {
//         sequence unique: true,
//         year  nullable:false,
//         period nullable:false,
//         startDay unique: true, blank: false
//         remark unique: true, blank: false
// 	}
	
// }
