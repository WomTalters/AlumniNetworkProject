/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBAccess;
import General.InputCheck;
import Models.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@WebServlet(name = "Profile", urlPatterns = {"/Profile"})
public class Profile extends HttpServlet {

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
            System.out.println("yay");
        } else {
            try {
                Connection con = DBAccess.getConnection();
                //if the url doesn't not contain a username the users profile is loaded 
                User profileUser;
                String requestedProfile = request.getParameter("u");
                
                if (requestedProfile == null) {
                    profileUser = (User) session.getAttribute("user");
                    if (request.getAttribute("edit")=="true"){
                        request.setAttribute("profileUser", profileUser);
                        request.getRequestDispatcher("profile.jsp").forward(request, response);
                    }
                    
                    
                } else {

                    //if the url contain a username the profile page loaded will belong to that user, unless the username is incorrect and then the users profile is loaded instead

                    if (requestedProfile.matches("\\w{4,25}")) {
                        System.out.println(requestedProfile);
                        profileUser = new User(DBAccess.doQuery("SELECT * FROM users WHERE username='" + requestedProfile + "';", con));
                        
                    } else {
                        profileUser = (User) session.getAttribute("user");
                    }
                }
                
                request.setAttribute("profileUser", profileUser);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Bad query");
                session.setAttribute("error", "Could not do query");
                response.sendRedirect("StartPage");
            } catch (Exception ex) {                
                System.out.println("Something went wrong");
                ex.printStackTrace();
                session.setAttribute("error", "Something went Wrong :(");
                response.sendRedirect("StartPage");
            }
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
