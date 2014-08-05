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
				if(sheet){
					if(sheet.instanceOf(PurchaseSheetDet)){
						if(sheet.item != batch.item){
							isSuccess = false
							args=[sheet]
							msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
						}
						else{
							batch.supplier = sheet.purchaseSheet.supplier
							isSuccess = true
						}
					}
					if(sheet.instanceOf(StockInSheetDet)){
						if(sheet.item != batch.item){
							isSuccess = false
							args=[sheet]
							msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
						}
						else{
							isSuccess = true
						}
					}
					if(sheet.instanceOf(OutSrcPurchaseSheetDet)){
						if(sheet.item != batch.item){
							isSuccess = false
							args=[sheet]
							msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
						}
						else{
							batch.supplier = sheet.outSrcPurchaseSheet.supplier
							isSuccess = true
						}
					}
					if(sheet.instanceOf(ManufactureOrder)){
						if(sheet.item != batch.item){
							isSuccess = false
							args=[sheet]
							msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
						}
						else{
							batch.expectQty = sheet.qty
							isSuccess = true
						}
					}
				}
				else
					isSuccess = true
			}

		}
		else{//未指定欲新增之批號
			isSuccess = false
			msg = messageSource.getMessage("batch.name.params.notfound", args, Locale.getDefault())  
		}

		return [success:isSuccess, batch:batch, message: msg]
	}

	def createBatchInstanceByJson(params, sheet){
		def isSuccess
		def msg
		Object[] args=[]
		//新增批號 若批號已存在則不允許新增
		if(params["batch.name"]){

			if(Batch.findByName(params["batch.name"])){
				args[params["batch.name"]]
				msg = messageSource.getMessage("batch.name.unique", args, Locale.getDefault())
            	return [success:false, message: msg]
			}
			else{
				def batch = new Batch()
			
				batch.name = params["batch.name"]
				batch.item = Item.get(params.item.id)

				if(sheet){
					if(sheet.instanceOf(PurchaseSheetDet)){
						batch.supplier = sheet.purchaseSheet.supplier
						isSuccess = true
					}
					if(sheet.instanceOf(StockInSheetDet)){
						if(sheet.item != batch.item){
							isSuccess = false
							args=[sheet]
							msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
						}
						else{
							isSuccess =true
						}
					}
					if(sheet.instanceOf(OutSrcPurchaseSheetDet)){
						if(sheet.item != batch.item){
							isSuccess = false
							args=[sheet]
							msg = messageSource.getMessage("sheet.item.batch.item.not.equal", args, Locale.getDefault())
						}
						else{
							batch.supplier = sheet.outSrcPurchaseSheet.supplier
							isSuccess = true
						}
					}
					if(sheet.instanceOf(ManufactureOrder)){
						batch.expectQty = sheet.qty
						isSuccess = true
					}
				}
				else
					isSuccess = true

				if(isSuccess){
					if(params["site.id"] && params["site.id"] != "null")
						batch.site = Site.findById(params["site.id"])

					def result=domainService.save(batch)

					if(result.success){
						result.batch=batch
					}
					return result
				}
				else
					return [success:isSuccess, batch:batch, message: msg]
			}
		}
		else{//未指定欲新增之批號
			msg = messageSource.getMessage("batch.name.params.notfound", args, Locale.getDefault())
            return [success:false, message: msg]
		}
	}

}
