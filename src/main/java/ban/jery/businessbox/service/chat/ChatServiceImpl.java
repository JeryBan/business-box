package ban.jery.businessbox.service.chat;

import ban.jery.businessbox.dto.chat.ChatEntryDTO;
import ban.jery.businessbox.dto.chat.ChatEntryMapper;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import ban.jery.businessbox.model.User;
import ban.jery.businessbox.repositories.BusinessRepository;
import ban.jery.businessbox.repositories.ChatEntryRepository;
import ban.jery.businessbox.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ChatServiceImpl implements IChatService {

    private final UserRepository userRepo;
    private final BusinessRepository businessRepo;
    private final ChatEntryRepository chatRepo;

    @Override
    public List<ChatEntryDTO> retrieveChatHistory(String email) throws EntityNotFoundException {
        List<ChatEntry> chatEntries = new ArrayList<>();
        List<ChatEntryDTO> chatHistory = new ArrayList<>();

        User sender = userRepo.findUserByEmail(email)
                .orElseThrow( () -> new EntityNotFoundException("User not found"));

        try {
            chatEntries = chatRepo.findAllBySender(sender);
            log.info("Chat retrieved successfully.");

            for (ChatEntry chatEntry : chatEntries) {
                chatHistory.add(
                        ChatEntryMapper.mapToChatDTO(chatEntry)
                );
            }

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return chatHistory;
    }

    @Override
    @Transactional
    public ChatEntry insertChatEntry(ChatEntryDTO dto) throws Exception {

        try {
            User sender = userRepo.findUserByEmail(dto.getSender())
                    .orElseThrow( () -> new EntityNotFoundException("Sender not found"));

            Business business = businessRepo.findBusinessByNameAndAndUserId(dto.getBusinessName(), sender.getId());

            ChatEntry chatEntry = ChatEntryMapper.mapToChatEntry(dto, business, sender);
            chatEntry = chatRepo.save(chatEntry);
            log.info("Chat entry with id: " + chatEntry.getId() + " inserted successfully.");

            business.getChatHistory().add(chatEntry);
            sender.getChatEntryList().add(chatEntry);
            log.info("Chat entry added to User: " + sender.getEmail() + " for bussiness " + business.getName());

            return chatEntry;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
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
