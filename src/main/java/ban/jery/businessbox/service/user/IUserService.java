package ban.jery.businessbox.service.user;

import ban.jery.businessbox.dto.accountSettings.AccSettingsUpdateDTO;
import ban.jery.businessbox.dto.business.BusinessInsertDTO;
import ban.jery.businessbox.dto.user.UserInsertDTO;
import ban.jery.businessbox.model.AccountSettings;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.User;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface IUserService {

    User insertUser(UserInsertDTO dto) throws Exception;

    User deleteUser(Long id) throws EntityNotFoundException;

    List<User> getAllUsers() throws Exception;

    User findUserByEmail(String email) throws EntityNotFoundException;

}
