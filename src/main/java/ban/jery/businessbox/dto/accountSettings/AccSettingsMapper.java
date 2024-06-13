package ban.jery.businessbox.dto.accountSettings;

import ban.jery.businessbox.model.AccountSettings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccSettingsMapper {

    public static AccSettingsDTO mapToAccSettingsDTO(AccountSettings accountSettings) {
        return new AccSettingsDTO(
                accountSettings.getTheme(),
                accountSettings.isChatVisible(),
                accountSettings.isNotificationsEnabled()
        );
    }
}
