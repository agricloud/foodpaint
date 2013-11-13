package foodpaint
import grails.converters.*
import foodpaint.view.*

class ApiController {

    def traceService
    def messageSource

    /**
     * PING
     */
    def ping() {
        render(contentType: "application/json") {
            result = true
            version = "1.0" // FIX ME, change to config value
        }
    }

    /*
     * 實作履歷查詢，回傳 json 資料
     */
    def queryBatchReport() {

        def reportInfo = [:]
        reportInfo.put("item",Item.list())
        reportInfo.put("workstation",Workstation.list())
        reportInfo.put("operation",Operation.list())
        reportInfo.put("supplier",Supplier.list())
        reportInfo.put("customer",Customer.list())
        reportInfo.put("batch",Batch.list())
        reportInfo.put("batchRoute",BatchRoute.list())
        reportInfo.put("batchSource",BatchSource.list())

        JSON.use('deep')
        def converter = reportInfo as JSON
        def jsParse = JSON.parse(converter.toString())

        jsParse.item.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.workstation.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.operation.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.supplier.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.customer.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
        }
        jsParse.batch.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
            if(!it.supplier){
                it.supplier=[:]
                it.supplier.put("id","null")
                it.supplier.put("name","null")
            }
        }
        jsParse.batchSource.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
            if(!it.lastUpdated)
                it.lastUpdated=new Date()
            if(!it.dateCreated)
                it.dateCreated=new Date()
        }
        jsParse.batchRoute.each{
            if(!it.site){
                it.site=[:]
                it.site.put("id","null")
                it.site.put("name","null")
            }
            if(!it.lastUpdated)
                it.lastUpdated=new Date()
            if(!it.dateCreated)
                it.dateCreated=new Date()
            if(!it.supplier){
                it.supplier=[:]
                it.supplier.put("id","null")
                it.supplier.put("name","null")
            }
            if(!it.workstation){
                it.workstation=[:]
                it.workstation.put("id","null")
                it.workstation.put("name","null")
            }
        }
        println "feed data to Foodprint finished!!"
        converter = jsParse as JSON
        converter.render(response)

        // render (contentType: 'text/json') {
        //     reportInfo
        // }
        // JSON.use('deep')
        // def converter=reportInfo as JSON
        // converter.render(response)

    }


    def querySourceSheetByBatch(String batchName){
     
        render (contentType: 'text/json') {
            [sheet:traceService.querySourceSheetByBatch(batchName).sourceSheet]
        }
    }

}
