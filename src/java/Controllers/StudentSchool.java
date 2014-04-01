package Controllers;

import Database.DBAccess;
import General.BadInputException;
import General.InputCheck;
import Models.SchoolAttendance;
import Models.User;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The controller used add and update info about which schools a user went to.
 * 
 * @author Tom
 */
@WebServlet(name = "StudentSchool", urlPatterns = {"/StudentSchool"})
public class StudentSchool extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        //if the user attribute is null (due to timeout etc) then the user need to login again
        if (session.getAttribute("user") == null) {
            response.sendRedirect("StartPage");
        } else {

            String username = ((User) session.getAttribute("user")).getUsername();
            String schoolname = request.getParameter("schoolname");
            int startDate = Integer.parseInt(request.getParameter("startyear"));
            int finishDate = Integer.parseInt(request.getParameter("finishyear"));
            
            //check the parameters have the correct format and length
            try {
                InputCheck.checkInput(schoolname, "[\\w ]{4,25}");
                InputCheck.checkInput(request.getParameter("startyear"), "\\d{4}");
                InputCheck.checkInput(request.getParameter("finishyear"), "\\d{4}");
            } catch (BadInputException ex) {
                session.setAttribute("error", ex.getMessage());
                response.sendRedirect("Profile");
                return;
            }
            
            // update the attendance
            SchoolAttendance schAtt = new SchoolAttendance(username, schoolname, startDate, finishDate);
            Connection con = DBAccess.getConnection();
            schAtt.save(con, schAtt.isAvailable(con));
            DBAccess.closeConnection(con);
            response.sendRedirect("SchoolPage?s="+ schoolname);
            
            
            
            

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
