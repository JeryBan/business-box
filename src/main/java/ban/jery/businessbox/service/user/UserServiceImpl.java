package ban.jery.businessbox.service.user;

import ban.jery.businessbox.dto.user.UserInsertDTO;
import ban.jery.businessbox.dto.user.UserMapper;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.User;
import ban.jery.businessbox.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository repo;

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;

        try {
            users = repo.findAll();
            log.info("Users fetched successfully.");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return users;
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user;

        try {
            user = repo.findUserByEmail(email);

            if (user.isEmpty()) throw new EntityNotFoundException();
            log.info("User with email: " + email + " found.");

            return user.get();

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

            Optional<User> checkUser = repo.findUserByEmail(dto.getEmail());
            if (checkUser.isPresent()) throw new DataIntegrityViolationException("User already exists");

            user = repo.save(UserMapper.mapToUser(dto));

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
        return null;
    }

    @Override
    public void addBusinessToUser(Long id) throws EntityNotFoundException {

    }

    @Override
    public void removeBusinessFromUser(Long id) throws EntityNotFoundException {

    }

    @Override
    public Set<Business> getUserBusinesses() throws Exception {
        return null;
    }
}
