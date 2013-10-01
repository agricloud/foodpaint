package foodpaint

class ReverseTraceController {

    //逆溯 找出第一階資訊
    def querySheetByBatch(){
        //def productBatch=Batch.findByName(params.batch.id)
        def batch=Batch.findByName("0927-410002")
        def batchSheet=StockInSheetDet.findAllByBatch(batch)

        if(batchSheet==null || batchSheet==[]){
            println "批號：${batch.name} 查無入庫單"
            batchSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)

            if(batchSheet==null || batchSheet==[]){
                println "批號：${batch.name} 查無託外進貨單"

                batchSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)
            }

        }

        [batchSheet:batchSheet]
    }

}
