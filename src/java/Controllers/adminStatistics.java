/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBAccess;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ckk13dzu
 */
@WebServlet(name = "adminStatistics", urlPatterns = {"/adminStatistics"})
public class adminStatistics extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        
        Connection connection = DBAccess.getConnection();
        try {
            Class.forName("org.postgresql.Driver");
            
            String sqlStatement;
            ResultSet resultSet;
            Statement statement = connection.createStatement();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet adminStatistics</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            boolean valid=false;
            
            sqlStatement="SELECT * FROM admin;";
            resultSet = statement.executeQuery(sqlStatement);            
            
            resultSet.next();
          
            if (resultSet.getString("username").equals(username) && resultSet.getString("password").equals(password)){
                valid=true;
            }
            
            
            if (valid==true){    
            }else{
                session.setAttribute("error", "The username or password is incorrect");
                response.sendRedirect("StartPage");
                return;
            }
            
            sqlStatement="SELECT COUNT(username) FROM users;";
            resultSet = statement.executeQuery(sqlStatement);            
            while(resultSet.next())
            {
                out.println("Number of users: "+resultSet.getInt("count")+"<br><br>");
            }
            
            sqlStatement="SELECT COUNT(schoolname) FROM schools;";
            resultSet = statement.executeQuery(sqlStatement);
            while(resultSet.next())
            {
                out.println("Number of schools: "+resultSet.getInt("count")+"<br><br>");
            }
            
            sqlStatement="SELECT users.username,schoolname,count(messageID) FROM users,schoolattendance,messages WHERE users.username=schoolattendance.username GROUP BY users.username, schoolname;";
            resultSet = statement.executeQuery(sqlStatement); 
            out.println("<table border=\"1\"");
                out.println("<tr>");
                out.println("<th>"+"User"+"</th>");
                out.println("<th>"+"School"+"</th>");
                out.println("<th>"+"Message count"+"</th>");
            while(resultSet.next())
            {
                out.println("<tr>");
                out.println("<td>"+resultSet.getString("username")+"</td>");
                out.println("<td>"+resultSet.getString("schoolname")+"</td>");
                out.println("<td>"+resultSet.getInt("count")+"</td>");
                out.println("</tr>");
            }
            out.println("</table><br><br>");
            
            sqlStatement="SELECT schools.schoolname,count(userName) FROM schools,schoolattendance WHERE schools.schoolname=schoolattendance.schoolname GROUP BY schools.schoolname;";
            resultSet = statement.executeQuery(sqlStatement); 

            out.println("<table border=\"1\"");
                out.println("<tr>");
                out.println("<th>"+"School"+"</th>");
                out.println("<th>"+"Number of alumni"+"</th>");
            while(resultSet.next())
            {
                out.println("<tr>");
                out.println("<td>"+resultSet.getString("schoolname")+"</td>");
                out.println("<td>"+resultSet.getInt("count")+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(adminStatistics.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(adminStatistics.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
        
        DBAccess.closeConnection(connection);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
