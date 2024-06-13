package ban.jery.businessbox.dto.accountSettings;

import ban.jery.businessbox.config.Theme;
import ban.jery.businessbox.dto.BaseDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccSettingsUpdateDTO extends BaseDTO {

    @Enumerated(EnumType.STRING)
    private Theme theme;

    private boolean isVisibleDashboard;

    private boolean notificationsEnabled;
}
