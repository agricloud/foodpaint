// package foodpaint
// import grails.converters.*


// class AccountancyPeriodDet {
//     int importFlag = -1

//     /**
//      * 年度
//      */
//    // Year  year 
    
//     /**
//      * 會計期間
//      */
//     int period

//     /**
//      * 起始日期
//      */
//     Date  startDay

//     /**
//      * 截止日期
//      */
//     Date  endDay

//     /**
//      * 建立日期（自動欄位）
//      */
//     Date dateCreated

//     /**
//      * 修改日期（自動欄位）
//      */
//     Date lastUpdated

//      /**
//      * 序列
//      */
//     int sequence
//     static belongsTo=[accountingPeriods :AccountingPeriod ]
//     /*
//     /**
//      *備註
//      */
//      String remark

//     static mapping = {
//         importFlag  defaultValue: -1
//     }
 
//     static constraints = {
//         sequence unique: true
//       //  year  nullable:false,
//         period nullable:false
//         startDay unique: true, blank: false
//         endDay unique: true, blank: false
//         remark unique: true, blank: false
// 	}
	
// }
