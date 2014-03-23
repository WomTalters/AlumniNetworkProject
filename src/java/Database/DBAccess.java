package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tom
 */
public class DBAccess {

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {

        String jdbcDriver = "org.postgresql.Driver";
        Class.forName(jdbcDriver);
        String jdbcUrl = "jdbc:postgresql:studentdb";
        String username = "dbuser";
        String password = "dbpassword";

        return (DriverManager.getConnection(jdbcUrl, username, password));
    }
}
