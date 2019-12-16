package org.obridge.query;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.obridge.query.exception.MappingException;
import org.obridge.query.util.StringHelper;

public class AutoObject<T> {

    private final Class<T> clazz;
    private final ResultSet resultSet;

    private List<T> list = null;
    private Set<String> columnNames = new LinkedHashSet<>();
    private Map<Method, String> methodColumnNameMap = new LinkedHashMap<>();
    private ResultSetRowMapper<T> resultSetRowMapper;

    public AutoObject(Class<T> clazz, ResultSet resultSet) {
        this.clazz = clazz;
        this.resultSet = resultSet;
        this.resultSetRowMapper = new ResultSetRowMapper<>(this.clazz);
    }

    public List<T> getList() {
        try {
            if (this.list == null) {
                this.initColumnNameSet();
                this.mapMethodsToColumnNames();
                this.list = new ArrayList<>();

                while (this.resultSet.next()) {
                    T row = this.resultSetRowMapper.getObject(this.methodColumnNameMap, this.resultSet);
                    this.list.add(row);
                }
            }
            return this.list;
        } catch (SQLException e) {
            this.list = null;
            throw new MappingException(e);
        }
    }

    private void mapMethodsToColumnNames() {
        this.methodColumnNameMap.clear();

        for (Method method : this.clazz.getMethods()) {

            String calColName = method.getName();

            if (this.columnNames.contains(calColName)) {
                this.methodColumnNameMap.put(method, calColName);
            }

            calColName = calColName.toUpperCase();
            if (this.columnNames.contains(calColName)) {
                this.methodColumnNameMap.put(method, calColName);
            }

            calColName = StringHelper.toOracleName(method.getName());
            if (this.columnNames.contains(calColName)) {
                this.methodColumnNameMap.put(method, calColName);
            }

            if (method.getName()
                      .startsWith("get")) {
                calColName = method.getName()
                                   .substring(3);
                if (this.columnNames.contains(calColName)) {
                    this.methodColumnNameMap.put(method, calColName);
                }

                calColName = calColName.toUpperCase();
                if (this.columnNames.contains(calColName)) {
                    this.methodColumnNameMap.put(method, calColName);
                }

                calColName = StringHelper.toOracleName(method.getName()
                                                             .substring(3));
                if (this.columnNames.contains(calColName)) {
                    this.methodColumnNameMap.put(method, calColName);
                }

            }

        }

    }

    private void initColumnNameSet() throws SQLException {
        this.columnNames.clear();

        for (int i = 1; i <= this.resultSet.getMetaData()
                                           .getColumnCount(); i++) {
            this.columnNames.add(this.resultSet.getMetaData()
                                               .getColumnLabel(i));
        }

    }
}
