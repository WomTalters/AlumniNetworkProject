package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Tom
 */
public class DBAccess {

    public static Connection getConnection()
            throws ClassNotFoundException, SQLException {

        String jdbcDriver = "org.postgresql.Driver";
        Class.forName(jdbcDriver);
        String jdbcUrl = "jdbc:postgresql:postgres";
        String username = "postgres";
        String password = "hello";

        return (DriverManager.getConnection(jdbcUrl, username, password));
    }

    public static void doUpdate(String sql, Connection con) throws SQLException{
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }
    
    public static ResultSet doQuery(String sql, Connection con) throws SQLException{
        Statement statement = con.createStatement();
        return statement.executeQuery(sql);
    }
}
