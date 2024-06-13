package ban.jery.businessbox.service.accSettings;

import ban.jery.businessbox.dto.accountSettings.AccSettingsUpdateDTO;
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
    public AccountSettings getAccSettings(Long userId) throws EntityNotFoundException {
        try {
           return accSettingsRepo.findByUserId(userId);

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public AccountSettings updateSettings(Long userId, AccSettingsUpdateDTO dto) throws EntityNotFoundException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        AccountSettings settings = accSettingsRepo.findByUserId(userId);

        settings.setUser(user);
        settings.setTheme(dto.getTheme());
        settings.setVisibleDashboard(dto.isVisibleDashboard());
        settings.setNotificationsEnabled(dto.isNotificationsEnabled());

        return accSettingsRepo.save(settings);
    }
}
