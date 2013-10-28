package foodpaint
import grails.converters.*
import foodpaint.view.*

class ApiController {

    def traceService

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
        log.info CustomerOrderView.list().get(0) as XML
        log.info CustomerOrderDetView.list().get(0) as XML
        //params.batch.name="0927-410002"

        def reportInfo = [batchSources:[]]
        def batchNames=[]

        if(params?.batch?.name)batchNames << params.batch.name
        else batchNames=Batch.list()*.name

        //查批號來源單據
        while(batchNames.size() >0){
            log.debug "正在查詢批號${batchNames.get(0)}共有${batchNames.size()}個"

            def batchSheet = traceService.querySourceSheetByBatch(batchNames.get(0))
            batchNames.remove(0)
            log.debug "剩餘${batchNames.size()}個批號"

            batchSheet.sourceSheet.each{
                //以來源單據查領料單
                if(it.instanceOf(StockInSheetDet) || it.instanceOf(OutSrcPurchaseSheetDet) ){
                    it.manufactureOrder.materialSheetDet.each{
                        def batchSource = [:]
                        batchSource.put("object","batchSource")
                        batchSource.put("batch",batchSheet.batch)
                        batchSource.put("childBatch",it.batch)
                        reportInfo.batchSources<< batchSource

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

        reportInfo.put("item",Item.list())
        reportInfo.put("batch",Batch.list())

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

        converter = jsParse as JSON
        converter.render(response)

        // render (contentType: 'text/json') {
        //     reportInfo
        // }
        // JSON.use('deep')
        // def converter=reportInfo as JSON
        // converter.render(response)

    }

    /*
     * 根據批號，品項查詢追溯資料
     */

    def queryBatchTrace() {

    }
}
