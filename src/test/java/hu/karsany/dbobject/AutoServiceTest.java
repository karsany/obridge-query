package hu.karsany.dbobject;

import hu.karsany.dbobject.conversion.Converters;
import hu.karsany.dbobject.conversion.StringPatternToLocalDateConverter;
import oracle.jdbc.pool.OracleDataSource;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class AutoServiceTest {

    @Test
    public void a1() throws SQLException {

        // Given : datasource inicializálva, egyedi típuskonverzió regisztrálva
        //         megjegyzés: nyilván jöhet beanből, stb.
        Converters.register(new StringPatternToLocalDateConverter("yyyy-MM-dd"));
        OracleDataSource ds = new OracleDataSource();
        ds.setURL("jdbc:oracle:thin:obridge/obridge@localhost:1521:xe");

        // When : elkérem az factory-tól az interfész implementációját és meghívom a szolgáltatást
        //        megjegyzés: ha az ember a beant megcsinálja az interfacehez, akkor ezt is elég @Autowired-del használni
        SimpleTestService sts = AutoServiceFactory.init(ds, SimpleTestService.class);
        final List<SimpleTestService.Customer> allCustomers = sts.getAllCustomers();

        // Then : A szolgáltatás az elvárt eredményeket produkálja
        Assert.assertEquals(10, allCustomers.size());


        System.out.println(pretty(new JsonList(allCustomers, "customers").toJson()));

    }

    private String pretty(String ugly) {
        JSONObject json = new JSONObject(ugly); // Convert text to object
        return (json.toString(2)); // Print it with specified indentation
    }
}
