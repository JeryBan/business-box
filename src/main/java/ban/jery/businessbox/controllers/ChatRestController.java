package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.chat.ChatEntryInsertDTO;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import ban.jery.businessbox.service.chat.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatRestController {


    private final IChatService chatService;

    @Autowired
    public ChatRestController(IChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatEntry sendMessage(@Payload ChatEntryInsertDTO dto) throws Exception {
        return chatService.insertChatEntry(dto);
    }

    @MessageMapping("/chat/getChat")
    @SendTo("/topic/chatHistory")
    public List<ChatEntry> getChat(@Payload Business business) throws Exception {
        return chatService.getChat(business);
    }

}
