package hu.karsany.dbobject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface SimpleTestService {

    List<Customer> getAllCustomers();

    interface Customer extends JsonString {
        Integer getId();

        String getName();

        LocalDate currentDate();

        LocalDate fromTextDate();

        List<AutoInterfaceTest.Properties> getEmbeddedObject();

    }

    interface Properties extends JsonString {
        String getDescription();

        Integer getSubId();

    }

}
