import grails.converters.*
import foodpaint.*
import foodpaint.view.*

class DataImportJob {
    def dataImportService
    def traceService
    static triggers = {
        simple name: 'mySimpleTrigger', startDelay: 1000, repeatInterval: 600000  
    }

    def group = "MyGroup"
    def execute(){

    	if(ItemView.count()>0){
			log.info "ItemView.count() = ${ItemView.count()}"
			dataImportService.doDataImport((ItemView.list() as XML).toString());
		}

		if(WorkstationView.count()>0){
		log.info "WorkstationView.count() = ${WorkstationView.count()}"
		dataImportService.doDataImport((WorkstationView.list() as XML).toString());
		}

		if(OperationView.count()>0){
			log.info "OperationView.count() = ${OperationView.count()}"
			dataImportService.doDataImport((OperationView.list() as XML).toString());
		}

		if(SupplierView.count()>0){
			log.info "SupplierView.count() = ${SupplierView.count()}"
			dataImportService.doDataImport((SupplierView.list() as XML).toString());
		}

		if(CustomerView.count()>0){
			log.info "CustomerView.count() = ${CustomerView.count()}"
			dataImportService.doDataImport((CustomerView.list() as XML).toString());
		}

		if(CustomerOrderView.count()>0){
			log.info "CustomerOrderView.count() = ${CustomerOrderView.count()}"
			dataImportService.doDataImport((CustomerOrderView.list() as XML).toString());
		}

		if(CustomerOrderDetView.count()>0){
			log.info "CustomerOrderDetView.count() = ${CustomerOrderDetView.count()}"
			dataImportService.doDataImport((CustomerOrderDetView.list() as XML).toString());
		}

		if(ManufactureOrderView.count()>0){	
			log.info "ManufactureOrderView.count() = ${ManufactureOrderView.count()}"
			dataImportService.doDataImport((ManufactureOrderView.list() as XML).toString());
		}

		if(PurchaseSheetView.count()>0){
			log.info "PurchaseSheetView.count() = ${PurchaseSheetView.count()}"
			dataImportService.doDataImport((PurchaseSheetView.list() as XML).toString());
		}

		if(PurchaseSheetDetView.count()>0){
			log.info "PurchaseSheetDetView.count() = ${PurchaseSheetDetView.count()}"	
			dataImportService.doDataImport((PurchaseSheetDetView.list() as XML).toString());
		}

		if(StockInSheetView.count()>0){
			log.info "StockInSheetView.count() = ${StockInSheetView.count()}"
			dataImportService.doDataImport((StockInSheetView.list() as XML).toString());
		}

		if(StockInSheetDetView.count()>0){
			log.info "StockInSheetDetView.count() = ${StockInSheetDetView.count()}"
			dataImportService.doDataImport((StockInSheetDetView.list() as XML).toString());
		}
		
		if(OutSrcPurchaseSheetView.count()>0){
			log.info "OutSrcPurchaseSheetView.count() = ${OutSrcPurchaseSheetView.count()}"
			dataImportService.doDataImport((OutSrcPurchaseSheetView.list() as XML).toString());
		}
		if(OutSrcPurchaseSheetDetView.count()>0){
			log.info "OutSrcPurchaseSheetDetView.count() = ${OutSrcPurchaseSheetDetView.count()}"	
			dataImportService.doDataImport((OutSrcPurchaseSheetDetView.list() as XML).toString());
		}

		if(ManufactureOrderRouteView.count()>0){	
			log.info "ManufactureOrderRouteView.count() = ${ManufactureOrderRouteView.count()}"
			dataImportService.doDataImport((ManufactureOrderRouteView.list() as XML).toString());
		}

		if(MaterialSheetView.count()>0){
			log.info "MaterialSheetView.count() = ${MaterialSheetView.count()}"
			dataImportService.doDataImport((MaterialSheetView.list() as XML).toString());
		}

		if(MaterialSheetDetView.count()>0){
			log.info "MaterialSheetDetView.count() = ${MaterialSheetDetView.list().size()}"				
			dataImportService.doDataImport((MaterialSheetDetView.list() as XML).toString());
		}

		log.info "BatchSource---generating!!"	
		traceService.generateBatchSourceInstance();
		
		ManufactureOrder.list().each{
			log.debug it.id+"/"+it.typeName+"-"+it.name
		}

		log.debug "超級分隔線噢喔噢！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！"

		MaterialSheetDet.list().each{
			log.debug it.manufactureOrder.id+"/"+it.manufactureOrder.typeName+"-"+it.manufactureOrder.name
		}
		println "import finish!!!!"

    }
}