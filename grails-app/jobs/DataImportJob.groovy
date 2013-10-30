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
  //       log.info "ItemView.list().size() = ${ItemView.list().size()}"
  //       dataImportService.doDataImport((ItemView.list() as XML).toString());

		// log.info "WorkstationView.list().size() = ${WorkstationView.list().size()}"
		// dataImportService.doDataImport((WorkstationView.list() as XML).toString());

		// log.info "OperationView.list().size() = ${OperationView.list().size()}"
		// dataImportService.doDataImport((OperationView.list() as XML).toString());
		
		// log.info "SupplierView.list().size() = ${SupplierView.list().size()}"
		// dataImportService.doDataImport((SupplierView.list() as XML).toString());

		// log.info "CustomerView.list().size() = ${CustomerView.list().size()}"
		// dataImportService.doDataImport((CustomerView.list() as XML).toString());

		// log.info "CustomerOrderView.list().size() = ${CustomerOrderView.list().size()}"
		// dataImportService.doDataImport((CustomerOrderView.list() as XML).toString());

		// log.info "CustomerOrderDetView.list().size() = ${CustomerOrderDetView.list().size()}"
		// dataImportService.doDataImport((CustomerOrderDetView.list() as XML).toString());
		
		// log.info "ManufactureOrderView.list().size() = ${ManufactureOrderView.list().size()}"
		// dataImportService.doDataImport((ManufactureOrderView.list() as XML).toString());

		log.info "PurchaseSheetView.list().size() = ${PurchaseSheetView.list().size()}"
		dataImportService.doDataImport((PurchaseSheetView.list() as XML).toString());
		log.info "PurchaseSheetDetView.list().size() = ${PurchaseSheetDetView.list().size()}"	
		dataImportService.doDataImport((PurchaseSheetDetView.list() as XML).toString());

		log.info "StockInSheetView.list().size() = ${StockInSheetView.list().size()}"
		dataImportService.doDataImport((StockInSheetView.list() as XML).toString());
		log.info "StockInSheetDetView.list().size() = ${StockInSheetDetView.list().size()}"
		dataImportService.doDataImport((StockInSheetDetView.list() as XML).toString());

		log.info "OutSrcPurchaseSheetView.list().size() = ${OutSrcPurchaseSheetView.list().size()}"
		dataImportService.doDataImport((OutSrcPurchaseSheetView.list() as XML).toString());
		log.info "OutSrcPurchaseSheetDetView.list().size() = ${OutSrcPurchaseSheetDetView.list().size()}"	
		dataImportService.doDataImport((OutSrcPurchaseSheetDetView.list() as XML).toString());

		log.info "MaterialSheetView.list().size() = ${MaterialSheetView.list().size()}"
		dataImportService.doDataImport((MaterialSheetView.list() as XML).toString());
		log.info "MaterialSheetDetView.list().size() = ${MaterialSheetDetView.list().size()}"				
		dataImportService.doDataImport((MaterialSheetDetView.list() as XML).toString());
	


    }
}