package ban.jery.businessbox.dto.user;

import ban.jery.businessbox.model.User;
import lombok.experimental.UtilityClass;

import java.util.HashSet;

@UtilityClass
public class UserMapper {

    public static User mapToUser(UserInsertDTO dto) {
        return new User(null, dto.getEmail(), dto.getPassword(), new HashSet<>());
    }

    public static User mapToUser(UserUpdateDTO dto) {
        return new User(dto.getId(), dto.getEmail(), dto.getPassword(), dto.getBusinesses());
    }

    public static UserRoDTO mapToRoUser(User user) {
        return new UserRoDTO(user.getId(), user.getEmail(), user.getPassword(), user.getBusinesses());
    }
}
