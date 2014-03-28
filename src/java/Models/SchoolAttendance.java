package Models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;

/**
 *
 * @author Tom
 */
public class SchoolAttendance {

    private String username;
    private String schoolname;
    private int startDate;
    private int finishDate;

    public SchoolAttendance() {
    }

    public SchoolAttendance(String username, String schoolname, int startDate, int finishDate) {
        this.username = username;
        this.schoolname = schoolname;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public SchoolAttendance(String username, String schoolname) {
        this.username = username;
        this.schoolname = schoolname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(int finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isAvailable(Connection con) throws ServletException {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT (username) FROM schoolAttendance WHERE username=? AND schoolname=?;");
            ps.setString(1, username);
            ps.setString(2, schoolname);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return (rs.getInt("count") == 0);

        } catch (Exception ex) {
            throw new ServletException("availblilty checking problem");
        }
    }
    
    public static boolean isAvailable(String username,String schoolname,Connection con) throws ServletException {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT COUNT (username) FROM schoolAttendance WHERE username=? AND schoolname=?;");
            ps.setString(1, username);
            ps.setString(2, schoolname);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return (rs.getInt("count") == 0);

        } catch (Exception ex) {
            throw new ServletException("availblilty checking problem");
        }
    }

    public void save(Connection con, boolean isNewEntry) throws ServletException {
        try {
            if (isNewEntry) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO schoolAttendance VALUES(?,?,?,?);");
                ps.setString(1, schoolname);
                ps.setString(2, username);
                ps.setInt(3, startDate);
                ps.setInt(4, finishDate);
                ps.executeUpdate();
            } else {
                PreparedStatement ps = con.prepareStatement("UPDATE schoolAttendance SET startdate=?,finishdate=? WHERE username=? AND schoolname=?;");
                ps.setInt(1, startDate);
                ps.setInt(2, finishDate);
                ps.setString(3, username);
                ps.setString(3, schoolname);
                ps.executeUpdate();
            }

        } catch (Exception ex) {
            throw new ServletException("Save problem");
        }

    }
    
    public static SchoolAttendance load(String username,String schoolname,Connection con) throws ServletException {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM schoolAttendance WHERE username=? AND schoolname=?;");
            System.out.println(username);
            System.out.println(schoolname);
            ps.setString(1, username);
            ps.setString(2, schoolname);
            ResultSet rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.getString("username"));
            //return new SchoolAttendance(rs.getString("username"),rs.getString("schoolname"),rs.getInt("startdate"),rs.getInt("finishdate"));
            return new SchoolAttendance();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException("Could not load UserDetails");
        }
    }

}
