package hu.karsany.dbobject;

import hu.karsany.dbobject.conversion.Converter;
import hu.karsany.dbobject.conversion.Converters;
import oracle.jdbc.pool.OracleDataSource;
import oracle.sql.CLOB;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class AutoInterface2Test {

    static {
        Converters.register(new Converter<CLOB, String>() {

            @Override
            public String valueOf(CLOB from) {
                try {
                    return from.stringValue();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Converters.register(new Converter<Timestamp, Date>() {
            @Override
            public Date valueOf(Timestamp from) {
                return Date.valueOf(from.toLocalDateTime().toLocalDate());
            }
        });


        Converters.register(new Converter<Timestamp, LocalDateTime>() {
            @Override
            public LocalDateTime valueOf(Timestamp from) {
                return from.toLocalDateTime();
            }
        });

    }

    @Test
    public void a1() throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL("jdbc:oracle:thin:obridge/obridge@localhost:1521:xe");
        final Connection connection = ods.getConnection();
        final PreparedStatement preparedStatement = connection.prepareStatement("Select 1 As n,\n" +
                "       1.01 As nmb,\n" +
                "       cast(sqrt(2) as binary_double) As dbl,\n" +
                "       Cast(2 As Integer),\n" +
                "       'HelloWorldŐŐŐŐŰŰŰ' As vch,\n" +
                "       Cast('ŐŐŐŐŰŰŰŰ' As Nvarchar2(10)) As nvch,\n" +
                "       Cast('XXX' As Char(10)) As ch,\n" +
                "       trunc(Sysdate) As dt,\n" +
                "       Sysdate As dttm,\n" +
                "       systimestamp As tsmp,\n" +
                "       to_clob('Hello') As cl\n" +
                "  From dual");
        final ResultSet resultSet = preparedStatement.executeQuery();


        List<Types> types = new AutoObject<>(Types.class, resultSet).getList();
        System.out.println(types);
    }

    private interface Types {
        Integer n();

        BigDecimal NMB();

        Double dbl();

        Integer INTG();

        String VCH();

        String NVCH();

        String CH();

        Date DT();

        LocalDateTime DTTM();

        String TSMP();

        String CL();

    }
}
