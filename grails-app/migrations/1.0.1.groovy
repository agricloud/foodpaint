databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1404155889824-1") {
		createTable(tableName: "inventory") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "inventoryPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_in_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_out_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-2") {
		createTable(tableName: "inventory_detail") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "inventory_detPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_in_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_out_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-3") {
		createTable(tableName: "material_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "supplier_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-4") {
		createTable(tableName: "material_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "material_return_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "material_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-5") {
		createTable(tableName: "out_src_purchase_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_purchPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "supplier_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-6") {
		createTable(tableName: "out_src_purchase_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_purchPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "manufacture_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "out_src_purchase_return_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "out_src_purchase_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-7") {
		createTable(tableName: "purchase_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "supplier_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-8") {
		createTable(tableName: "purchase_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_retuPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "purchase_return_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "purchase_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-9") {
		createTable(tableName: "sale_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_return_sPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "pick_up_address", type: "varchar(255)")

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-10") {
		createTable(tableName: "sale_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_return_sPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_det_id", type: "bigint")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sale_return_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sale_sheet_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-11") {
		createTable(tableName: "warehouse") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "warehousePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "capacity", type: "bigint")

			column(name: "capacity_unit", type: "varchar(255)")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-12") {
		createTable(tableName: "warehouse_location") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "warehouse_locPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "capacity", type: "bigint")

			column(name: "capacity_unit", type: "varchar(255)")

			column(name: "creator", type: "varchar(255)")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "varchar(255)")

			column(name: "editor", type: "varchar(255)")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "remark", type: "varchar(255)")

			column(name: "site_id", type: "bigint")

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-13") {
		addColumn(tableName: "customer") {
			column(name: "contact", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-14") {
		addColumn(tableName: "customer") {
			column(name: "fax", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-15") {
		addColumn(tableName: "customer") {
			column(name: "shipping_address", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-16") {
		addColumn(tableName: "customer") {
			column(name: "tel", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-17") {
		addColumn(tableName: "customer_order") {
			column(name: "shipping_address", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-18") {
		addColumn(tableName: "item_route") {
			column(name: "supplier_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-19") {
		addColumn(tableName: "manufacture_order") {
			column(name: "supplier_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-20") {
		addColumn(tableName: "manufacture_order") {
			column(name: "workstation_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-21") {
		addColumn(tableName: "material_sheet") {
			column(name: "supplier_id", type: "bigint")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-22") {
		addColumn(tableName: "material_sheet_det") {
			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-23") {
		addColumn(tableName: "material_sheet_det") {
			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-24") {
		addColumn(tableName: "material_sheet_det") {
			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-25") {
		addColumn(tableName: "out_src_purchase_sheet_det") {
			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-26") {
		addColumn(tableName: "out_src_purchase_sheet_det") {
			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-27") {
		addColumn(tableName: "purchase_sheet_det") {
			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-28") {
		addColumn(tableName: "purchase_sheet_det") {
			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-29") {
		addColumn(tableName: "sale_sheet") {
			column(name: "shipping_address", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-30") {
		addColumn(tableName: "sale_sheet_det") {
			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-31") {
		addColumn(tableName: "sale_sheet_det") {
			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-32") {
		addColumn(tableName: "stock_in_sheet_det") {
			column(name: "qty", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-33") {
		addColumn(tableName: "stock_in_sheet_det") {
			column(name: "warehouse_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-34") {
		addColumn(tableName: "stock_in_sheet_det") {
			column(name: "warehouse_location_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-35") {
		addColumn(tableName: "supplier") {
			column(name: "contact", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-36") {
		addColumn(tableName: "supplier") {
			column(name: "fax", type: "varchar(255)")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-37") {
		modifyDataType(columnName: "qty", newDataType: "bigint", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-38") {
		addNotNullConstraint(columnDataType: "bigint", columnName: "qty", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-39") {
		modifyDataType(columnName: "sequence", newDataType: "integer", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-40") {
		addNotNullConstraint(columnDataType: "integer", columnName: "sequence", tableName: "customer_order_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-41") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "unit", tableName: "item")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-42") {
		dropNotNullConstraint(columnDataType: "bigint", columnName: "workstation_id", tableName: "item_route")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-43") {
		modifyDataType(columnName: "qty", newDataType: "bigint", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-44") {
		dropNotNullConstraint(columnDataType: "bigint", columnName: "workstation_id", tableName: "material_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-45") {
		modifyDataType(columnName: "qty", newDataType: "bigint", tableName: "out_src_purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-46") {
		modifyDataType(columnName: "qty", newDataType: "bigint", tableName: "purchase_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-47") {
		dropNotNullConstraint(columnDataType: "bigint", columnName: "customer_order_det_id", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-48") {
		modifyDataType(columnName: "qty", newDataType: "bigint", tableName: "sale_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-49") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "description", tableName: "site")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-50") {
		dropNotNullConstraint(columnDataType: "varchar(255)", columnName: "country", tableName: "supplier")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-51") {
		dropForeignKeyConstraint(baseTableName: "out_src_return_sheet", baseTableSchemaName: "foodpaint", constraintName: "FKE88830DC92B046E8")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-52") {
		dropForeignKeyConstraint(baseTableName: "out_src_return_sheet", baseTableSchemaName: "foodpaint", constraintName: "FKE88830DC5FA885C8")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-53") {
		dropForeignKeyConstraint(baseTableName: "out_src_return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FK15391630352752AC")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-54") {
		dropForeignKeyConstraint(baseTableName: "out_src_return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FK1539163093A32368")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-55") {
		dropForeignKeyConstraint(baseTableName: "out_src_return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FK153916306BCD0205")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-56") {
		dropForeignKeyConstraint(baseTableName: "out_src_return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FK15391630326F8809")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-57") {
		dropForeignKeyConstraint(baseTableName: "out_src_return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FK1539163092B046E8")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-58") {
		dropForeignKeyConstraint(baseTableName: "return_sheet", baseTableSchemaName: "foodpaint", constraintName: "FK45B9599092B046E8")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-59") {
		dropForeignKeyConstraint(baseTableName: "return_sheet", baseTableSchemaName: "foodpaint", constraintName: "FK45B959905FA885C8")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-60") {
		dropForeignKeyConstraint(baseTableName: "return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FKAF7EC4E4352752AC")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-61") {
		dropForeignKeyConstraint(baseTableName: "return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FKAF7EC4E493A32368")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-62") {
		dropForeignKeyConstraint(baseTableName: "return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FKAF7EC4E470AA11EB")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-63") {
		dropForeignKeyConstraint(baseTableName: "return_sheet_det", baseTableSchemaName: "foodpaint", constraintName: "FKAF7EC4E492B046E8")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-129") {
		dropIndex(indexName: "sequence_uniq_1389790098474", tableName: "item_route")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-130") {
		dropIndex(indexName: "unique_name", tableName: "out_src_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-131") {
		dropIndex(indexName: "unique_sequence", tableName: "out_src_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-132") {
		dropIndex(indexName: "unique_name", tableName: "return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-133") {
		dropIndex(indexName: "unique_sequence", tableName: "return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-134") {
		createIndex(indexName: "FK8790195C92B046E8", tableName: "inventory") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-135") {
		createIndex(indexName: "FK8790195C93A32368", tableName: "inventory") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-136") {
		createIndex(indexName: "FK8790195CDF7DD10C", tableName: "inventory") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-137") {
		createIndex(indexName: "unique_item_id", tableName: "inventory", unique: "true") {
			column(name: "warehouse_id")

			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-138") {
		createIndex(indexName: "FK7A97FC94352752AC", tableName: "inventory_detail") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-139") {
		createIndex(indexName: "FK7A97FC9492B046E8", tableName: "inventory_detail") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-140") {
		createIndex(indexName: "FK7A97FC9493A32368", tableName: "inventory_detail") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-141") {
		createIndex(indexName: "FK7A97FC94DF7DD10C", tableName: "inventory_detail") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-142") {
		createIndex(indexName: "FK7A97FC94F7D7BA33", tableName: "inventory_detail") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-143") {
		createIndex(indexName: "unique_batch_id", tableName: "inventory_detail", unique: "true") {
			column(name: "item_id")

			column(name: "warehouse_location_id")

			column(name: "warehouse_id")

			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-144") {
		createIndex(indexName: "FK8B96B61D5FA885C8", tableName: "item_route") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-145") {
		createIndex(indexName: "unique_sequence", tableName: "item_route", unique: "true") {
			column(name: "item_id")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-146") {
		createIndex(indexName: "FK6C4A0A501BF6E48C", tableName: "manufacture_order") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-147") {
		createIndex(indexName: "FK6C4A0A505FA885C8", tableName: "manufacture_order") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-148") {
		createIndex(indexName: "FK6EBB35A81BF6E48C", tableName: "material_return_sheet") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-149") {
		createIndex(indexName: "FK6EBB35A85FA885C8", tableName: "material_return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-150") {
		createIndex(indexName: "FK6EBB35A892B046E8", tableName: "material_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-151") {
		createIndex(indexName: "unique_name", tableName: "material_return_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-152") {
		createIndex(indexName: "FK8D84D4FC352752AC", tableName: "material_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-153") {
		createIndex(indexName: "FK8D84D4FC6BCD0205", tableName: "material_return_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-154") {
		createIndex(indexName: "FK8D84D4FC74AB212C", tableName: "material_return_sheet_det") {
			column(name: "material_return_sheet_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-155") {
		createIndex(indexName: "FK8D84D4FC92B046E8", tableName: "material_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-156") {
		createIndex(indexName: "FK8D84D4FC93A32368", tableName: "material_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-157") {
		createIndex(indexName: "FK8D84D4FCDF7DD10C", tableName: "material_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-158") {
		createIndex(indexName: "FK8D84D4FCE4E8DC8", tableName: "material_return_sheet_det") {
			column(name: "material_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-159") {
		createIndex(indexName: "FK8D84D4FCF7D7BA33", tableName: "material_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-160") {
		createIndex(indexName: "unique_sequence", tableName: "material_return_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-161") {
		createIndex(indexName: "FKB4A86C475FA885C8", tableName: "material_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-162") {
		createIndex(indexName: "FK16A7A41BDF7DD10C", tableName: "material_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-163") {
		createIndex(indexName: "FK16A7A41BF7D7BA33", tableName: "material_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-164") {
		createIndex(indexName: "FK529E46425FA885C8", tableName: "out_src_purchase_return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-165") {
		createIndex(indexName: "FK529E464292B046E8", tableName: "out_src_purchase_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-166") {
		createIndex(indexName: "unique_name", tableName: "out_src_purchase_return_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-167") {
		createIndex(indexName: "FKB1DA0896352752AC", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-168") {
		createIndex(indexName: "FKB1DA08966BCD0205", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-169") {
		createIndex(indexName: "FKB1DA089692B046E8", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-170") {
		createIndex(indexName: "FKB1DA089693A32368", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-171") {
		createIndex(indexName: "FKB1DA0896B41E7D22", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "out_src_purchase_return_sheet_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-172") {
		createIndex(indexName: "FKB1DA0896D48BA592", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "out_src_purchase_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-173") {
		createIndex(indexName: "FKB1DA0896DF7DD10C", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-174") {
		createIndex(indexName: "FKB1DA0896F7D7BA33", tableName: "out_src_purchase_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-175") {
		createIndex(indexName: "unique_sequence", tableName: "out_src_purchase_return_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-176") {
		createIndex(indexName: "FK4CADE9C1DF7DD10C", tableName: "out_src_purchase_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-177") {
		createIndex(indexName: "FK4CADE9C1F7D7BA33", tableName: "out_src_purchase_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-178") {
		createIndex(indexName: "FK79DDA80E5FA885C8", tableName: "purchase_return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-179") {
		createIndex(indexName: "FK79DDA80E92B046E8", tableName: "purchase_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-180") {
		createIndex(indexName: "unique_name", tableName: "purchase_return_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-181") {
		createIndex(indexName: "FK83BBA462352752AC", tableName: "purchase_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-182") {
		createIndex(indexName: "FK83BBA4624DBE2608", tableName: "purchase_return_sheet_det") {
			column(name: "purchase_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-183") {
		createIndex(indexName: "FK83BBA4629011A0EC", tableName: "purchase_return_sheet_det") {
			column(name: "purchase_return_sheet_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-184") {
		createIndex(indexName: "FK83BBA46292B046E8", tableName: "purchase_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-185") {
		createIndex(indexName: "FK83BBA46293A32368", tableName: "purchase_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-186") {
		createIndex(indexName: "FK83BBA462DF7DD10C", tableName: "purchase_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-187") {
		createIndex(indexName: "FK83BBA462F7D7BA33", tableName: "purchase_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-188") {
		createIndex(indexName: "unique_sequence", tableName: "purchase_return_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-189") {
		createIndex(indexName: "FK6C42BB75DF7DD10C", tableName: "purchase_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-190") {
		createIndex(indexName: "FK6C42BB75F7D7BA33", tableName: "purchase_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-191") {
		createIndex(indexName: "FK81FDEE8892B046E8", tableName: "sale_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-192") {
		createIndex(indexName: "FK81FDEE889A7AF488", tableName: "sale_return_sheet") {
			column(name: "customer_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-193") {
		createIndex(indexName: "unique_name", tableName: "sale_return_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-194") {
		createIndex(indexName: "FK5D001DDC352752AC", tableName: "sale_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-195") {
		createIndex(indexName: "FK5D001DDC816578C8", tableName: "sale_return_sheet_det") {
			column(name: "sale_sheet_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-196") {
		createIndex(indexName: "FK5D001DDC8CA8562C", tableName: "sale_return_sheet_det") {
			column(name: "sale_return_sheet_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-197") {
		createIndex(indexName: "FK5D001DDC92B046E8", tableName: "sale_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-198") {
		createIndex(indexName: "FK5D001DDC93A32368", tableName: "sale_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-199") {
		createIndex(indexName: "FK5D001DDCD925BF0A", tableName: "sale_return_sheet_det") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-200") {
		createIndex(indexName: "FK5D001DDCDF7DD10C", tableName: "sale_return_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-201") {
		createIndex(indexName: "FK5D001DDCF7D7BA33", tableName: "sale_return_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-202") {
		createIndex(indexName: "unique_sequence", tableName: "sale_return_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-203") {
		createIndex(indexName: "FKDAAA973BDF7DD10C", tableName: "sale_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-204") {
		createIndex(indexName: "FKDAAA973BF7D7BA33", tableName: "sale_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-205") {
		createIndex(indexName: "FK7C460D22DF7DD10C", tableName: "stock_in_sheet_det") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-206") {
		createIndex(indexName: "FK7C460D22F7D7BA33", tableName: "stock_in_sheet_det") {
			column(name: "warehouse_location_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-207") {
		createIndex(indexName: "FK88EF3AC392B046E8", tableName: "warehouse") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-208") {
		createIndex(indexName: "name_uniq_1404155889218", tableName: "warehouse", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-209") {
		createIndex(indexName: "FK423801B192B046E8", tableName: "warehouse_location") {
			column(name: "site_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-210") {
		createIndex(indexName: "FK423801B1DF7DD10C", tableName: "warehouse_location") {
			column(name: "warehouse_id")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-211") {
		createIndex(indexName: "name_uniq_1404155889219", tableName: "warehouse_location", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-212") {
		dropColumn(columnName: "expiration_date", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-213") {
		dropColumn(columnName: "out_src_purchase_date", tableName: "out_src_purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-214") {
		dropColumn(columnName: "incoming_date", tableName: "purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-215") {
		dropColumn(columnName: "order_date", tableName: "purchase_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-216") {
		dropColumn(columnName: "stock_in_date", tableName: "stock_in_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-217") {
		dropColumn(columnName: "stock_location", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-218") {
		dropColumn(columnName: "warehouse", tableName: "stock_in_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-219") {
		dropTable(tableName: "out_src_return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-220") {
		dropTable(tableName: "out_src_return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-221") {
		dropTable(tableName: "return_sheet")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-222") {
		dropTable(tableName: "return_sheet_det")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-64") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "inventory", constraintName: "FK8790195C93A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-65") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "inventory", constraintName: "FK8790195C92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-66") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "inventory", constraintName: "FK8790195CDF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-67") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "inventory_detail", constraintName: "FK7A97FC94352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-68") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "inventory_detail", constraintName: "FK7A97FC9493A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-69") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "inventory_detail", constraintName: "FK7A97FC9492B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-70") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "inventory_detail", constraintName: "FK7A97FC94DF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-71") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "inventory_detail", constraintName: "FK7A97FC94F7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-72") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "item_route", constraintName: "FK8B96B61D5FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-73") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "manufacture_order", constraintName: "FK6C4A0A505FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-74") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "manufacture_order", constraintName: "FK6C4A0A501BF6E48C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-75") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_return_sheet", constraintName: "FK6EBB35A892B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-76") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "material_return_sheet", constraintName: "FK6EBB35A85FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-77") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "material_return_sheet", constraintName: "FK6EBB35A81BF6E48C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-78") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FC352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-79") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FC93A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-80") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FC6BCD0205", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-81") {
		addForeignKeyConstraint(baseColumnNames: "material_return_sheet_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FC74AB212C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-82") {
		addForeignKeyConstraint(baseColumnNames: "material_sheet_det_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FCE4E8DC8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-83") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FC92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-84") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FCDF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-85") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "material_return_sheet_det", constraintName: "FK8D84D4FCF7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-86") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "material_sheet", constraintName: "FKB4A86C475FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-87") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "material_sheet_det", constraintName: "FK16A7A41BDF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-88") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "material_sheet_det", constraintName: "FK16A7A41BF7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-89") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_return_sheet", constraintName: "FK529E464292B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-90") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "out_src_purchase_return_sheet", constraintName: "FK529E46425FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-91") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA0896352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-92") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA089693A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-93") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA08966BCD0205", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-94") {
		addForeignKeyConstraint(baseColumnNames: "out_src_purchase_return_sheet_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA0896B41E7D22", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "out_src_purchase_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-95") {
		addForeignKeyConstraint(baseColumnNames: "out_src_purchase_sheet_det_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA0896D48BA592", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "out_src_purchase_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-96") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA089692B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-97") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA0896DF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-98") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "out_src_purchase_return_sheet_det", constraintName: "FKB1DA0896F7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-99") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK4CADE9C1DF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-100") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK4CADE9C1F7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-101") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_return_sheet", constraintName: "FK79DDA80E92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-102") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "purchase_return_sheet", constraintName: "FK79DDA80E5FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-103") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK83BBA462352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-104") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK83BBA46293A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-105") {
		addForeignKeyConstraint(baseColumnNames: "purchase_return_sheet_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK83BBA4629011A0EC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "purchase_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-106") {
		addForeignKeyConstraint(baseColumnNames: "purchase_sheet_det_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK83BBA4624DBE2608", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "purchase_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-107") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK83BBA46292B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-108") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK83BBA462DF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-109") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "purchase_return_sheet_det", constraintName: "FK83BBA462F7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-110") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "purchase_sheet_det", constraintName: "FK6C42BB75DF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-111") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "purchase_sheet_det", constraintName: "FK6C42BB75F7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-112") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "sale_return_sheet", constraintName: "FK81FDEE889A7AF488", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-113") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_return_sheet", constraintName: "FK81FDEE8892B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-114") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDC352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-115") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDCD925BF0A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-116") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDC93A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-117") {
		addForeignKeyConstraint(baseColumnNames: "sale_return_sheet_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDC8CA8562C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sale_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-118") {
		addForeignKeyConstraint(baseColumnNames: "sale_sheet_det_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDC816578C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sale_sheet_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-119") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDC92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-120") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDCDF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-121") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "sale_return_sheet_det", constraintName: "FK5D001DDCF7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-122") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "sale_sheet_det", constraintName: "FKDAAA973BDF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-123") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "sale_sheet_det", constraintName: "FKDAAA973BF7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-124") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "stock_in_sheet_det", constraintName: "FK7C460D22DF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-125") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_location_id", baseTableName: "stock_in_sheet_det", constraintName: "FK7C460D22F7D7BA33", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse_location", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-126") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "warehouse", constraintName: "FK88EF3AC392B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-127") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "warehouse_location", constraintName: "FK423801B192B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "pipi (generated)", id: "1404155889824-128") {
		addForeignKeyConstraint(baseColumnNames: "warehouse_id", baseTableName: "warehouse_location", constraintName: "FK423801B1DF7DD10C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "warehouse", referencesUniqueColumn: "false")
	}
}
