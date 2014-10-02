databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1405098598371-1") {
		addColumn(tableName: "inventory") {
			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-2") {
		addColumn(tableName: "inventory_detail") {
			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-3") {
		addColumn(tableName: "site") {
			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-4") {
		addColumn(tableName: "warehouse") {
			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-5") {
		addColumn(tableName: "warehouse_location") {
			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-6") {
		modifyDataType(columnName: "expect_qty", newDataType: "double precision", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-7") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "expect_qty", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-8") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-9") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-10") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "inventory")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-11") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "inventory")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-12") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-13") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-14") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "title", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-15") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-16") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-17") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-18") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-19") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-20") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-21") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-22") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-23") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-24") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-25") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "address", tableName: "site")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-26") {
		modifyDataType(columnName: "qty", newDataType: "double precision", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-27") {
		addNotNullConstraint(columnDataType: "double precision", columnName: "qty", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-28") {
		dropIndex(indexName: "name_uniq_1389790098447", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-29") {
		dropIndex(indexName: "unique_sequence", tableName: "batch_route")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-30") {
		dropIndex(indexName: "unique_child_batch_id", tableName: "batch_source")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-31") {
		dropIndex(indexName: "name_uniq_1389790098465", tableName: "customer")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-32") {
		dropIndex(indexName: "unique_name", tableName: "customer_order")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-33") {
		dropIndex(indexName: "unique_sequence", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-34") {
		dropIndex(indexName: "unique_item_id", tableName: "inventory")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-35") {
		dropIndex(indexName: "unique_batch_id", tableName: "inventory_detail")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-36") {
		dropIndex(indexName: "name_uniq_1389790098472", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-37") {
		dropIndex(indexName: "unique_sequence", tableName: "item_route")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-38") {
		dropIndex(indexName: "unique_name", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-39") {
		dropIndex(indexName: "unique_name", tableName: "material_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-40") {
		dropIndex(indexName: "unique_sequence", tableName: "material_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-41") {
		dropIndex(indexName: "unique_name", tableName: "material_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-42") {
		dropIndex(indexName: "unique_sequence", tableName: "material_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-43") {
		dropIndex(indexName: "name_uniq_1389790098484", tableName: "operation")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-44") {
		dropIndex(indexName: "unique_name", tableName: "out_src_purchase_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-45") {
		dropIndex(indexName: "unique_sequence", tableName: "out_src_purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-46") {
		dropIndex(indexName: "unique_name", tableName: "out_src_purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-47") {
		dropIndex(indexName: "unique_sequence", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-48") {
		dropIndex(indexName: "unique_name", tableName: "purchase_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-49") {
		dropIndex(indexName: "unique_sequence", tableName: "purchase_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-50") {
		dropIndex(indexName: "unique_name", tableName: "purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-51") {
		dropIndex(indexName: "unique_sequence", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-52") {
		dropIndex(indexName: "unique_name", tableName: "sale_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-53") {
		dropIndex(indexName: "unique_sequence", tableName: "sale_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-54") {
		dropIndex(indexName: "unique_name", tableName: "sale_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-55") {
		dropIndex(indexName: "unique_sequence", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-56") {
		dropIndex(indexName: "unique_name", tableName: "stock_in_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-57") {
		dropIndex(indexName: "unique_sequence", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-58") {
		dropIndex(indexName: "name_uniq_1389790098523", tableName: "supplier")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-59") {
		dropIndex(indexName: "name_uniq_1404155889218", tableName: "warehouse")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-60") {
		dropIndex(indexName: "name_uniq_1404155889219", tableName: "warehouse_location")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-61") {
		dropIndex(indexName: "name_uniq_1389790098524", tableName: "workstation")
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-62") {
		createIndex(indexName: "unique_name", tableName: "batch", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-63") {
		createIndex(indexName: "unique_sequence", tableName: "batch_route", unique: "true") {
			column(name: "site_id")

			column(name: "batch_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-64") {
		createIndex(indexName: "unique_child_batch_id", tableName: "batch_source", unique: "true") {
			column(name: "site_id")

			column(name: "batch_id")

			column(name: "child_batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-65") {
		createIndex(indexName: "unique_name", tableName: "customer", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-66") {
		createIndex(indexName: "unique_name", tableName: "customer_order", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-67") {
		createIndex(indexName: "unique_sequence", tableName: "customer_order_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-68") {
		createIndex(indexName: "unique_item_id", tableName: "inventory", unique: "true") {
			column(name: "site_id")

			column(name: "warehouse_id")

			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-69") {
		createIndex(indexName: "unique_batch_id", tableName: "inventory_detail", unique: "true") {
			column(name: "site_id")

			column(name: "item_id")

			column(name: "warehouse_location_id")

			column(name: "warehouse_id")

			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-70") {
		createIndex(indexName: "unique_name", tableName: "item", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-71") {
		createIndex(indexName: "unique_sequence", tableName: "item_route", unique: "true") {
			column(name: "site_id")

			column(name: "item_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-72") {
		createIndex(indexName: "unique_name", tableName: "manufacture_order", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-73") {
		createIndex(indexName: "unique_name", tableName: "material_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-74") {
		createIndex(indexName: "unique_sequence", tableName: "material_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-75") {
		createIndex(indexName: "unique_name", tableName: "material_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-76") {
		createIndex(indexName: "unique_sequence", tableName: "material_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-77") {
		createIndex(indexName: "unique_name", tableName: "operation", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-78") {
		createIndex(indexName: "unique_name", tableName: "out_src_purchase_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-79") {
		createIndex(indexName: "unique_sequence", tableName: "out_src_purchase_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-80") {
		createIndex(indexName: "unique_name", tableName: "out_src_purchase_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-81") {
		createIndex(indexName: "unique_sequence", tableName: "out_src_purchase_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-82") {
		createIndex(indexName: "unique_name", tableName: "purchase_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-83") {
		createIndex(indexName: "unique_sequence", tableName: "purchase_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-84") {
		createIndex(indexName: "unique_name", tableName: "purchase_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-85") {
		createIndex(indexName: "unique_sequence", tableName: "purchase_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-86") {
		createIndex(indexName: "unique_name", tableName: "sale_return_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-87") {
		createIndex(indexName: "unique_sequence", tableName: "sale_return_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-88") {
		createIndex(indexName: "unique_name", tableName: "sale_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-89") {
		createIndex(indexName: "unique_sequence", tableName: "sale_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-90") {
		createIndex(indexName: "unique_name", tableName: "stock_in_sheet", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-91") {
		createIndex(indexName: "unique_sequence", tableName: "stock_in_sheet_det", unique: "true") {
			column(name: "site_id")

			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-92") {
		createIndex(indexName: "unique_name", tableName: "supplier", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-93") {
		createIndex(indexName: "unique_name", tableName: "warehouse", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-94") {
		createIndex(indexName: "unique_name", tableName: "warehouse_location", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1405098598371-95") {
		createIndex(indexName: "unique_name", tableName: "workstation", unique: "true") {
			column(name: "site_id")

			column(name: "name")
		}
	}
}
