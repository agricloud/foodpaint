databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1406694837858-1") {
		addNotNullConstraint(columnDataType: "bigint", columnName: "batch_id", tableName: "manufacture_order")
	}

	changeSet(author: "pipi (generated)", id: "1406694837858-2") {
		dropIndex(indexName: "unique_name", tableName: "warehouse_location")
	}

	changeSet(author: "pipi (generated)", id: "1406694837858-3") {
		createIndex(indexName: "unique_name", tableName: "warehouse_location", unique: "true") {
			column(name: "site_id")

			column(name: "warehouse_id")

			column(name: "name")
		}
	}
}
