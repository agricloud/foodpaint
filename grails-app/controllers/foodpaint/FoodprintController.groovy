package foodpaint
import grails.converters.JSON

class FoodprintController {


    /*
    * 實作履歷查詢，回傳 json 資料
    */
    def queryBatchReport() {
        
        params.name="0927-410002"

        TraceController trc = new TraceController()

        def reportInfo = [batchSources:[]]
        def batchNames=[params.name]
        //查批號來源單據
        while(batchNames.size() >0){
            println batchNames
            println "正在查詢批號${batchNames.get(0)}共有${batchNames.size()}個"

            def batchSheet = trc.querySourceSheetByBatch(batchNames.get(0))
            batchNames.remove(0)
            println "剩餘${batchNames.size()}個批號"

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
                        //此批號為進貨單購入
                    }
                    else{
                        println "查無單據類型"
                    }
                }
               
            }//end each
            println "${batchNames}更新後尚有${batchNames.size()}個批號"
        }//end while

        reportInfo.put("item",Item.list())
        reportInfo.put("batch",Batch.list())

        // render (contentType: 'text/json') {
        //     reportInfo
        // }
        JSON.use('deep')
        def converter=reportInfo as JSON
        converter.render(response)

    }

    
    def test(){
        println "test124"
        Integer max
        params.max = Math.min(max ?: 10, 100)

        println "CustomerSheet=${CustomerOrder.count()}"
        println "CustomerSheetDet=${CustomerOrderDet.count()}"
        println "PurchaseSheet=${PurchaseSheet.count()}"
        println "PurchaseSheetDet=${PurchaseSheetDet.count()}"
        println "OutSrcPurchaseSheet=${OutSrcPurchaseSheet.count()}"
        // def data=[ DS:DefaultSheet.list(params), dsCount:DefaultSheet.count()]//, DSD:DefaultSheetDet.list(params), dsdCount:DefaultSheetDet.count()]
        // def data=[PS:PurchaseSheet.list(params),count:PurchaseSheet.count()]

        // def data=[ CO:CustomerOrder.list(params),coCount:CustomerOrder.count(),COD:CustomerOrderDet.list(params),codCount:CustomerOrderDet.count()]
        // def data=[ MS:MaterialSheet.list(params),msCount:MaterialSheet.count()]
        // def data=[ MSD:MaterialSheetDet.list(params), msdCount:MaterialSheetDet.count() ]
        def data=[ SSD:StockInSheetDet.list(params),ssdCount:StockInSheetDet.count()]
        // def data=[MO:ManufactureOrder.list(params)]

//         def data=[]
//         ManufactureOrder.list(params).each{
//             println "each--"+it
//             def ar=[]
//             ar<< it.materialSheetDet
//             it.materialSheetDet=ar
// println "bef==="+it.materialSheetDet
//             it.materialSheetDet.each{
//                 println it
//                     ar<< it
//             }
//             it.materialSheetDet=[ar]

//             println "aft==="+it.materialSheetDet

//             def br=[]
//             // br<< it.stockInSheetDet
//             // it.stockInSheetDet=br
//             it.stockInSheetDet.each{
//                 br<< it
//             }
//             it.stockInSheetDet=[br]
//             data<< it
//             println data
//         }

        // def data=[]
        // MaterialSheet.list(params).each{
            
        //     it.materialSheetDets.each{
        //         def ar=[]
        //         println it
        //         println "before:"+it.manufactureOrder.materialSheetDet
        //         it.manufactureOrder.materialSheetDet.each{
        //             println "=="+it
        //             ar<< it
        //         }
        //         it.manufactureOrder.materialSheetDet=[ar]
        //         println "after:"+it.manufactureOrder.materialSheetDet

        //         def br=[]
        //         it.manufactureOrder.stockInSheetDet.each{
        //             println "=="+it
        //             br<< it
        //         }
        //         it.manufactureOrder.stockInSheetDet=[br]
        //     }
        //     data<< it
        // }

        render (contentType: 'text/json') {
            data
        }
        // JSON.use('deep')
        // def converter=data as JSON
        // converter.render(response)

    }

    /*
    * 根據批號，品項查詢追溯資料
    */

    def queryBatchTrace() {

    }
}
