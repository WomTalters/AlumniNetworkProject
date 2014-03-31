/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controllers;

import Database.DBAccess;
import General.BadInputException;
import General.InputCheck;
import Models.Message;
import Models.MessageThread;
import Models.User;
import Models.UserDetails;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
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
@WebServlet(name = "Messager", urlPatterns = {"/Messager"})
public class Messager extends HttpServlet {

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
        String messageText = request.getParameter("messagetext");
        String recipient = request.getParameter("recipient");
        
        String fromProfile = request.getParameter("from");
        
        if (fromProfile == null) {
            fromProfile = "";
        }
        
        try {
            InputCheck.checkInput("message", messageText, "[\\w\\s\\.,!?;:\"]{1,255}");            
        } catch (BadInputException ex) {
            session.setAttribute("error", ex.getMessage());
            response.sendRedirect("Profile" + "?u=" +fromProfile);
            return;
        }
        
        Connection con = DBAccess.getConnection();
        
        if (session.getAttribute("user") == null) {
            response.sendRedirect("StartPage");
        } else if (request.getParameter("replyto") == null) {
            MessageThread mt = new MessageThread(recipient, ((User)session.getAttribute("user")).getUsername(),UserDetails.getNameFromUsername(recipient, con),UserDetails.getNameFromUsername(((User)session.getAttribute("user")).getUsername(), con));
            mt.saveNew(con);            
            Message message = new Message(messageText,mt.getMessageThreadId(),((User)session.getAttribute("user")).getUsername(),new Timestamp(System.currentTimeMillis()),UserDetails.getNameFromUsername(((User)session.getAttribute("user")).getUsername(), con));
            MessageThread.saveLatestUpdateTime(message.getMessageThreadId(), con);
            message.save(con);
            response.sendRedirect("Profile" + "?u=" + fromProfile);
        } else {
            Message message = new Message(messageText,Integer.parseInt(request.getParameter("replyto")),((User)session.getAttribute("user")).getUsername(),new Timestamp(System.currentTimeMillis()),UserDetails.getNameFromUsername(((User)session.getAttribute("user")).getUsername(), con));
            MessageThread.saveLatestUpdateTime(message.getMessageThreadId(), con);
            message.save(con);
            response.sendRedirect("Profile" + "?u=" + fromProfile);
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
