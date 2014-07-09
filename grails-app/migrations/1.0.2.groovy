databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1404898468354-1") {
		modifyDataType(columnName: "expect_qty", newDataType: "double precision", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-2") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-3") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "inventory")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-4") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-5") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-6") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-7") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-8") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-9") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-10") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-11") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-12") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-13") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-14") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-15") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-16") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-17") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-18") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-19") {
		modifyDataType(columnName: "capacity", newDataType: "double precision", tableName: "warehouse")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-20") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "capacity", tableName: "warehouse")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-21") {
		modifyDataType(columnName: "capacity", newDataType: "double precision", tableName: "warehouse_location")
	}

	changeSet(author: "pipi (generated)", id: "1404898468354-22") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "capacity", tableName: "warehouse_location")
	}
}
