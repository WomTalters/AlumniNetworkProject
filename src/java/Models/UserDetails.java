package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;

/**
 * The model used to represent the users details
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
    
    /**
     * gets the users fullname from their username
     * 
     * @param username
     * @param con
     * @return
     * @throws ServletException 
     */
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
    
    /**
     * gets the list of User details
     * 
     * @param con
     * @return
     * @throws ServletException 
     */
    public static ArrayList getUserList(Connection con) throws ServletException{
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM userDetails;");
            ResultSet rs = ps.executeQuery();
            ArrayList<UserDetails> userList = new ArrayList();
            while (rs.next()){
                userList.add(new UserDetails(rs.getString("username"),rs.getString("firstname"),rs.getString("lastname")));
            }
            return userList;
        } catch (SQLException ex) {
            throw new ServletException("user list load problem");
        }  
    }
    
    /**
     * loads a users details
     * 
     * @param username
     * @param con
     * @return
     * @throws ServletException 
     */
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
    
    /**
     * saves the users details
     * 
     * @param con
     * @param isNewUser
     * @throws ServletException 
     */
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
