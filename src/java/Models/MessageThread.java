package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;

/**
 *
 * @author Tom
 */
public class MessageThread {

    private ArrayList<Message> messages;

    private int messageThreadId;
    private String recipient;
    private String recFullname;
    private String seneder;
    private String senFullname;
    
    

    public MessageThread() {
    }

    public MessageThread(String recipient, String seneder, String recFullName, String senFullname) {
        this.recipient = recipient;
        this.seneder = seneder;
        this.messages = new ArrayList();
        this.recFullname = recFullname;
        this.senFullname = senFullname;
    }

    public MessageThread(int messageThreadId, String recipient, String seneder, String recFullName, String senFullname) {
        this.messageThreadId = messageThreadId;
        this.recipient = recipient;
        this.seneder = seneder;
        this.messages = new ArrayList();
        this.recFullname = recFullname;
        this.senFullname = senFullname;
    }

    public int getMessageThreadId() {
        return messageThreadId;
    }

    public void setMessageThreadId(int messagethreadId) {
        this.messageThreadId = messagethreadId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
        
    }

    public String getRecFullname() {
        return recFullname;
    }

    public void setRecFullname(String recFullname) {
        this.recFullname = recFullname;
    }

    public String getSenFullname() {
        return senFullname;
    }

    public void setSenFullname(String senFullname) {
        this.senFullname = senFullname;
    }

    
    
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSeneder() {
        return seneder;
    }

    public void setSeneder(String seneder) {
        this.seneder = seneder;
    }

    public void saveNew(Connection con) throws ServletException {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO messagethreads (sender,recipient) VALUES (?,?);");
            ps.setString(1, seneder);
            ps.setString(2, recipient);
            ps.executeUpdate();
            ps = con.prepareStatement("SELECT max(messagethreadid) FROM messagethreads WHERE sender=? AND recipient=?;");
            ps.setString(1, seneder);
            ps.setString(2, recipient);
            ResultSet rs = ps.executeQuery();
            rs.next();
            setMessageThreadId(rs.getInt("max"));
            
            
        } catch (Exception ex) {
            throw new ServletException("message thread save problem");
        }
    }

    public static ArrayList loadMessageThreads(String profileUser, String user, Connection con) throws ServletException {

        try {
            PreparedStatement ps;

            if (profileUser.equals(user)) {
                ps = con.prepareStatement("SELECT * FROM messagethreads WHERE recipient=? OR sender=?;");
                ps.setString(1, user);
                ps.setString(2, user);
            } else {
                ps = con.prepareStatement("SELECT * FROM messagethreads WHERE recipient=? AND sender=?;");
                ps.setString(1, profileUser);
                ps.setString(2, user);
            }

            ResultSet rs = ps.executeQuery();
            ArrayList<MessageThread> messageThreads = new ArrayList();

            while (rs.next()) {
                messageThreads.add(new MessageThread(rs.getInt("messagethreadid"), rs.getString("recipient"), rs.getString("sender"),UserDetails.getNameFromUsername(rs.getString("recipient"), con),UserDetails.getNameFromUsername(rs.getString("sender"),con)));
            }
            
            for (MessageThread mt : messageThreads){
                mt.setMessages(Message.loadMessages(mt.getMessageThreadId(),con));
            }
            
            return messageThreads;
        } catch (Exception ex) {
            throw new ServletException("school list load problem");
        }

    }

}
