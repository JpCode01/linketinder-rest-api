databaseChangeLog = {

    changeSet(author: "linketinder", id: "insert-default-roles") {

        ['ROLE_CANDIDATO', 'ROLE_EMPRESA', 'ROLE_RECRUTADOR'].each { authority ->

            insert(tableName: "role") {
                column(name: "id", valueComputed: "nextval('hibernate_sequence')")
                column(name: "version", valueNumeric: 0)
                column(name: "authority", value: authority)
            }
        }
    }
}