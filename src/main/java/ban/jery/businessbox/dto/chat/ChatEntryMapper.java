package ban.jery.businessbox.dto.chat;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import ban.jery.businessbox.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatEntryMapper {


    public static ChatEntry mapToChatEntry(ChatEntryInsertDTO dto, Business business, User sender) {
        return new ChatEntry(null, dto.getBody(), null, business, sender);
    }

    public static ChatEntryRoDTO mapToRoChatEntry(ChatEntry chatEntry) {
        return new ChatEntryRoDTO(chatEntry.getId() ,chatEntry.getBody(), chatEntry.getBusiness());
    }


}