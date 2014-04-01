package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;

/**
 * The model used to represent the table that stores users and their passwords
 * 
 * @author Tom
 */
public class User {

    private String username;
    private String password;

    public User() {
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * checks if the username/password combination exists
     * 
     * @param con
     * @return
     * @throws ServletException 
     */
    public boolean isValid(Connection con) throws ServletException{
        try {
            
            PreparedStatement ps = con.prepareStatement("SELECT COUNT (username) FROM users WHERE username=? AND password=?;");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            return (rs.getInt("count") == 1);

        } catch (Exception ex) {
            throw new ServletException("validity checking problem");
        } 
    }

    /**
     * checks if the username exists
     * 
     * @param con
     * @return
     * @throws ServletException 
     */
    public boolean isAvailable(Connection con) throws ServletException{
        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT (username) FROM users WHERE username=?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return (rs.getInt("count") == 0);

        } catch (Exception ex) {
            throw new ServletException("availblilty checking problem");
        } 
    }
    
    /**
     * saves the user informations
     * 
     * @param user
     * @param con
     * @throws ServletException 
     */
    public void save(User user, Connection con) throws ServletException {
        try {

            PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES(?,?);");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();

        } catch (Exception ex) {
            throw new ServletException("Save problem");
        }

    }

}
