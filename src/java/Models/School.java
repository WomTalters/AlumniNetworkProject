
package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;

/**
 *
 * @author Tom
 */
public class School {

    private String schoolname;
    private String location;
    private String webSiteAddress;

    public School(String schoolname, String location, String webSiteAddress) {
        this.schoolname = schoolname;
        this.location = location;
        this.webSiteAddress = webSiteAddress;
    }
    
    public School(String schoolname) {
        this.schoolname = schoolname;
    }
    
    

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }
    
    public static ArrayList getSchoolList(Connection con) throws ServletException{
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM schools;");
            ResultSet rs = ps.executeQuery();
            ArrayList<School> schoolList = new ArrayList();
            while (rs.next()){
                schoolList.add(new School(rs.getString("schoolname")));
            }
            return schoolList;
        } catch (SQLException ex) {
            throw new ServletException("school list load problem");
        }  
    }
    
    public static School load(String schoolname, Connection con) throws ServletException{
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM schools WHERE schoolname=?;");
            ps.setString(1, schoolname);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return new School(rs.getString("schoolname"),rs.getString("location"),rs.getString("websiteaddress"));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException("Could not load School");
            
        }
    }
    
    
    
}
