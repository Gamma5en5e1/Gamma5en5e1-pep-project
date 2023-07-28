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

    public Message createMessage(int posted_by,String message_text,long time_posted_epoch){
        return messageDAO.createMessage(posted_by,message_text,time_posted_epoch);
    }

    public Message updateMessage(Long message_text, int message_id){
        return messageDAO.updateMessage(message_text,message_id);
    }

    public Message deleteMessageByID(int message_id){
        return messageDAO.deleteMessageByID(message_id);
    }
}
