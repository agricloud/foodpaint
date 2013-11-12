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

        //產生batchSource
        generateBatchSourceInstance()

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

    //追溯單據產生BatchSource關聯
    def generateBatchSourceInstance(){
        println "BatchSource---generating!!"   

        def batchNames=Batch.list()*.name

        //查批號來源單據
        while(batchNames.size() >0){
            log.debug "正在查詢批號${batchNames.get(0)}共有${batchNames.size()}個"

            def batchSheet = traceService.querySourceSheetByBatch(batchNames.get(0))
            batchNames.remove(0)
            log.debug "剩餘${batchNames.size()}個批號"

            batchSheet.sourceSheet.each{
                //以來源單據查領料單
                if(it.instanceOf(StockInSheetDet) || it.instanceOf(OutSrcPurchaseSheetDet) || it.instanceOf(ManufactureOrder)){
                    def mo
                    if(it.instanceOf(ManufactureOrder))
                        mo=it
                    else
                        mo=it.manufactureOrder

                    mo.materialSheetDet.each{
                        def batchSource =  BatchSource.findByBatchAndChildBatch(batchSheet.batch,it.batch)
                        
                        if(!batchSource){
                            batchSource = new BatchSource(batch:batchSheet.batch, childBatch:it.batch,importFlag:1)
                        }

                        if (!batchSource.validate() || !batchSource.save(flush: true)){
                            batchSource.errors.allErrors.each{ 
                                log.error messageSource.getMessage(it, Locale.getDefault())
                            }
                        }
                        log.debug batchSource as JSON

                        batchNames<< it.batch.name
                    }
                }
                else{
                    if(it.instanceOf(PurchaseSheetDet)){
                        it.batch.supplier = it.purchaseSheet.supplier
                        //此批號為進貨單購入
                    }
                    else{
                        log.debug "查無單據類型"
                    }
                }
               
            }//end each
            log.debug "${batchNames}更新後尚有${batchNames.size()}個批號"
        }//end while
    }
}
