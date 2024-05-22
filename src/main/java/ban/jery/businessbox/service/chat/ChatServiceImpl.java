package ban.jery.businessbox.service.chat;

import ban.jery.businessbox.dto.chat.ChatEntryInsertDTO;
import ban.jery.businessbox.dto.chat.ChatEntryMapper;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import ban.jery.businessbox.repositories.BusinessRepository;
import ban.jery.businessbox.repositories.ChatEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ChatServiceImpl implements IChatService {

    private final ChatEntryRepository chatRepo;
    private final BusinessRepository businessRepo;

    @Override
    public List<ChatEntry> getChat(Business business) throws Exception {
        List<ChatEntry> chat = new ArrayList<>();

        try {
            chat = chatRepo.findAllByBusiness(business);
            log.info("Chat retrieved successfully.");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return chat;
    }

    @Override
    public ChatEntry insertChatEntry(ChatEntryInsertDTO dto) throws Exception {

        try {
            Business business = businessRepo.findById(dto.getBusiness().getId())
                    .orElseThrow( () -> new EntityNotFoundException("Business not found"));

            ChatEntry chatEntry = ChatEntryMapper.mapToChatEntry(dto, business);
            chatEntry = chatRepo.save(chatEntry);
            log.info("Chat entry with id: " + chatEntry.getId() + " inserted successfully.");

            business.getChatHistory().add(chatEntry);
            log.info("Chat entry added to Business: " + business.getName());

            return chatEntry;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ChatEntry deleteChatEntry(Long id) throws EntityNotFoundException {
        Business business;

        try {
            ChatEntry chatEntry = chatRepo.findById(id)
                    .orElseThrow( () -> new EntityNotFoundException("Chat entry not found - id: " + id));

            business = chatEntry.getBusiness();
            business.getChatHistory().remove(chatEntry);

            chatRepo.deleteById(id);
            log.info("Chat entry with id: " + id + " deleted successfully.");

            return chatEntry;

        } catch(EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
