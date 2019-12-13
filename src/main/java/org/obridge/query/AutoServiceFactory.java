package org.obridge.query;

import java.lang.reflect.Proxy;

import javax.sql.DataSource;

public final class AutoServiceFactory {

    private AutoServiceFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T init(DataSource ds, Class<T> clazz) {
        return (T) Proxy.newProxyInstance(AutoServiceFactory.class.getClassLoader(),
                                          new Class[] { clazz },
                                          new AutoServiceInvocationHandler(ds));
    }

}
