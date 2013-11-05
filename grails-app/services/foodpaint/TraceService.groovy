package foodpaint

class TraceService{

	def querySourceSheetByBatch(String batchName){
        //def productBatch=Batch.findByName(params.batch.id)
        log.debug "TraceService--querySourceSheetByBatch"
        def batch=Batch.findByName(batchName)
        def sourceSheet=StockInSheetDet.findAllByBatch(batch)

        if(sourceSheet==null || sourceSheet==[]){
            
            sourceSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)

            if(sourceSheet==null || sourceSheet==[]){

                sourceSheet=PurchaseSheetDet.findAllByBatch(batch)

                if(sourceSheet==null || sourceSheet==[]){
                    log.debug "批號：${batch.name} 查無任何單據"

                    return null   
                }
                else{
                    log.debug "批號：${batch.name} 存於進貨單"
                }
            }
            else{
                log.debug "批號：${batch.name} 存於託外進貨單"
            }
        }
        else{
            log.debug "批號：${batch.name} 存於入庫單"
        }

        [sourceSheet:sourceSheet,batch:batch]
    }

}