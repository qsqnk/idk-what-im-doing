/*
 * This file is generated by jOOQ.
 */
package domain.db;


import domain.db.tables.FlywaySchemaHistory;
import domain.db.tables.Message;
import domain.db.tables.records.FlywaySchemaHistoryRecord;
import domain.db.tables.records.MessageRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<FlywaySchemaHistoryRecord> FLYWAY_SCHEMA_HISTORY_PK = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), new TableField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final UniqueKey<MessageRecord> MESSAGE_PKEY = Internal.createUniqueKey(Message.MESSAGE, DSL.name("message_pkey"), new TableField[] { Message.MESSAGE.ID }, true);
}
