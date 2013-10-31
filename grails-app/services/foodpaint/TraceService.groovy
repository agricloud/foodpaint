package foodpaint

class TraceService{

	def querySourceSheetByBatch(String batchName){
        //def productBatch=Batch.findByName(params.batch.id)
        log.debug "TraceService--querySourceSheetByBatch"
        def batch=Batch.findByName(batchName)
        def sourceSheet=StockInSheetDet.findAllByBatch(batch)

        if(sourceSheet==null || sourceSheet==[]){
            log.debug "批號：${batch.name} 查無入庫單"
            sourceSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)

            if(sourceSheet==null || sourceSheet==[]){
                log.debug "批號：${batch.name} 查無託外進貨單"

                sourceSheet=PurchaseSheetDet.findAllByBatch(batch)
                if(sourceSheet==null || sourceSheet==[]){
                    log.debug "批號：${batch.name} 查無進貨單"

                    return null            
                }
            }
        }

        [sourceSheet:sourceSheet,batch:batch]
    }

}