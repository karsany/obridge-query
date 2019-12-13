package org.obridge.query.interfaces;

import java.io.IOException;
import java.lang.reflect.Method;

public interface Query {

    String sql();

    String sql(Method m) throws IOException;

}
