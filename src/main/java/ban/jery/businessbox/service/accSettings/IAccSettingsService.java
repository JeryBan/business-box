package ban.jery.businessbox.service.accSettings;

import ban.jery.businessbox.dto.accountSettings.AccSettingsUpdateDTO;
import ban.jery.businessbox.model.AccountSettings;
import jakarta.persistence.EntityNotFoundException;

public interface IAccSettingsService {

    AccountSettings getAccSettings(Long userId) throws EntityNotFoundException;

    AccountSettings updateSettings(Long userId, AccSettingsUpdateDTO dto) throws EntityNotFoundException;
}
