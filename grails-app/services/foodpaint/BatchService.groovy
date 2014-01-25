package foodpaint

class BatchService {

	def messageSource
	def domainService

	def findOrCreateBatchInstanceByXml(recordXml, sheet){
		//如果批號欄位沒有資料則不新增batch
		//但單據仍會儲存
		if(recordXml.batchName.text() || recordXml.batchName.text().trim()){
			def batch = Batch.findByName(recordXml.batchName.text())

			if(!batch){
				batch=new Batch(name:recordXml.batchName.text())
			}

			batch.importFlag = batch.importFlag++
			batch.item = sheet.item

			if(sheet.instanceOf(PurchaseSheetDet)){
				batch.supplier = sheet.purchaseSheet.supplier
			}
			if(sheet.instanceOf(StockInSheetDet)){
				batch.manufactureDate = sheet.stockInSheet.stockInDate
			}
			if(sheet.instanceOf(OutSrcPurchaseSheetDet)){
				batch.supplier = sheet.outSrcPurchaseSheet.supplier
				batch.manufactureDate = sheet.outSrcPurchaseSheet.outSrcPurchaseDate
			}
			if(sheet.instanceOf(ManufactureOrder)){
				batch.expectQty = sheet.qty
			}

			if (!batch.validate() || !batch.save(flush: true)){
				batch.errors.allErrors.each{ 
					log.error messageSource.getMessage(it, Locale.getDefault())
				}

			}
			else{
				return batch
			}
		}
		else{
			return null
		}
	}
	def findOrCreateBatchInstanceByJson(params, sheet){
		//如果批號欄位沒有資料則不新增batch
		def msg
		def isSuccess
		Object[] args=[]
		def batch

		if(params["batch.name"]){
			batch = Batch.findByName(params["batch.name"])

			if(!batch){
				return createBatchInstanceByJson(params, sheet)
			}
			else{

				if(sheet.instanceOf(PurchaseSheetDet)){
					batch.supplier = sheet.purchaseSheet.supplier
					isSuccess = true
				}
				if(sheet.instanceOf(StockInSheetDet)){
					if(sheet.manufactureOrder.item != batch.item){
						isSuccess = false
						args=[sheet.manufactureOrder]
						msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
					}
					else{
						batch.manufactureDate = sheet.stockInSheet.stockInDate
						isSuccess = true
					}
				}
				if(sheet.instanceOf(OutSrcPurchaseSheetDet)){
					if(sheet.manufactureOrder.item != batch.item){
						isSuccess = false
						args=[sheet.manufactureOrder]
						msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
					}
					else{
						batch.supplier = sheet.outSrcPurchaseSheet.supplier
						batch.manufactureDate = sheet.outSrcPurchaseSheet.outSrcPurchaseDate
						isSuccess = true
					}
				}
				if(sheet.instanceOf(ManufactureOrder)){
					batch.expectQty = sheet.qty
					isSuccess = true
				}
			}

		}
		else{//未指定欲新增之批號
			isSuccess = false
			msg = messageSource.getMessage("batch.name.params.notfound", args, Locale.getDefault())  
		}

		return [success:isSuccess, batch:batch, message: msg]
	}

	def createBatchInstanceByJson(params, sheet){
		def msg
		Object[] args=[]
		//新增批號 若批號已存在則不允許新增
		if(params["batch.name"]){

			def batch = new Batch()
		
			batch.name = params["batch.name"]
			batch.item = sheet.item

			if(sheet.instanceOf(PurchaseSheetDet)){
				batch.supplier = sheet.purchaseSheet.supplier
			}
			if(sheet.instanceOf(StockInSheetDet)){
				batch.manufactureDate = sheet.stockInSheet.stockInDate
			}
			if(sheet.instanceOf(OutSrcPurchaseSheetDet)){
				batch.supplier = sheet.outSrcPurchaseSheet.supplier
				batch.manufactureDate = sheet.outSrcPurchaseSheet.outSrcPurchaseDate
			}
			if(sheet.instanceOf(ManufactureOrder)){
				batch.expectQty = sheet.qty
			}

			def result=domainService.save(batch)

			if(result.success){
				result.batch=batch
			}
			return result
		}
		else{//未指定欲新增之批號
			msg = messageSource.getMessage("batch.name.params.notfound", args, Locale.getDefault())
            return [success:false, message: msg]
		}
	}

}
