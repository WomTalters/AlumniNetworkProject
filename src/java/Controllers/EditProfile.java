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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                InputCheck.checkInput(0,255,"description",enteredDescription, "[\\w\\s]{0,255}");
            } catch (BadInputException ex) {
                session.setAttribute("error", ex.getMessage());
                response.sendRedirect("Profile");
                return;
            }
            //TODO needs thinking through
            try{
                System.out.println("update");
                Connection con = DBAccess.getConnection();
                User user = (User)session.getAttribute("user");
                
                
                DBAccess.doUpdate("UPDATE users SET firstname='"+enteredFirstname+"', lastname='"+enteredLastname+"',description='"+enteredDescription+"' WHERE username='"+user.getUsername()+"'", con);
                
                
                user.setDescription(enteredDescription);
                user.setFirstname(enteredFirstname);
                user.setLastname(enteredLastname);
                response.sendRedirect("Profile");
                
            } catch (SQLException ex) {
                System.out.println("Bad query");
                session.setAttribute("error", "Could not do query");
                response.sendRedirect("Profile");
            } catch (Exception ex) {
                System.out.println("Something went wrong");
                ex.printStackTrace();
                session.setAttribute("error", "Something went Wrong :(");
                response.sendRedirect("Profile");
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
