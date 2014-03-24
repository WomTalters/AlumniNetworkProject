
package Models;

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
    
    
    
    
}
