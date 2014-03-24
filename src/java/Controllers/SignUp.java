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
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
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
        

        //try to update the database with the new username and password
        try {
            Connection con = DBAccess.getConnection();
            

            ResultSet r = DBAccess.doQuery("SELECT COUNT (username) FROM users WHERE username='" + enteredUsername + "';", con);
            r.next();
            //if the username already exists the update proccess is cancelled and the user is returned to the startpage with an error message.
            if (r.getInt("count") == 1) {
                System.out.println("This username is taken");
                session.setAttribute("error", "This username is already taken");
                response.sendRedirect("StartPage");

            } else {
                //TODO is this done in model?
                DBAccess.doUpdate("INSERT INTO users (username, password, firstname, lastname) VALUES ('" + enteredUsername + "','"
                        + enteredPassword + "', '" + enteredFirstname + "', '" + enteredLastname + "');", con);
                User user = new User(enteredUsername, enteredFirstname, enteredLastname);

                session.setAttribute("user", user);

                //Makes the browser redirect to the profile servlet. This is done to make the url reflect the page it is on and to stop form resubmission.
                response.sendRedirect("Profile");
            }
            
            con.close();

        } catch (SQLException ex) {
            //if a query could not be excuted this error message is show on the start page
            System.out.println("Could not do querry");
            ex.printStackTrace();
            session.setAttribute("error", "Could not do querry");
            response.sendRedirect("StartPage");

        } catch (Exception ex) {
            //if the database connection could not be made the user is redirected to the startPage which shows an error message
            System.out.println("Could not connect to the database");
            session.setAttribute("error", "Could not connect to the database");
            response.sendRedirect("StartPage");

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
