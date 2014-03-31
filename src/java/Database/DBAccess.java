package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;

/**
 *
 * @author Tom
 */
public class DBAccess {

    
    
    public static Connection getConnection()
            throws ServletException {
        try {
            String jdbcDriver = "org.postgresql.Driver";
            Class.forName(jdbcDriver);
            String jdbcUrl = "jdbc:postgresql:postgres";
            String username = "postgres";
            String password = "hello";
            
            return DriverManager.getConnection(jdbcUrl, username, password);
        } catch (Exception ex) {
            throw new ServletException("Could not connect to the database");
        }
    }
    
    public static void closeConnection(Connection con) throws ServletException {
        try {
            con.close();
        } catch (Exception ex){
            throw new ServletException("Could not close connection");
        }
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
