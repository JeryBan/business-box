package ban.jery.businessbox.service.chat;

import ban.jery.businessbox.dto.chat.ChatEntryInsertDTO;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IChatService {

    List<ChatEntry> getChat(Business business) throws Exception;

    ChatEntry insertChatEntry(ChatEntryInsertDTO dto) throws Exception;

    ChatEntry deleteChatEntry(Long id) throws EntityNotFoundException;
}
