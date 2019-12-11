package org.obridge.query;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.Test;
import org.obridge.query.util.JsonList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AutoObjectTest {

    @Test
    public void getList() throws SQLException {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test");
        ds.setUser("sa");
        ds.setPassword("sa");

        final Connection connection = ds.getConnection();
        final PreparedStatement preparedStatement = connection.prepareStatement("select table_name description, row_count_estimate as sub_id from information_schema.tables");


        final List<SimpleTestService.Properties> list = new AutoObject<>(SimpleTestService.Properties.class, preparedStatement.executeQuery()).getList();

        System.out.println(new JsonList(list).toJson());

    }
}