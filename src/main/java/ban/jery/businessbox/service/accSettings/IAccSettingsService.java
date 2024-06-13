package ban.jery.businessbox.service.accSettings;

import ban.jery.businessbox.dto.accountSettings.AccSettingsDTO;
import ban.jery.businessbox.model.AccountSettings;
import jakarta.persistence.EntityNotFoundException;

public interface IAccSettingsService {

    AccountSettings getAccSettings(String email) throws EntityNotFoundException;

    AccountSettings updateSettings(String email, AccSettingsDTO dto) throws EntityNotFoundException;
}
