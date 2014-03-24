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
@WebServlet(name = "EditProfile", urlPatterns = {"/EditProfile"})
public class EditProfile extends HttpServlet {

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

        if (session.getAttribute("user") == null) {
            response.sendRedirect("StartPage");
        } else {
            String enteredFirstname = request.getParameter("firstname");
            String enteredLastname = request.getParameter("lastname");
            String enteredDescription = request.getParameter("description");

            //using regular expressions to make sure the entered feilds are the right length and format. 
            try {
                InputCheck.checkInput(1, 25, "firstname", enteredFirstname, "\\w");
                InputCheck.checkInput(1, 25, "lastname", enteredLastname, "\\w");
                //TODO description should be able to accept a greater range of characters
                InputCheck.checkInput(0, 255, "description", enteredDescription, "[\\w\\s\\.,!?;:\"]{0,255}");
            } catch (BadInputException ex) {
                session.setAttribute("error", ex.getMessage());
                response.sendRedirect("Profile");
                return;
            }

            Connection con = DBAccess.getConnection();
            UserDetails userDetails = new UserDetails(((User) session.getAttribute("user")).getUsername(), enteredFirstname, enteredLastname, enteredDescription);

            userDetails.save(userDetails, con, false);
            response.sendRedirect("Profile");

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
