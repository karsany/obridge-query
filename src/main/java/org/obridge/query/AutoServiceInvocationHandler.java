package org.obridge.query;

import org.obridge.query.annotation.Bind;
import org.obridge.query.util.NamedParameterStatement;

import javax.sql.DataSource;
import java.lang.reflect.*;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

class AutoServiceInvocationHandler implements InvocationHandler {

    private final DataSource ds;
    private final Class clazz;

    public AutoServiceInvocationHandler(DataSource ds, Class clazz) {
        this.ds = ds;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String sqlFileName = clazz.getSimpleName() + "_" + method.getName().substring(0, 1).toUpperCase() + method.getName().substring(1) + ".sql";
        final String query = new Scanner(clazz.getResourceAsStream(sqlFileName), "UTF-8").useDelimiter("\\A").next();

        boolean isList;

        Class c;
        final Class<?> returnType = method.getReturnType();

        if (returnType.equals(List.class)) {
            final Type genericReturnType = method.getGenericReturnType();
            c = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
            isList = true;
        } else {
            c = returnType;
            isList = false;
        }


        try (Connection conn = ds.getConnection()) {

            final NamedParameterStatement namedParameterStatement = new NamedParameterStatement(conn, query);

            int i = 0;
            for (Parameter parameter : method.getParameters()) {
                for (Bind bind : parameter.getAnnotationsByType(Bind.class)) {
                    namedParameterStatement.setObject(bind.value(), args[i]);
                }
                i++;
            }


            final List list = new AutoObject<>(c, namedParameterStatement.executeQuery()).getList();
            return isList ? list : (list.size() > 0 ? list.get(0) : null);
        }
    }
}
