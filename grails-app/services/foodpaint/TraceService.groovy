package foodpaint

class TraceService{

    //追溯單據產生BatchSource關聯
    def generateBatchSourceInstance(){
        def reportInfo = [batchSource:[]]
        def batchNames=Batch.list()*.name

        //查批號來源單據
        while(batchNames.size() >0){
            log.debug "正在查詢批號${batchNames.get(0)}共有${batchNames.size()}個"

            def batchSheet = querySourceSheetByBatch(batchNames.get(0))
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
                        def batchSource = new BatchSource(batch:batchSheet.batch,childBatch:it.batch)

                        if (!batchSource.validate() || !batchSource.save(flush: true)){
                            batchSource.errors.allErrors.each{ 
                                log.error messageSource.getMessage(it, Locale.getDefault())
                            }
                        }

                        //log.debug it as JSON

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

    //查詢批號之來源單據
	def querySourceSheetByBatch(String batchName){
        //def productBatch=Batch.findByName(params.batch.id)
        log.debug "TraceService--querySourceSheetByBatch"
        def batch=Batch.findByName(batchName)
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

}