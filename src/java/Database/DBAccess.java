package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletException;

/**
 * A class used to create and close connections to the database
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
            String password = "";
            
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
    

}
