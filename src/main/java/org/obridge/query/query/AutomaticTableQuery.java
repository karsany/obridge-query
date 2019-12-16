package org.obridge.query.query;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.obridge.query.annotation.Bind;
import org.obridge.query.interfaces.Query;
import org.obridge.query.util.StringHelper;

public class AutomaticTableQuery implements Query {
    @Override
    public String sql() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String sql(Method m) {

        List<String> s = new ArrayList<>();

        for (Parameter parameter : m.getParameters()) {
            final String bindname = parameter.getAnnotation(Bind.class)
                                             .value();
            s.add(StringHelper.toOracleName(bindname) + " = :" + bindname);

        }

        String where = s.size() > 0 ? " where " + s.stream()
                                                   .collect(Collectors.joining(" AND "))
                                    : "";

        final String q = "select * from " + StringHelper.toOracleName(m.getDeclaringClass()
                                                                       .getSimpleName())
                + where;

        System.out.println(q);
        return q;
    }
}
