package Service;
import java.util.List;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }

    public List<Message> getMessagesByUser(int posted_by){
        return messageDAO.getMessagesByUser(posted_by);
    }

    public Message createMessage(Message message){
        return messageDAO.createMessage(message);
    }

    public Message updateMessage(String message_text, int message_id){
        return messageDAO.updateMessage(message_text,message_id);
    }

    public Message deleteMessageByID(int message_id){
        return messageDAO.deleteMessageByID(message_id);
    }
}
