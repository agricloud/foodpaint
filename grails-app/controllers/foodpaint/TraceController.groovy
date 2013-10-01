package foodpaint

class TraceController {

    //查出批號之來源單據（逆溯 找出第一階資訊）
    def querySourceSheetByBatch(String batchName){
        //def productBatch=Batch.findByName(params.batch.id)
        log.debug "${controllerName}--${actionName}"
        def batch=Batch.findByName(batchName)
        def sourceSheet=StockInSheetDet.findAllByBatch(batch)

        if(sourceSheet==null || sourceSheet==[]){
            println "批號：${batch.name} 查無入庫單"
            sourceSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)

            if(sourceSheet==null || sourceSheet==[]){
                println "批號：${batch.name} 查無託外進貨單"

                sourceSheet=OutSrcPurchaseSheetDet.findAllByBatch(batch)
            }

        }

        [sourceSheet:sourceSheet,batch:batch]
    }

    def queryMaterialSheetByMO(ManufactureOrder mo){

    }

}
