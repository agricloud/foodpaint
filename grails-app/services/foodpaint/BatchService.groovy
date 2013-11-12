package foodpaint

class BatchService {

	def messageSource

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
			if(sheet.instanceOf(OutSrcPurchaseSheetDet)){
				batch.supplier = sheet.outSrcPurchaseSheet.supplier
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
		//但單據仍會儲存
		if(params["batch.name"]){
			def batch = Batch.findById(params["batch.id"])

			if(!batch){
				batch = new Batch()
			}
			batch.name = params["batch.name"]
			batch.item = sheet.item

			if(sheet.instanceOf(PurchaseSheetDet)){
				batch.supplier = sheet.purchaseSheet.supplier
			}
			if(sheet.instanceOf(OutSrcPurchaseSheetDet)){
				batch.supplier = sheet.outSrcPurchaseSheet.supplier
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


}
