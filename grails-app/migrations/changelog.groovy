databaseChangeLog = {

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-1") {
        createSequence(incrementBy: "1", sequenceName: "hibernate_sequence", startValue: "1")
    }

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-2") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-3") {
        createTable(tableName: "usuario") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "usuarioPK")
            }

            column(name: "version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "BOOLEAN") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-4") {
        createTable(tableName: "usuario_role") {
            column(name: "usuario_id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "usuario_rolePK")
            }

            column(name: "role_id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "usuario_rolePK")
            }
        }
    }

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-5") {
        addUniqueConstraint(columnNames: "authority", constraintName: "UC_ROLEAUTHORITY_COL", tableName: "role")
    }

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-6") {
        addUniqueConstraint(columnNames: "email", constraintName: "UC_USUARIOEMAIL_COL", tableName: "usuario")
    }

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-7") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "usuario_role", constraintName: "FKe7gfguqsiox6p89xggm8g2twf", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", validate: "true")
    }

    changeSet(author: "COMPUTADOR (generated)", id: "1795642420413-8") {
        addForeignKeyConstraint(baseColumnNames: "usuario_id", baseTableName: "usuario_role", constraintName: "FKpc2qjts6sqq4hja9f6i3hf0ep", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "usuario", validate: "true")
    }
}
