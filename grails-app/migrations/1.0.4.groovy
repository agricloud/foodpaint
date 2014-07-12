databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1405191595981-1") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-2") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-3") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "batch_route")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-4") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "batch_route")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-5") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "batch_source")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-6") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "batch_source")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-7") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "customer")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-8") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "customer")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-9") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "customer_order")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-10") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "customer_order")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-11") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-12") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-13") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "inventory")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-14") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-15") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-16") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-17") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-18") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "item_route")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-19") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "item_route")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-20") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-21") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-22") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "material_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-23") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "material_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-24") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-25") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-26") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "material_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-27") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "material_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-28") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-29") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-30") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "operation")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-31") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "operation")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-32") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "out_src_purchase_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-33") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "out_src_purchase_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-34") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-35") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-36") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "out_src_purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-37") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "out_src_purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-38") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-39") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-40") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "purchase_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-41") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "purchase_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-42") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-43") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-44") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-45") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-46") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-47") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-48") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "sale_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-49") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "sale_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-50") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-51") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-52") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "sale_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-53") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "sale_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-54") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-55") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-56") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "site")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-57") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "site")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-58") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "stock_in_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-59") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "stock_in_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-60") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-61") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-62") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "supplier")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-63") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "supplier")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-64") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "warehouse")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-65") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "warehouse")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-66") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "warehouse_location")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-67") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "warehouse_location")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-68") {
		modifyDataType(columnName: "import_flag", newDataType: "varchar(255)", tableName: "workstation")
	}

	changeSet(author: "pipi (generated)", id: "1405191595981-69") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "import_flag", tableName: "workstation")
	}
}
