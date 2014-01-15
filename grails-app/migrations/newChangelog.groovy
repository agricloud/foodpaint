databaseChangeLog = {

	changeSet(author: "Spooky (generated)", id: "1389790558006-1") {
		dropNotNullConstraint(columnDataType: "VARCHAR(255)", columnName: "description", tableName: "operation")
	}
}
