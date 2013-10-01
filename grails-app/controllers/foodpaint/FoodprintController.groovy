package foodpaint
import grails.converters.JSON

class FoodprintController {


    /*
    * 實作履歷查詢，回傳 json 資料
    */
    def queryBatchReport() {
        
        ReverseTraceController rtrc = new ReverseTraceController()
        def productSheet = rtrc.querySheetByBatch()

        // render (contentType: 'text/json') {
        //     productSheet
        // }

        JSON.use('deep')
        def converter=productSheet as JSON
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
        // def data=[ DS:DefaultSheet.list(params), dsCount:DefaultSheet.count(), DSD:DefaultSheetDet.list(params), dsdCount:DefaultSheetDet.count()]
        // def data=[PS:PurchaseSheet.list(params),count:PurchaseSheet.count()]

        def data=[ CO:CustomerOrder.list(params),coCount:CustomerOrder.count(),COD:CustomerOrderDet.list(params),codCount:CustomerOrderDet.count()]
        // def data=[ MS:MaterialSheet.list(params),msCount:MaterialSheet.count()]
        // def data=[ MSD:MaterialSheetDet.list(params), msdCount:MaterialSheetDet.count() ]
        //def data=[ SSD:StockInSheetDet.list(params),ssdCount:StockInSheetDet.count()]
        //def data=[MO:ManufactureOrder.list(params)]

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
            
        //     it.details.each{
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

        // render (contentType: 'text/json') {
        //     data
        // }
        JSON.use('deep')
        def converter=data as JSON
        converter.render(response)

    }

    /*
    * 根據批號，品項查詢追溯資料
    */

    def queryBatchTrace() {

    }
}
