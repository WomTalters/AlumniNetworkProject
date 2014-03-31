/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBAccess;
import Models.School;
import Models.SchoolAttendance;
import Models.User;
import Models.UserDetails;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tom
 */
@WebServlet(name = "SchoolPage_1", urlPatterns = {"/SchoolPage_1"})
public class SchoolPage extends HttpServlet {

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
            Connection con = DBAccess.getConnection();
            //if the url doesn't not contain a username the users profile is loaded 
            School school;
            String requestedSchool = request.getParameter("s");
            if (requestedSchool == null) {
                
                response.sendRedirect("Profile");
                //TODO eventually have a school search page here perhaps
            } else {
                //TODO schoolnames that are the right format but don't exist need dealing with
                //if the url contain a schoolname the  schoolPage loaded will belong to that school, unless the schoolname is incorrect and then the users profile is loaded instead
                if (requestedSchool.matches("[\\w ]{4,25}")) {
                    school = School.load(requestedSchool, con);
                    SchoolAttendance schAtt = null;
                    if (!SchoolAttendance.isAvailable(((User) session.getAttribute("user")).getUsername(), requestedSchool, con)){
                        schAtt = SchoolAttendance.load(((User) session.getAttribute("user")).getUsername(),requestedSchool , con); 
                        
                    }else{
                        
                        schAtt = new SchoolAttendance(((User)session.getAttribute("user")).getUsername(),school.getSchoolname()); 
                    }
                    
                    ArrayList<UserDetails> profiles = SchoolAttendance.getAlumniList(requestedSchool, con);
                    request.setAttribute("profiles", profiles);
                    request.setAttribute("schatt", schAtt);
                    request.setAttribute("school", school);
                    

                    request.getRequestDispatcher("schoolPage.jsp").forward(request, response);
                    request.getSession().removeAttribute("error");

                } else {
                   
                    response.sendRedirect("Profile");
                    request.getSession().removeAttribute("error");
                }
            }

            DBAccess.closeConnection(con);
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
