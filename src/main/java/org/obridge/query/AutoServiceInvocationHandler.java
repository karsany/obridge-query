package org.obridge.query;

import org.obridge.query.annotation.Bind;
import org.obridge.query.annotation.QuerySource;
import org.obridge.query.util.NamedParameterStatement;

import javax.sql.DataSource;
import java.lang.reflect.*;
import java.sql.Connection;
import java.util.List;

class AutoServiceInvocationHandler implements InvocationHandler {

    private final DataSource ds;
    private final Class clazz;

    public AutoServiceInvocationHandler(DataSource ds, Class clazz) {
        this.ds = ds;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        QuerySource annotation = method.getAnnotation(QuerySource.class);

        String query;
        if (annotation == null) {
            query = new ResourceFileQuery().sql(method);
        } else {
            query = annotation.value().newInstance().sql(method);
        }

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
