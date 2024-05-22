package ban.jery.businessbox.repositories;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatEntryRepository extends JpaRepository<ChatEntry, Long> {

    List<ChatEntry> findAllByBusiness(Business business);

    Optional<ChatEntry> findById(Long id);

}