databaseChangeLog = {
    include file: '001-create-security-tables.groovy'

    include file: '002-insert-default-roles.groovy'
}