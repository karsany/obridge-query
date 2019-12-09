package org.obridge.query;

import oracle.jdbc.pool.OracleDataSource;
import org.junit.Assert;
import org.junit.Test;
import org.obridge.query.conversion.Converters;
import org.obridge.query.conversion.StringPatternToLocalDateConverter;
import org.obridge.query.interfaces.JsonString;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class AutoInterfaceTest {

    @Test
    public void a1() throws SQLException {

        Converters.register(new StringPatternToLocalDateConverter("yyyy-MM-dd"));


        OracleDataSource ods = new OracleDataSource();
        ods.setURL("jdbc:oracle:thin:obridge/obridge@localhost:1521:xe");
        final Connection connection = ods.getConnection();
        final String sql = "Select id,\n" +
                "       'HELLO' || rownum As name,\n" +
                "       Sysdate As current_date, '2011-11-11' from_text_date, \n" +
                "       Cursor (Select q.id || 'ABCD' || rownum As description, rownum as sub_id\n" +
                "                 From dual\n" +
                "               Connect By rownum < 4) As embedded_object\n" +
                "  From (select rownum as id from dual\n" +
                "Connect By rownum < 11) q\n";

        System.out.println(sql);

        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        final ResultSet resultSet = preparedStatement.executeQuery();


        List<Customer> ugyfelek = new AutoObject<>(Customer.class, resultSet).getList();

        final Customer customer = ugyfelek.get(0);
        Assert.assertEquals("HELLO1", customer.getName());
        Assert.assertEquals(Integer.valueOf(1), customer.getId());
        Assert.assertEquals(LocalDate.now(), customer.currentDate());
        Assert.assertEquals(customer.getCurrentDate().toLocalDateTime().toLocalDate(), LocalDate.now());
        Assert.assertTrue(customer.getEmbeddedObject().size() > 0);
        Assert.assertEquals(customer.getEmbeddedObject().get(0).getDescription(), "1ABCD1");
        Assert.assertEquals(customer.fromTextDate(), LocalDate.parse("2011-11-11"));

        System.out.println(ugyfelek);

        System.out.println(customer.toJson());

    }

    public interface Customer extends JsonString {
        Integer getId();

        String getName();

        Timestamp getCurrentDate();

        LocalDate currentDate();

        LocalDate fromTextDate();

        List<Properties> getEmbeddedObject();

    }

    public interface Properties extends JsonString {
        String getDescription();

        Integer getSubId();

    }


}
