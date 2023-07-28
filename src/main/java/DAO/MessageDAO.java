package DAO;
import Util.ConnectionUtil;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql="SELECT * FROM MESSAGE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql="SELECT * FROM MESSAGE WHERE (message.message_id) =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,message_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Message message = new Message(resultSet.getInt("message_id"),resultSet.getInt("posted_by"),resultSet.getString("message_text"),resultSet.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try{
            if(getMessageByID(message_id)!=null){
                Message message = getMessageByID(message_id);
                String sql="DELETE * FROM MESSAGE WHERE (message.message_id) =?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1,message_id);
                preparedStatement.executeUpdate();
                return message;
                }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message createMessage(int posted_by,String message_text,long time_posted_epoch){
        Connection connection = ConnectionUtil.getConnection();
        if(message_text.toString().length()<255&&message_text.toString().length()>0){
        try{
            String sql = "INSERT INTO Message (posted_by,message_text,time_posted_epoch) VALUES (?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, posted_by);
            preparedStatement.setString(2, message_text);
            preparedStatement.setLong(2, time_posted_epoch);
            preparedStatement.executeUpdate();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int generated_message_id = (int) resultSet.getInt(1);
                return getMessageByID(generated_message_id);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
        return null;
    }

    public Message updateMessage(Long message_text,int message_id){
        if(message_text.toString().length()<256&&getMessageByID(message_id)!=null){
            Connection connection = ConnectionUtil.getConnection();
            try{
            String sql = "UPDATE MESSAGE SET message.message_text=? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, message_text);
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();
            return getMessageByID(message_id);
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    return null;
}
    public List<Message> getMessagesByUser(int posted_by){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql="SELECT * FROM MESSAGE WHERE (posted_by)=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, posted_by);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


}
