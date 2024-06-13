package ban.jery.businessbox.dto.user;

import ban.jery.businessbox.model.AccountSettings;
import ban.jery.businessbox.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashSet;

@UtilityClass
public class UserMapper {
    public static User mapToUser(UserInsertDTO dto) {
        User user = new User();

        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBusinesses(new HashSet<>());
        user.setChatEntryList(new ArrayList<>());

        AccountSettings accountSettings = new AccountSettings();
        accountSettings.setUser(user);
        user.setAccountSettings(accountSettings);

        return user;
    }

    public static UserRoDTO mapToRoUser(User user) {
        return new UserRoDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getBusinesses(),
                user.getChatEntryList(),
                user.getAccountSettings()
        );
    }
}
