zpackage com.example.demo.controller;

import com.example.demo.model.MessageChat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageChat sendMessage(@Payload MessageChat messageChat){return  messageChat;}

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public  MessageChat addUser(@Payload MessageChat messageChat, SimpMessageHeaderAccessor messageHeaderAccessor){
        messageHeaderAccessor.getSessionAttributes().put("userName",messageChat.getSender());
        return  messageChat;
    }
}
