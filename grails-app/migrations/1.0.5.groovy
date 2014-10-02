databaseChangeLog = {

	changeSet(author: "pipi (generated)", id: "1405370736855-1") {
		dropIndex(indexName: "unique_name", tableName: "batch")
	}

	changeSet(author: "pipi (generated)", id: "1405370736855-2") {
		createIndex(indexName: "name_uniq_1405370735800", tableName: "batch", unique: "true") {
			column(name: "name")
		}
	}
}
