package Models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tom
 */
public class User {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String description;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String firstname, String lastname) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(ResultSet rs) throws SQLException {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
