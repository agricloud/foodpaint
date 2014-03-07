package foodpaint

import grails.converters.*

class TraceService{


    //追溯單據產生BatchSource關聯
    def generateBatchSourceInstance(){   

        //未來若要針對一張單據處理批號關係，直接將批號加入batchNames=[]即可。
        def batchNames=Batch.list()*.name

        //查批號來源單據
        while(batchNames.size() >0){
            log.debug "正在查詢批號${batchNames.get(0)}共有${batchNames.size()}個"

            def batchSheet = querySourceSheetByBatch(batchNames.get(0))
            batchNames.remove(0)
            log.debug "剩餘${batchNames.size()}個批號"

            batchSheet?.sourceSheet.each{
                //以來源單據查領料單
                if(it.instanceOf(StockInSheetDet) || it.instanceOf(OutSrcPurchaseSheetDet) || it.instanceOf(ManufactureOrder)){
                    def mo
                    if(it.instanceOf(ManufactureOrder))
                        mo=it
                    else
                        mo=it.manufactureOrder

                    mo.materialSheetDets.each{
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

                        //若領料單無輸入批號會有錯誤，未來可考慮是否增加檢查。
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
        println "BatchSource---generate finished!"   
    }

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
                            println "批號：${batch.name} 查無任何單據"
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