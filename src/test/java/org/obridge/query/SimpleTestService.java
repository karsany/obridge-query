package org.obridge.query;

import org.obridge.query.annotation.Bind;
import org.obridge.query.interfaces.JsonString;

import java.time.LocalDate;
import java.util.List;

public interface SimpleTestService {

    List<Customer> getAllCustomers();

    List<Customer> getAllCustomersNameLike(@Bind("pattern") String name, @Bind("test") Integer tst);

    Customer getCustomerById(@Bind("id") Integer id);

    interface Customer extends JsonString {
        Integer getId();

        String getName();

        LocalDate currentDate();

        LocalDate fromTextDate();

        List<Properties> getEmbeddedObject();

    }

    interface Properties extends JsonString {
        String getDescription();

        Integer getSubId();
    }

}