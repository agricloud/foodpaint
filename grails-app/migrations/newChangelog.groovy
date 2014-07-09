databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1404936797380-1") {
		modifyDataType(columnName: "expect_qty", newDataType: "double precision", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-2") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "expect_qty", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-3") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-4") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-5") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "inventory")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-6") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "inventory")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-7") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-8") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-9") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-10") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-11") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-12") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-13") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-14") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-15") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-16") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-17") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-18") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-19") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-20") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-21") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-22") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-23") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-24") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-25") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-26") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-27") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-28") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-29") {
		modifyDataType(columnName: "capacity", newDataType: "double precision", tableName: "warehouse")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-31") {
		modifyDataType(columnName: "capacity", newDataType: "double precision", tableName: "warehouse_location")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-33") {
		dropForeignKeyConstraint(baseTableName: "batch_report_det", baseTableSchemaName: "foodtest", constraintName: "FKCF51E4AD6E07D5BB")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-34") {
		dropForeignKeyConstraint(baseTableName: "batch_report_det", baseTableSchemaName: "foodtest", constraintName: "FKCF51E4ADD78243C4")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-35") {
		dropForeignKeyConstraint(baseTableName: "batch_report_det", baseTableSchemaName: "foodtest", constraintName: "FKCF51E4AD606AE3A2")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-36") {
		dropForeignKeyConstraint(baseTableName: "batch_report_det", baseTableSchemaName: "foodtest", constraintName: "FKCF51E4AD6B3BA5F9")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-37") {
		dropForeignKeyConstraint(baseTableName: "param", baseTableSchemaName: "foodtest", constraintName: "FK658188D6B3BA5F9")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-38") {
		dropForeignKeyConstraint(baseTableName: "report", baseTableSchemaName: "foodtest", constraintName: "FKC84C55346B3BA5F9")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-39") {
		dropForeignKeyConstraint(baseTableName: "report_params", baseTableSchemaName: "foodtest", constraintName: "FKCDF21EF16C2E8279")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-40") {
		dropForeignKeyConstraint(baseTableName: "report_params", baseTableSchemaName: "foodtest", constraintName: "FKCDF21EF171A4821B")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-41") {
		dropForeignKeyConstraint(baseTableName: "report_params", baseTableSchemaName: "foodtest", constraintName: "FKCDF21EF11999F0DB")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-42") {
		dropForeignKeyConstraint(baseTableName: "report_params", baseTableSchemaName: "foodtest", constraintName: "FKCDF21EF193BA5719")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-43") {
		dropForeignKeyConstraint(baseTableName: "report_params", baseTableSchemaName: "foodtest", constraintName: "FKCDF21EF16B3BA5F9")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-44") {
		dropForeignKeyConstraint(baseTableName: "report_params", baseTableSchemaName: "foodtest", constraintName: "FKCDF21EF1365BF459")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-45") {
		dropForeignKeyConstraint(baseTableName: "report_params", baseTableSchemaName: "foodtest", constraintName: "FKCDF21EF116A6A05B")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-46") {
		dropForeignKeyConstraint(baseTableName: "user", baseTableSchemaName: "foodtest", constraintName: "FK36EBCB6B3BA5F9")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-47") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "foodtest", constraintName: "FK143BF46A40216399")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-48") {
		dropForeignKeyConstraint(baseTableName: "user_role", baseTableSchemaName: "foodtest", constraintName: "FK143BF46AE54C2779")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-49") {
		dropIndex(indexName: "batch_id", tableName: "batch_report_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-50") {
		dropIndex(indexName: "name", tableName: "param")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-51") {
		dropIndex(indexName: "report_id", tableName: "report_params")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-52") {
		dropIndex(indexName: "authority", tableName: "role")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-53") {
		dropIndex(indexName: "username", tableName: "user")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-54") {
		dropColumn(columnName: "expiration_date", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-55") {
		dropColumn(columnName: "remark", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-56") {
		dropColumn(columnName: "uuid", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-57") {
		dropColumn(columnName: "due_days", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-58") {
		dropColumn(columnName: "effect_end_date", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-59") {
		dropColumn(columnName: "effect_start_date", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-60") {
		dropTable(tableName: "batch_report_det")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-61") {
		dropTable(tableName: "param")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-62") {
		dropTable(tableName: "report")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-63") {
		dropTable(tableName: "report_params")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-64") {
		dropTable(tableName: "request_map")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-65") {
		dropTable(tableName: "role")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-66") {
		dropTable(tableName: "user")
	}

	changeSet(author: "pipi (generated)", id: "1404936797380-67") {
		dropTable(tableName: "user_role")
	}
}
