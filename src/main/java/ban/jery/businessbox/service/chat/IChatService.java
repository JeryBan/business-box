package ban.jery.businessbox.service.chat;

import ban.jery.businessbox.dto.chat.ChatEntryDTO;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IChatService {

    List<ChatEntryDTO> retrieveChatHistory(String email) throws EntityNotFoundException;

    ChatEntry insertChatEntry(ChatEntryDTO dto) throws Exception;

    ChatEntry deleteChatEntry(Long id) throws EntityNotFoundException;
}
