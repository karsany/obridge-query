package org.obridge.query.query;

import java.util.List;

import org.obridge.query.annotation.Bind;
import org.obridge.query.annotation.QuerySource;
import org.obridge.query.interfaces.JsonString;

public interface AllTables {

    @QuerySource(AutomaticTableQuery.class)
    List<Table> getAllTables();

    @QuerySource(AutomaticTableQuery.class)
    List<Table> getAllTablesByOwner(@Bind("owner") String owner);

    interface Table extends JsonString {

        String owner();

        String tableName();

    }
}
