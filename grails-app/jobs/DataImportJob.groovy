import grails.converters.*
import foodpaint.*
import foodpaint.view.*

class DataImportJob {
    def dataImportService
    static triggers = {
        simple name: 'mySimpleTrigger', startDelay: 1000, repeatInterval: 600000  
    }

    def group = "MyGroup"
    def execute(){
        log.info "ItemView.list().size() = ${ItemView.list().size()}"
        // dataImportService.doDataImport((ItemView.list() as XML).toString());

		log.info "WorkstationView.list().size() = ${WorkstationView.list().size()}"
		log.info "SupplierView.list().size() = ${SupplierView.list().size()}"
		log.info "OperationView.list().size() = ${OperationView.list().size()}"
		log.info "CustomerView.list().size() = ${CustomerView.list().size()}"

		log.info "CustomerOrderView.list().size() = ${CustomerOrderView.list().size()}"
		log.info "CustomerOrderDetView.list().size() = ${CustomerOrderDetView.list().size()}"
		
		log.info "ManufactureOrderView.list().size() = ${ManufactureOrderView.list().size()}"

		log.info "MaterialSheetView.list().size() = ${MaterialSheetView.list().size()}"
		log.info "MaterialSheetDetView.list().size() = ${MaterialSheetDetView.list().size()}"				

		log.info "OutSrcPurchaseSheetView.list().size() = ${OutSrcPurchaseSheetView.list().size()}"
		log.info "OutSrcPurchaseSheetDetView.list().size() = ${OutSrcPurchaseSheetDetView.list().size()}"	

		log.info "PurchaseSheetView.list().size() = ${PurchaseSheetView.list().size()}"
		log.info "PurchaseSheetDetView.list().size() = ${PurchaseSheetDetView.list().size()}"	


		log.info "StockInSheetView.list().size() = ${StockInSheetView.list().size()}"
		log.info "StockInSheetDetView.list().size() = ${StockInSheetDetView.list().size()}"	


    }
}