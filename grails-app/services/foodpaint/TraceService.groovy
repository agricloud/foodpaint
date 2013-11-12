package foodpaint


class TraceService{

    //查詢批號之來源單據
	def querySourceSheetByBatch(String batchName){
        //def productBatch=Batch.findByName(params.batch.id)
        log.debug "TraceService--querySourceSheetByBatch"
        def batch=Batch.findByName(batchName)
        if(batch){
            def sourceSheet=StockInSheetDet.findAllByBatch(batch)

            if(!sourceSheet){
                
                sourceSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)

                if(!sourceSheet){

                    sourceSheet=PurchaseSheetDet.findAllByBatch(batch)

                    if(!sourceSheet){

                        sourceSheet=ManufactureOrder.findAllByBatch(batch)

                        if(!sourceSheet){
                            log.debug "批號：${batch.name} 查無任何單據"

                            return null  
                        }
                        else log.debug "批號：${batch.name} 存於製令"
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
        else log.error "查無此批號：${batchName}"
        
    }

}