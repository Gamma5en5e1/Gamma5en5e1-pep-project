package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();

    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("/messages/{message_id}",this::getMessageByID);
        app.get("/messages",this::getAllMessages);
        app.get("/accounts/{account_id}/messages",this::getMessagesByUser);
        app.post("/register", this::registerAccount);
        app.post("/messages",this::createMessage);
        app.post("/login", this::login);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.delete("/messages/{message_id}", this::deleteMessageByID);
        //app.start(8080);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context){
        context.json("sample text");
    }

    private void getAllMessages(Context context){
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
        context.status(200);
    }

    private void getMessageByID(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = messageService.getMessageByID(mapper.readValue(context.pathParam("message_id"), int.class));
        context.json(mapper.writeValueAsString(message));
        context.status(200);
    }

    private void getMessagesByUser(Context context) throws JsonProcessingException{
        //ObjectMapper mapper = new ObjectMapper();
        int posted_by = Integer.parseInt(context.pathParam("posted_by"));
        List<Message> messages = messageService.getMessagesByUser(posted_by);
        context.json(messages);
        context.status(200);
    }

    private void registerAccount(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        account = accountService.addAccount(account);
        if(account!=null){
            context.json(mapper.writeValueAsString(accountService));
            context.status(200);
        }else{
            context.status(400);
        };
    }

    private void createMessage(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Integer posted_by = Integer.parseInt(context.pathParam("posted_by"));
        String message_text = context.appAttribute("message_text");
        Long time_posted_epoch = context.appAttribute("time_posted_enough");
        Message message = messageService.createMessage(posted_by, message_text, time_posted_epoch);
        if(message==null){
            context.status(400);
        }else{
            context.json(mapper.writeValueAsString(message));
            context.status(200);
        }
    }
    private void login(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        account = accountService.loginAccount(account);
        if(account.getAccount_id()!=0){
            context.json(mapper.writeValueAsString(account));
            context.status(200);
        }else{
            context.status(401);
        }
    }

    private void updateMessage(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        String message_text = context.pathParam("message_text");
        Message message = messageService.updateMessage(message_text, message_id);
            if(message==null){
                context.status(400);
            }else{
                context.json(mapper.writeValueAsString(message));
                context.status(200);
            }
        }

    private void deleteMessageByID(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = messageService.deleteMessageByID(mapper.readValue(context.pathParam("message_id"), int.class));
        context.json(mapper.writeValueAsString(message));
        context.status(200);
    }
}