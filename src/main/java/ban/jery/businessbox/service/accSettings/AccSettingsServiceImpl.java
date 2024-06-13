package ban.jery.businessbox.service.accSettings;

import ban.jery.businessbox.dto.accountSettings.AccSettingsDTO;
import ban.jery.businessbox.model.AccountSettings;
import ban.jery.businessbox.model.User;
import ban.jery.businessbox.repositories.AccSettingsRepository;
import ban.jery.businessbox.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AccSettingsServiceImpl implements IAccSettingsService {

    private final UserRepository userRepo;
    private final AccSettingsRepository accSettingsRepo;

    @Override
    public AccountSettings getAccSettings(String email) throws EntityNotFoundException {
        try {
           return accSettingsRepo.findByUserEmail(email);

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountSettings updateSettings(String email, AccSettingsDTO dto) throws EntityNotFoundException {
        User user = userRepo.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        AccountSettings settings = accSettingsRepo.findByUserEmail(email);

        settings.setUser(user);
        settings.setTheme(dto.getTheme());
        settings.setChatVisible(dto.isChatVisible());
        settings.setNotificationsEnabled(dto.isNotificationsEnabled());

        return accSettingsRepo.save(settings);
    }
}
