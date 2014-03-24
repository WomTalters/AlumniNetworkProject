/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBAccess;
import General.BadInputException;
import General.InputCheck;
import Models.User;
import Models.UserDetails;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "Ctrl_signUp", urlPatterns = {"/Ctrl_signUp"})
public class SignUp extends HttpServlet {

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

        String enteredUsername = request.getParameter("username");
        String enteredPassword = request.getParameter("password");
        String enteredFirstname = request.getParameter("firstname");
        String enteredLastname = request.getParameter("lastname");

        //using regular expressions to make sure the entered feilds are the right length and format. 
        try {
            InputCheck.checkInput(4, 25, "username", enteredUsername, "\\w");
            InputCheck.checkInput(4, 25, "password", enteredPassword, "\\w");
            InputCheck.checkInput(1, 25, "firstname", enteredFirstname, "\\w");
            InputCheck.checkInput(1, 25, "lastname", enteredLastname, "\\w");
        } catch (BadInputException ex) {
            session.setAttribute("error", ex.getMessage());
            response.sendRedirect("StartPage");
            return;
        }
        User user = new User(enteredUsername, enteredPassword);
        UserDetails userDetails = new UserDetails(enteredUsername, enteredFirstname, enteredLastname);
        Connection con = DBAccess.getConnection();
        
        //try to update the database with the new username and password
        if (user.isAvailable(con)) {
            //TODO user still saves if there is an error with the saving of userdetails
            user.save(user, con);
            userDetails.save(userDetails,con,true);
            session.setAttribute("user", user);
            response.sendRedirect("Profile");
            
        } else {
            session.setAttribute("error", "This username is already taken");
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
