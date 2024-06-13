package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.chat.ChatEntryInsertDTO;
import ban.jery.businessbox.dto.chat.ChatEntryMapper;
import ban.jery.businessbox.dto.chat.ChatEntryRoDTO;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import ban.jery.businessbox.service.chat.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class ChatController {


    private final IChatService chatService;

    @Autowired
    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatEntryRoDTO sendMessage(@Payload ChatEntryInsertDTO dto) throws Exception {
        chatService.insertChatEntry(dto);
        return ChatEntryMapper.mapToRoChatEntry(dto);
    }

    @MessageMapping("/chat/getChat")
    @SendTo("/topic/chatHistory")
    public List<ChatEntry> getChat(@Payload Business business) throws Exception {
        return chatService.getChat(business);
    }

}
