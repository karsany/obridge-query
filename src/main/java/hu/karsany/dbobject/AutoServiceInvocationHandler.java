package hu.karsany.dbobject;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AutoServiceInvocationHandler implements InvocationHandler {

    private final DataSource ds;
    private final Class clazz;

    public AutoServiceInvocationHandler(DataSource ds, Class clazz) {
        this.ds = ds;
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String sqlFileName = clazz.getSimpleName() + "_" + method.getName().substring(0, 1).toUpperCase() + method.getName().substring(1) + ".sql";
        final String query = new Scanner(this.getClass().getResourceAsStream(sqlFileName), "UTF-8").useDelimiter("\\A").next();

        Class c = (Class) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                return new AutoObject<>(c, ps.executeQuery()).getList();
            }
        }

    }
}
