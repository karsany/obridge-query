# OBridge Query

OBridge Query is a java proxy based SQL ResultSet to Java object processor. You don't need to write code, unless the SQL query, the service interface and the data interface.

## How to install in your project?

Maven repo:

    <repositories>
        <repository>
            <id>obridge-repo</id>
            <name>OBridge Repository</name>
            <url>https://karsany.dynv6.net/maven/</url>
        </repository>
    </repositories>

Maven dependency:

        <dependency>
            <groupId>org.obridge</groupId>
            <artifactId>obridge-query</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>

## How to use it?

Create the data model: **UserTable.java**

    public interface UserTable extends JsonString {
        String owner();
        String tableName();
    }

Create the service interface: **UserTableService.java**

    public interface UserTableService {
        List<UserTable> getUserTables();
    }

Create the SQL file for every Service method, put it in the same package as the service interface, but in the resources. **UserTableService_GetUserTables.sql**

    select * from user_tables

Initialize the service class:

    UserTableService s = AutoServiceFactory.init(dataSource, UserTableService.class);

Use the service class:

    final List<UserTable> userTables = s.getUserTables();

If you want to produce json, then use the ```.toJson()``` method on the objects, or ```new JsonList(listObject, "rootName").toJson()``` on lists.

## How to contribute?

Nothing special yet.