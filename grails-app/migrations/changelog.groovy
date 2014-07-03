databaseChangeLog = {

	changeSet(author: "Spooky (generated)", id: "1389790098582-5") {
		createTable(tableName: "customer_order") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "customer_ordePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_id", type: "bigint")

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "due_date", type: "datetime")

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

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-6") {
		createTable(tableName: "customer_order_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "customer_ordePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "editor", type: "varchar(255)")

			column(defaultValue: "-1", name: "import_flag", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "item_id", type: "bigint")

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "integer")

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}


	changeSet(author: "Spooky (generated)", id: "1389790098582-10") {
		createTable(tableName: "manufacture_order") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "manufacture_oPK")
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

			column(name: "qty", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-11") {
		createTable(tableName: "material_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_sheePK")
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

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-12") {
		createTable(tableName: "material_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "material_sheePK")
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

			column(name: "material_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-14") {
		createTable(tableName: "out_src_purchase_sheet") {
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

			column(name: "out_src_purchase_date", type: "datetime") {
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

	changeSet(author: "Spooky (generated)", id: "1389790098582-15") {
		createTable(tableName: "out_src_purchase_sheet_det") {
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

			column(name: "out_src_purchase_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-16") {
		createTable(tableName: "out_src_return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_returPK")
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

	changeSet(author: "Spooky (generated)", id: "1389790098582-17") {
		createTable(tableName: "out_src_return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "out_src_returPK")
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

			column(name: "out_src_return_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-18") {
		createTable(tableName: "purchase_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_sheePK")
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

			column(name: "incoming_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "last_updated", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "order_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "supplier_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-19") {
		createTable(tableName: "purchase_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "purchase_sheePK")
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

			column(name: "purchase_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "qty", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-20") {
		createTable(tableName: "return_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "return_sheetPK")
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

	changeSet(author: "Spooky (generated)", id: "1389790098582-21") {
		createTable(tableName: "return_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "return_sheet_PK")
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

			column(name: "qty", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "return_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-22") {
		createTable(tableName: "sale_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_sheetPK")
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

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-23") {
		createTable(tableName: "sale_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sale_sheet_dePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "batch_id", type: "bigint")

			column(name: "creator", type: "varchar(255)")

			column(name: "customer_order_det_id", type: "bigint") {
				constraints(nullable: "false")
			}

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

			column(name: "qty", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "sale_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-25") {
		createTable(tableName: "stock_in_sheet") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "stock_in_sheePK")
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

			column(name: "stock_in_date", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "workstation_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-26") {
		createTable(tableName: "stock_in_sheet_det") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "stock_in_sheePK")
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

			column(name: "sequence", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "site_id", type: "bigint")

			column(name: "stock_in_sheet_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "stock_location", type: "varchar(255)")

			column(name: "type_name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "warehouse", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-105") {
		createIndex(indexName: "FK592D73A5FA885C8", tableName: "batch") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-106") {
		createIndex(indexName: "FK592D73A92B046E8", tableName: "batch") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-107") {
		createIndex(indexName: "FK592D73A93A32368", tableName: "batch") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-108") {
		createIndex(indexName: "name_uniq_1389790098447", tableName: "batch", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-109") {
		createIndex(indexName: "FK109619041BF6E48C", tableName: "batch_route") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-110") {
		createIndex(indexName: "FK10961904352752AC", tableName: "batch_route") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-111") {
		createIndex(indexName: "FK109619045FA885C8", tableName: "batch_route") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-112") {
		createIndex(indexName: "FK1096190471EA1E8C", tableName: "batch_route") {
			column(name: "operation_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-113") {
		createIndex(indexName: "FK1096190492B046E8", tableName: "batch_route") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-115") {
		createIndex(indexName: "FK3E1D8C0352752AC", tableName: "batch_source") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-116") {
		createIndex(indexName: "FK3E1D8C092B046E8", tableName: "batch_source") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-117") {
		createIndex(indexName: "FK3E1D8C0DD948E6F", tableName: "batch_source") {
			column(name: "child_batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-119") {
		createIndex(indexName: "FK24217FDE92B046E8", tableName: "customer") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-120") {
		createIndex(indexName: "name_uniq_1389790098465", tableName: "customer", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-121") {
		createIndex(indexName: "FK86DB8BAD92B046E8", tableName: "customer_order") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-122") {
		createIndex(indexName: "FK86DB8BAD9A7AF488", tableName: "customer_order") {
			column(name: "customer_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-123") {
		createIndex(indexName: "unique_name", tableName: "customer_order", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-124") {
		createIndex(indexName: "FK51D0A08192B046E8", tableName: "customer_order_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-125") {
		createIndex(indexName: "FK51D0A08193A32368", tableName: "customer_order_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-126") {
		createIndex(indexName: "FK51D0A081D0768E2F", tableName: "customer_order_det") {
			column(name: "customer_order_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-127") {
		createIndex(indexName: "unique_sequence", tableName: "customer_order_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-128") {
		createIndex(indexName: "FK317B1392B046E8", tableName: "item") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-129") {
		createIndex(indexName: "name_uniq_1389790098472", tableName: "item", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-130") {
		createIndex(indexName: "FK8B96B61D1BF6E48C", tableName: "item_route") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-131") {
		createIndex(indexName: "FK8B96B61D71EA1E8C", tableName: "item_route") {
			column(name: "operation_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-132") {
		createIndex(indexName: "FK8B96B61D92B046E8", tableName: "item_route") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-133") {
		createIndex(indexName: "FK8B96B61D93A32368", tableName: "item_route") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-134") {
		createIndex(indexName: "sequence_uniq_1389790098474", tableName: "item_route", unique: "true") {
			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-135") {
		createIndex(indexName: "FK6C4A0A50352752AC", tableName: "manufacture_order") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-136") {
		createIndex(indexName: "FK6C4A0A5092B046E8", tableName: "manufacture_order") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-137") {
		createIndex(indexName: "FK6C4A0A5093A32368", tableName: "manufacture_order") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-138") {
		createIndex(indexName: "FK6C4A0A50D925BF0A", tableName: "manufacture_order") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-139") {
		createIndex(indexName: "unique_name", tableName: "manufacture_order", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-140") {
		createIndex(indexName: "FKB4A86C471BF6E48C", tableName: "material_sheet") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-141") {
		createIndex(indexName: "FKB4A86C4792B046E8", tableName: "material_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-142") {
		createIndex(indexName: "unique_name", tableName: "material_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-143") {
		createIndex(indexName: "FK16A7A41B352752AC", tableName: "material_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-144") {
		createIndex(indexName: "FK16A7A41B6BCD0205", tableName: "material_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-145") {
		createIndex(indexName: "FK16A7A41B92B046E8", tableName: "material_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-146") {
		createIndex(indexName: "FK16A7A41B93A32368", tableName: "material_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-147") {
		createIndex(indexName: "FK16A7A41BE0BE85BD", tableName: "material_sheet_det") {
			column(name: "material_sheet_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-148") {
		createIndex(indexName: "unique_sequence", tableName: "material_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-149") {
		createIndex(indexName: "FK631AD56792B046E8", tableName: "operation") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-150") {
		createIndex(indexName: "name_uniq_1389790098484", tableName: "operation", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-151") {
		createIndex(indexName: "FK9C4F74ED5FA885C8", tableName: "out_src_purchase_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-152") {
		createIndex(indexName: "FK9C4F74ED92B046E8", tableName: "out_src_purchase_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-153") {
		createIndex(indexName: "unique_name", tableName: "out_src_purchase_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-154") {
		createIndex(indexName: "FK4CADE9C1352752AC", tableName: "out_src_purchase_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-155") {
		createIndex(indexName: "FK4CADE9C16BCD0205", tableName: "out_src_purchase_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-156") {
		createIndex(indexName: "FK4CADE9C191ECBA27", tableName: "out_src_purchase_sheet_det") {
			column(name: "out_src_purchase_sheet_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-157") {
		createIndex(indexName: "FK4CADE9C192B046E8", tableName: "out_src_purchase_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-158") {
		createIndex(indexName: "FK4CADE9C193A32368", tableName: "out_src_purchase_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-159") {
		createIndex(indexName: "unique_sequence", tableName: "out_src_purchase_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-160") {
		createIndex(indexName: "FKE88830DC5FA885C8", tableName: "out_src_return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-161") {
		createIndex(indexName: "FKE88830DC92B046E8", tableName: "out_src_return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-162") {
		createIndex(indexName: "unique_name", tableName: "out_src_return_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-163") {
		createIndex(indexName: "FK15391630326F8809", tableName: "out_src_return_sheet_det") {
			column(name: "out_src_return_sheet_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-164") {
		createIndex(indexName: "FK15391630352752AC", tableName: "out_src_return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-165") {
		createIndex(indexName: "FK153916306BCD0205", tableName: "out_src_return_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-166") {
		createIndex(indexName: "FK1539163092B046E8", tableName: "out_src_return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-167") {
		createIndex(indexName: "FK1539163093A32368", tableName: "out_src_return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-168") {
		createIndex(indexName: "unique_sequence", tableName: "out_src_return_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-169") {
		createIndex(indexName: "FK71D940A15FA885C8", tableName: "purchase_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-170") {
		createIndex(indexName: "FK71D940A192B046E8", tableName: "purchase_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-171") {
		createIndex(indexName: "unique_name", tableName: "purchase_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-172") {
		createIndex(indexName: "FK6C42BB752BAC5389", tableName: "purchase_sheet_det") {
			column(name: "purchase_sheet_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-173") {
		createIndex(indexName: "FK6C42BB75352752AC", tableName: "purchase_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-174") {
		createIndex(indexName: "FK6C42BB7592B046E8", tableName: "purchase_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-175") {
		createIndex(indexName: "FK6C42BB7593A32368", tableName: "purchase_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-176") {
		createIndex(indexName: "unique_sequence", tableName: "purchase_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-177") {
		createIndex(indexName: "FK45B959905FA885C8", tableName: "return_sheet") {
			column(name: "supplier_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-178") {
		createIndex(indexName: "FK45B9599092B046E8", tableName: "return_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-179") {
		createIndex(indexName: "unique_name", tableName: "return_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-180") {
		createIndex(indexName: "FKAF7EC4E4352752AC", tableName: "return_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-181") {
		createIndex(indexName: "FKAF7EC4E470AA11EB", tableName: "return_sheet_det") {
			column(name: "return_sheet_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-182") {
		createIndex(indexName: "FKAF7EC4E492B046E8", tableName: "return_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-183") {
		createIndex(indexName: "FKAF7EC4E493A32368", tableName: "return_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-184") {
		createIndex(indexName: "unique_sequence", tableName: "return_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-185") {
		createIndex(indexName: "FK4561EF6792B046E8", tableName: "sale_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-186") {
		createIndex(indexName: "FK4561EF679A7AF488", tableName: "sale_sheet") {
			column(name: "customer_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-187") {
		createIndex(indexName: "unique_name", tableName: "sale_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-188") {
		createIndex(indexName: "FKDAAA973B352752AC", tableName: "sale_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-189") {
		createIndex(indexName: "FKDAAA973B3846B07D", tableName: "sale_sheet_det") {
			column(name: "sale_sheet_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-190") {
		createIndex(indexName: "FKDAAA973B92B046E8", tableName: "sale_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-191") {
		createIndex(indexName: "FKDAAA973B93A32368", tableName: "sale_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-192") {
		createIndex(indexName: "FKDAAA973BD925BF0A", tableName: "sale_sheet_det") {
			column(name: "customer_order_det_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-193") {
		createIndex(indexName: "unique_sequence", tableName: "sale_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-194") {
		createIndex(indexName: "name_uniq_1389790098518", tableName: "site", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-195") {
		createIndex(indexName: "FK937A70CE1BF6E48C", tableName: "stock_in_sheet") {
			column(name: "workstation_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-196") {
		createIndex(indexName: "FK937A70CE92B046E8", tableName: "stock_in_sheet") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-197") {
		createIndex(indexName: "unique_name", tableName: "stock_in_sheet", unique: "true") {
			column(name: "type_name")

			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-198") {
		createIndex(indexName: "FK7C460D22352752AC", tableName: "stock_in_sheet_det") {
			column(name: "batch_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-199") {
		createIndex(indexName: "FK7C460D2269FE91FE", tableName: "stock_in_sheet_det") {
			column(name: "stock_in_sheet_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-200") {
		createIndex(indexName: "FK7C460D226BCD0205", tableName: "stock_in_sheet_det") {
			column(name: "manufacture_order_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-201") {
		createIndex(indexName: "FK7C460D2292B046E8", tableName: "stock_in_sheet_det") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-202") {
		createIndex(indexName: "FK7C460D2293A32368", tableName: "stock_in_sheet_det") {
			column(name: "item_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-203") {
		createIndex(indexName: "unique_sequence", tableName: "stock_in_sheet_det", unique: "true") {
			column(name: "type_name")

			column(name: "name")

			column(name: "sequence")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-204") {
		createIndex(indexName: "FK9CDBF9CC92B046E8", tableName: "supplier") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-205") {
		createIndex(indexName: "name_uniq_1389790098523", tableName: "supplier", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-206") {
		createIndex(indexName: "FK22AA550392B046E8", tableName: "workstation") {
			column(name: "site_id")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-207") {
		createIndex(indexName: "name_uniq_1389790098524", tableName: "workstation", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-29") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "batch", constraintName: "FK592D73A93A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-30") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch", constraintName: "FK592D73A92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-31") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "batch", constraintName: "FK592D73A5FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-32") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "batch_route", constraintName: "FK10961904352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-33") {
		addForeignKeyConstraint(baseColumnNames: "operation_id", baseTableName: "batch_route", constraintName: "FK1096190471EA1E8C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-34") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_route", constraintName: "FK1096190492B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-35") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "batch_route", constraintName: "FK109619045FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-36") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "batch_route", constraintName: "FK109619041BF6E48C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-37") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "batch_source", constraintName: "FK3E1D8C0352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-38") {
		addForeignKeyConstraint(baseColumnNames: "child_batch_id", baseTableName: "batch_source", constraintName: "FK3E1D8C0DD948E6F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-39") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "batch_source", constraintName: "FK3E1D8C092B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-40") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "customer", constraintName: "FK24217FDE92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-41") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "customer_order", constraintName: "FK86DB8BAD9A7AF488", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-42") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "customer_order", constraintName: "FK86DB8BAD92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-43") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_id", baseTableName: "customer_order_det", constraintName: "FK51D0A081D0768E2F", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-44") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "customer_order_det", constraintName: "FK51D0A08193A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-45") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "customer_order_det", constraintName: "FK51D0A08192B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-46") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item", constraintName: "FK317B1392B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-47") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "item_route", constraintName: "FK8B96B61D93A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-48") {
		addForeignKeyConstraint(baseColumnNames: "operation_id", baseTableName: "item_route", constraintName: "FK8B96B61D71EA1E8C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "operation", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-49") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "item_route", constraintName: "FK8B96B61D92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-50") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "item_route", constraintName: "FK8B96B61D1BF6E48C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-51") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "manufacture_order", constraintName: "FK6C4A0A50352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-52") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "manufacture_order", constraintName: "FK6C4A0A50D925BF0A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-53") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "manufacture_order", constraintName: "FK6C4A0A5093A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-54") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "manufacture_order", constraintName: "FK6C4A0A5092B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-55") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_sheet", constraintName: "FKB4A86C4792B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-56") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "material_sheet", constraintName: "FKB4A86C471BF6E48C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-57") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "material_sheet_det", constraintName: "FK16A7A41B352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-58") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "material_sheet_det", constraintName: "FK16A7A41B93A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-59") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "material_sheet_det", constraintName: "FK16A7A41B6BCD0205", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-60") {
		addForeignKeyConstraint(baseColumnNames: "material_sheet_id", baseTableName: "material_sheet_det", constraintName: "FK16A7A41BE0BE85BD", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "material_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-61") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "material_sheet_det", constraintName: "FK16A7A41B92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-62") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "operation", constraintName: "FK631AD56792B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-63") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_sheet", constraintName: "FK9C4F74ED92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-64") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "out_src_purchase_sheet", constraintName: "FK9C4F74ED5FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-65") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK4CADE9C1352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-66") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK4CADE9C193A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-67") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK4CADE9C16BCD0205", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-68") {
		addForeignKeyConstraint(baseColumnNames: "out_src_purchase_sheet_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK4CADE9C191ECBA27", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "out_src_purchase_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-69") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_purchase_sheet_det", constraintName: "FK4CADE9C192B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-70") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_return_sheet", constraintName: "FKE88830DC92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-71") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "out_src_return_sheet", constraintName: "FKE88830DC5FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-72") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "out_src_return_sheet_det", constraintName: "FK15391630352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-73") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "out_src_return_sheet_det", constraintName: "FK1539163093A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-74") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "out_src_return_sheet_det", constraintName: "FK153916306BCD0205", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-75") {
		addForeignKeyConstraint(baseColumnNames: "out_src_return_sheet_id", baseTableName: "out_src_return_sheet_det", constraintName: "FK15391630326F8809", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "out_src_return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-76") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "out_src_return_sheet_det", constraintName: "FK1539163092B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-77") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_sheet", constraintName: "FK71D940A192B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-78") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "purchase_sheet", constraintName: "FK71D940A15FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-79") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "purchase_sheet_det", constraintName: "FK6C42BB75352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-80") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "purchase_sheet_det", constraintName: "FK6C42BB7593A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-81") {
		addForeignKeyConstraint(baseColumnNames: "purchase_sheet_id", baseTableName: "purchase_sheet_det", constraintName: "FK6C42BB752BAC5389", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "purchase_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-82") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "purchase_sheet_det", constraintName: "FK6C42BB7592B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-83") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "return_sheet", constraintName: "FK45B9599092B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-84") {
		addForeignKeyConstraint(baseColumnNames: "supplier_id", baseTableName: "return_sheet", constraintName: "FK45B959905FA885C8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "supplier", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-85") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "return_sheet_det", constraintName: "FKAF7EC4E4352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-86") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "return_sheet_det", constraintName: "FKAF7EC4E493A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-87") {
		addForeignKeyConstraint(baseColumnNames: "return_sheet_id", baseTableName: "return_sheet_det", constraintName: "FKAF7EC4E470AA11EB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "return_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-88") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "return_sheet_det", constraintName: "FKAF7EC4E492B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-89") {
		addForeignKeyConstraint(baseColumnNames: "customer_id", baseTableName: "sale_sheet", constraintName: "FK4561EF679A7AF488", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-90") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_sheet", constraintName: "FK4561EF6792B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-91") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "sale_sheet_det", constraintName: "FKDAAA973B352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-92") {
		addForeignKeyConstraint(baseColumnNames: "customer_order_det_id", baseTableName: "sale_sheet_det", constraintName: "FKDAAA973BD925BF0A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "customer_order_det", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-93") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "sale_sheet_det", constraintName: "FKDAAA973B93A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-94") {
		addForeignKeyConstraint(baseColumnNames: "sale_sheet_id", baseTableName: "sale_sheet_det", constraintName: "FKDAAA973B3846B07D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sale_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-95") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "sale_sheet_det", constraintName: "FKDAAA973B92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-96") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "stock_in_sheet", constraintName: "FK937A70CE92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-97") {
		addForeignKeyConstraint(baseColumnNames: "workstation_id", baseTableName: "stock_in_sheet", constraintName: "FK937A70CE1BF6E48C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "workstation", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-98") {
		addForeignKeyConstraint(baseColumnNames: "batch_id", baseTableName: "stock_in_sheet_det", constraintName: "FK7C460D22352752AC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "batch", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-99") {
		addForeignKeyConstraint(baseColumnNames: "item_id", baseTableName: "stock_in_sheet_det", constraintName: "FK7C460D2293A32368", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "item", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-100") {
		addForeignKeyConstraint(baseColumnNames: "manufacture_order_id", baseTableName: "stock_in_sheet_det", constraintName: "FK7C460D226BCD0205", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "manufacture_order", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-101") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "stock_in_sheet_det", constraintName: "FK7C460D2292B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-102") {
		addForeignKeyConstraint(baseColumnNames: "stock_in_sheet_id", baseTableName: "stock_in_sheet_det", constraintName: "FK7C460D2269FE91FE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "stock_in_sheet", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-103") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "supplier", constraintName: "FK9CDBF9CC92B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	changeSet(author: "Spooky (generated)", id: "1389790098582-104") {
		addForeignKeyConstraint(baseColumnNames: "site_id", baseTableName: "workstation", constraintName: "FK22AA550392B046E8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "site", referencesUniqueColumn: "false")
	}

	include file: '1.0.1.groovy'
}
