/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBAccess;
import General.InputCheck;
import Models.UserDetails;
import java.io.IOException;
import General.BadInputException;
import Models.User;
import java.sql.Connection;
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
@WebServlet(name = "Ctrl_login", urlPatterns = {"/Ctrl_login"})
public class Login extends HttpServlet {

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

        HttpSession session = request.getSession(true);

        if (session.getAttribute("user") != null) {
            response.sendRedirect("Profile");
            return;
        }

        String enteredUsername = request.getParameter("username");
        String enteredPassword = request.getParameter("password");

        //using regular expressions to make sure the entered username and password are the right length and format.
        try {
            InputCheck.checkInput(4, 25, "username", enteredUsername, "\\w");
            InputCheck.checkInput(4, 25, "password", enteredPassword, "\\w");
        } catch (BadInputException ex) {
            session.setAttribute("error", ex.getMessage());
            response.sendRedirect("StartPage");
            return;
        }

        Connection con = DBAccess.getConnection();
        
        User user = new User(enteredUsername, enteredPassword);

        if (user.isValid(con)) {
            session.setAttribute("user", user);
            response.sendRedirect("Profile");
        } else {
            session.setAttribute("error", "The username or password is incorrect");
            response.sendRedirect("StartPage");
        }    
        DBAccess.closeConnection(con);
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
