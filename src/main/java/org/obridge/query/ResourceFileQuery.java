package org.obridge.query;


import org.obridge.query.interfaces.Query;

import java.lang.reflect.Method;
import java.util.Scanner;

public class ResourceFileQuery implements Query {

    @Override
    public String sql() {
        throw new UnsupportedOperationException("Resource file must be used with method parameter");
    }

    @Override
    public String sql(Method m) {
        String sqlFileName = m.getDeclaringClass().getSimpleName() + "_" + m.getName().substring(0, 1).toUpperCase() + m.getName().substring(1) + ".sql";
        return new Scanner(m.getDeclaringClass().getResourceAsStream(sqlFileName), "UTF-8").useDelimiter("\\A").next();
    }

}
