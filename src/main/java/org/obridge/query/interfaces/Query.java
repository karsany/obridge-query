package org.obridge.query.interfaces;

import java.lang.reflect.Method;

public interface Query {

    String sql();

    String sql(Method m);

}
