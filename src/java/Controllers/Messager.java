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
 * The controller used to send other users messages
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
        //the profile that the message was typed in
        String fromProfile = request.getParameter("from");
        
        //if nothing is entered for this parameter it is set to an empty string instead of null
        if (fromProfile == null) {
            fromProfile = "";
        }
        
        // check if the message has the correct length and format
        try {
            InputCheck.checkInput("message", messageText, "[\\w\\s\\.,'!?;:\"]{1,255}");            
        } catch (BadInputException ex) {
            session.setAttribute("error", ex.getMessage());
            response.sendRedirect("Profile" + "?u=" +fromProfile);
            return;
        }
        
        Connection con = DBAccess.getConnection();
        
        //if the user attribute is null (due to timeout etc) then the user need to login again
        if (session.getAttribute("user") == null) {
            response.sendRedirect("StartPage");
            // replyto is the id of the message thread
        } else if (request.getParameter("replyto") == null) {
            
            //if the message is not a reply, make a new message thread
            MessageThread mt = new MessageThread(recipient, ((User)session.getAttribute("user")).getUsername(),UserDetails.getNameFromUsername(recipient, con),UserDetails.getNameFromUsername(((User)session.getAttribute("user")).getUsername(), con));
            mt.saveNew(con);
            //add a new message to the thread
            Message message = new Message(messageText,mt.getMessageThreadId(),((User)session.getAttribute("user")).getUsername(),new Timestamp(System.currentTimeMillis()),UserDetails.getNameFromUsername(((User)session.getAttribute("user")).getUsername(), con));
            MessageThread.saveLatestUpdateTime(message.getMessageThreadId(), con);
            message.save(con);
            response.sendRedirect("Profile" + "?u=" + fromProfile);
        } else {
            //add a new message to the thread
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
