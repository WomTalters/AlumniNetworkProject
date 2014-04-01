package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;

/**
 * A model used to represent a message
 * 
 * @author Tom
 */
public class Message {

    private int messageID;
    private int messageThreadId;
    private String messageText;
    private String sender;
    private String senFullname;
    private Timestamp dateTimeSent;

    public Message() {
    }

    public int getMessageID() {
        return messageID;
    }

    public Message(String messageText,int messageThreadId , String sender, Timestamp dateTimeSent, String senFullname) {
        this.messageText = messageText;
        this.sender = sender;
        this.dateTimeSent = dateTimeSent;
        this.messageThreadId = messageThreadId;
        this.senFullname = senFullname;
    }

    public Message(String messageText, String sender, Timestamp dateTimeSent, String senFullname) {
        this.messageText = messageText;
        this.sender = sender;
        this.dateTimeSent = dateTimeSent;
        this.senFullname = senFullname;
    }

    public String getSenFullname() {
        return senFullname;
    }

    public void setSenFullname(String senFullname) {
        this.senFullname = senFullname;
    }

    

    public int getMessageThreadId() {
        return messageThreadId;
    }

    public void setMessageThreadId(int messageThreadId) {
        this.messageThreadId = messageThreadId;
    }

    
    
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(Timestamp dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }

    /**
     * adds a new message to the database
     * 
     * @param con
     * @throws ServletException 
     */
    public void save(Connection con) throws ServletException {
        try {

            PreparedStatement ps = con.prepareStatement("INSERT INTO messages (messagethreadid,messagetext,sender,datetimesent) VALUES(?,?,?,?);");
            ps.setInt(1, messageThreadId);
            ps.setString(2, messageText);
            ps.setString(3, sender);
            ps.setTimestamp(4, dateTimeSent);
            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException("Message save problem");
        }
    }

    /**
     * loads a list of messages in a particular thread
     * 
     * @param messageThreadId the id of
     * @param con
     * @return
     * @throws ServletException 
     */
    public static ArrayList loadMessages(int messageThreadId,Connection con) throws ServletException{        
        try{
            
            PreparedStatement ps = con.prepareStatement("SELECT * FROM messages where messagethreadid=?;");
            ps.setInt(1, messageThreadId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Message> messages = new ArrayList();

            while (rs.next()){
                messages.add(new Message(rs.getString("messagetext"),rs.getString("sender"),rs.getTimestamp("datetimesent"),UserDetails.getNameFromUsername(rs.getString("sender"), con)));                
            }
            return messages;
        } catch (SQLException ex) {
            throw new ServletException("school list load problem");
        } 
        
    }
}