package ban.jery.businessbox.repositories;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import ban.jery.businessbox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatEntryRepository extends JpaRepository<ChatEntry, Long> {

    List<ChatEntry> findAllByBusiness(Business business);

    List<ChatEntry> findAllBySender(User sender);

    Optional<ChatEntry> findById(Long id);

    void deleteByCreatedAtBefore(LocalDateTime timestamp);

}