package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.chat.ChatEntryInsertDTO;
import ban.jery.businessbox.dto.chat.ChatEntryMapper;
import ban.jery.businessbox.dto.chat.ChatEntryRoDTO;
import ban.jery.businessbox.dto.employee.EmployeeMapper;
import ban.jery.businessbox.dto.employee.EmployeeRoDTO;
import ban.jery.businessbox.model.ChatEntry;
import ban.jery.businessbox.model.Employee;
import ban.jery.businessbox.service.chat.IChatService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRestController {

    private final IChatService chatService;

    @GetMapping("/")
    public List<ChatEntry> getChat() {
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<ChatEntryRoDTO> addChatEntry(@Valid @RequestBody ChatEntryInsertDTO dto) {

        try {
            ChatEntry chatEntry = chatService.insertChatEntry(dto);
            ChatEntryRoDTO roChatEntry = ChatEntryMapper.mapToRoChatEntry(chatEntry);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(roChatEntry.getId())
                    .toUri();

            return ResponseEntity.created(location).body(roChatEntry);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ChatEntryRoDTO> deleteChatEntry(@PathVariable("id") Long id) {

        try {
            ChatEntry chatEntry = chatService.deleteChatEntry(id);
            ChatEntryRoDTO roChatEntry = ChatEntryMapper.mapToRoChatEntry(chatEntry);

            return ResponseEntity.ok(roChatEntry);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
