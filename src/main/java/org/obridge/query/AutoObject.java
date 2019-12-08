package org.obridge.query;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AutoObject<T> {

    private final Class<T> clazz;
    private final ResultSet resultSet;

    private List<T> list = null;
    private Set<String> columnNames = new LinkedHashSet<>();
    private Map<Method, String> methodColumnNameMap = new LinkedHashMap<>();


    public AutoObject(Class<T> clazz, ResultSet resultSet) {
        this.clazz = clazz;
        this.resultSet = resultSet;
    }

    public List<T> getList() {

        try {

            if (list == null) {

                initColumnNameSet();
                mapMethodsToColumnNames();
                list = new ArrayList<>();

                while (resultSet.next()) {

                    T row = new ResultSetExtractor<T>(clazz).getObject(methodColumnNameMap, resultSet);

                    this.list.add(row);

                }
            }

            return this.list;
        } catch (SQLException e) {
            this.list = null;
            throw new RuntimeException(e);
        }
    }

    private void mapMethodsToColumnNames() throws SQLException {
        this.methodColumnNameMap.clear();

        for (Method method : this.clazz.getMethods()) {

            String calColName = method.getName();

            if (columnNames.contains(calColName)) {
                methodColumnNameMap.put(method, calColName);
            }

            calColName = calColName.toUpperCase();
            if (columnNames.contains(calColName)) {
                methodColumnNameMap.put(method, calColName);
            }

            calColName = StringHelper.toOracleName(method.getName());
            if (columnNames.contains(calColName)) {
                methodColumnNameMap.put(method, calColName);
            }

            if (method.getName().startsWith("get")) {
                calColName = method.getName().substring(3);
                if (columnNames.contains(calColName)) {
                    methodColumnNameMap.put(method, calColName);
                }

                calColName = calColName.toUpperCase();
                if (columnNames.contains(calColName)) {
                    methodColumnNameMap.put(method, calColName);
                }

                calColName = StringHelper.toOracleName(method.getName().substring(3));
                if (columnNames.contains(calColName)) {
                    methodColumnNameMap.put(method, calColName);
                }

            }

        }


    }

    private void initColumnNameSet() throws SQLException {

        this.columnNames.clear();

        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            columnNames.add(resultSet.getMetaData().getColumnName(i));
        }
    }
}
