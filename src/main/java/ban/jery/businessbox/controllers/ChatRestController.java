package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.chat.ChatEntryDTO;
import ban.jery.businessbox.service.chat.IChatService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/chat")
public class ChatRestController {

    private final IChatService chatService;

    @Autowired
    public ChatRestController(IChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/history/{email}")
    public ResponseEntity<List<ChatEntryDTO>> getChatHistory(@PathVariable("email") String email) {
        List<ChatEntryDTO> chatHistory = new ArrayList<>();

        try {
            chatHistory = chatService.retrieveChatHistory(email);
            return ResponseEntity.ok(chatHistory);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
}
