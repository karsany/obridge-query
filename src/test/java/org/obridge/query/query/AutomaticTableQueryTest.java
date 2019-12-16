package org.obridge.query.query;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;
import org.obridge.query.AutoServiceFactory;
import org.obridge.query.util.JsonCollection;

import oracle.jdbc.pool.OracleDataSource;

public class AutomaticTableQueryTest {

    @Test
    public void t1() throws SQLException {

        // Given : datasource inicializálva
        OracleDataSource ds = new OracleDataSource();
        ds.setURL("jdbc:oracle:thin:obridge/obridge@localhost:1521:xe");

        // When : elkérem az factory-tól az interfész implementációját és meghívom a
        // szolgáltatást
        // megjegyzés: ha az ember a beant megcsinálja az interfacehez, akkor ezt is
        // elég @Autowired-del használni
        AllTables ut = AutoServiceFactory.init(ds, AllTables.class);
        final List<AllTables.Table> allTables = ut.getAllTables();

        // Then : A szolgáltatás az elvárt eredményeket produkálja
        // Assert.assertEquals(10, allCustomers.size());
        System.out.println(pretty(new JsonCollection(allTables, "allTables").toJson()));

    }

    @Test
    public void t2() throws SQLException {

        // Given : datasource inicializálva
        OracleDataSource ds = new OracleDataSource();
        ds.setURL("jdbc:oracle:thin:obridge/obridge@localhost:1521:xe");

        // When : elkérem az factory-tól az interfész implementációját és meghívom a
        // szolgáltatást
        // megjegyzés: ha az ember a beant megcsinálja az interfacehez, akkor ezt is
        // elég @Autowired-del használni
        AllTables ut = AutoServiceFactory.init(ds, AllTables.class);
        final List<AllTables.Table> allTables = ut.getAllTablesByOwner("SYSTEM");

        // Then : A szolgáltatás az elvárt eredményeket produkálja
        // Assert.assertEquals(10, allCustomers.size());
        System.out.println(pretty(new JsonCollection(allTables, "allTables").toJson()));

    }

    private String pretty(String ugly) {
        JSONObject json = new JSONObject(ugly); // Convert text to object
        return (json.toString(2)); // Print it with specified indentation
    }

}