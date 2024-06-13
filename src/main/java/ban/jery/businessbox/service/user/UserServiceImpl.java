package ban.jery.businessbox.service.user;

import ban.jery.businessbox.dto.accountSettings.AccSettingsUpdateDTO;
import ban.jery.businessbox.dto.user.UserInsertDTO;
import ban.jery.businessbox.dto.user.UserMapper;
import ban.jery.businessbox.model.AccountSettings;
import ban.jery.businessbox.model.User;
import ban.jery.businessbox.repositories.AccSettingsRepository;
import ban.jery.businessbox.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    @Autowired
    private final UserRepository userRepo;

    @Override
    public List<User> getAllUsers() {
        List<User> users;

        try {
            users = userRepo.findAll();
            log.info("Users fetched successfully.");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return users;
    }

    @Override
    public User findUserByEmail(String email) {

        try {
            User user = userRepo.findUserByEmail(email)
                    .orElseThrow( () -> new UsernameNotFoundException("User not found"));

            log.info("User with email: " + email + " found.");
            return user;

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public User insertUser(UserInsertDTO dto) throws Exception {
        User user;
        try {

            Optional<User> checkUser = userRepo.findUserByEmail(dto.getEmail());
            if (checkUser.isPresent()) throw new DataIntegrityViolationException("User already exists");

            user = userRepo.save(UserMapper.mapToUser(dto));

            if (user == null) throw new Exception("error - insert user");
            log.info("user with id: " + user.getId() + " inserted successfully.");

        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return user;
    }

    @Override
    public User deleteUser(Long id) throws EntityNotFoundException {
        Optional<User> user;

        try {
            user = userRepo.findById(id);
            if (user.isEmpty()) throw new EntityNotFoundException("User not found - id: " + id);

            userRepo.deleteById(id);
            log.info("User, id: " + id + " deleted");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return user.orElseThrow();
    }

}
