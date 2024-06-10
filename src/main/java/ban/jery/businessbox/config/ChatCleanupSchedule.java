package ban.jery.businessbox.config;

import ban.jery.businessbox.repositories.ChatEntryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChatCleanupSchedule {

    private final ChatEntryRepository chatEntryRepository;


    public ChatCleanupSchedule(ChatEntryRepository chatEntryRepository) {
        this.chatEntryRepository = chatEntryRepository;
    }

    @Scheduled(cron = "0 0 0 1 * *") // midnight 1st of each month
    public void deleteOldMessages() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        chatEntryRepository.deleteByCreatedAtBefore(oneMonthAgo);

    }
}
