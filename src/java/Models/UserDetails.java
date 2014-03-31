package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;

/**
 *
 * @author Tom
 */
public class UserDetails {

    private String username;
    private String firstname;
    private String lastname;
    private String description;

    public UserDetails() {
    }


    public UserDetails(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    
    public UserDetails(String username, String firstname, String lastname, String description) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.description = description;
    }
    
    

    public UserDetails(ResultSet rs) throws SQLException {
        rs.next();
        username = rs.getString("username");
        firstname = rs.getString("firstname");
        lastname = rs.getString("lastname");
        description = rs.getString("description");
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public static String getNameFromUsername(String username, Connection con) throws ServletException{
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM userdetails WHERE username=?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("firstname") + " " + rs.getString("lastname");
        } catch (Exception ex) {
            throw new ServletException("Could not load UserDetails");
        }
       
    }
    
    public static UserDetails load(String username,Connection con) throws ServletException {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM userdetails WHERE username=?;");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new UserDetails(rs.getString("username"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("description"));
        } catch (Exception ex) {
            throw new ServletException("Could not load UserDetails");
        }
    }
    
    
    public void save(Connection con, boolean isNewUser) throws ServletException{
        try {            
            if (isNewUser){
                PreparedStatement ps = con.prepareStatement("INSERT INTO userdetails VALUES(?,?,?);");
                ps.setString(1, username);
                ps.setString(2, firstname);
                ps.setString(3, lastname);
                ps.executeUpdate();
            }else{
                PreparedStatement ps = con.prepareStatement("UPDATE userdetails SET firstname=?,lastname=?,description=? WHERE username=?;");
                ps.setString(1, firstname);
                ps.setString(2, lastname);
                ps.setString(3, description);
                ps.setString(4, username);
                ps.executeUpdate();
            }
            

        } catch (Exception ex) {
            throw new ServletException("Save problem");
        } 
        
    }

}
