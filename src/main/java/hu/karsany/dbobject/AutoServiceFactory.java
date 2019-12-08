package hu.karsany.dbobject;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

public class AutoServiceFactory {

    public static <T> T init(DataSource ds, Class<T> clazz) {
        return (T)
                Proxy
                        .newProxyInstance(
                                AutoServiceFactory.class.getClassLoader(),
                                new Class[]{clazz},
                                new AutoServiceInvocationHandler(ds, clazz)
                        );
    }

}
