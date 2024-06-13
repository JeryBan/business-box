package ban.jery.businessbox.repositories;

import ban.jery.businessbox.model.AccountSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccSettingsRepository extends JpaRepository<AccountSettings, Long> {

    AccountSettings findByUserId(Long userId);

    AccountSettings findByUserEmail(String email);
}
