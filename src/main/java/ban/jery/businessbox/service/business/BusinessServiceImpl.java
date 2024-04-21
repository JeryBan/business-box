package ban.jery.businessbox.service.business;

import ban.jery.businessbox.dto.business.BusinessDeleteDTO;
import ban.jery.businessbox.dto.business.BusinessInsertDTO;
import ban.jery.businessbox.dto.business.BusinessMapper;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.User;
import ban.jery.businessbox.repositories.BusinessRepository;
import ban.jery.businessbox.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class BusinessServiceImpl implements IBusinessService {

    private final BusinessRepository businessRepo;
    private final UserRepository userRepo;

    @Override
    public Business insertBusinessToUser(BusinessInsertDTO dto) throws EntityNotFoundException {
        Optional<User> user;

        try {
            user = userRepo.findUserByEmail(dto.getEmail());
            if (user.isEmpty()) throw new EntityNotFoundException("User not found");

            Business business = BusinessMapper.mapToBusiness(dto, user.get());
            business = businessRepo.save(business);
            log.info("Business: " + business + " created.");

            user.get().getBusinesses().add(business);
            log.info("Business: " + business.getName() + " added to User: " + user.get().getEmail());

            return business;

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Business removeBusinessFromUser(BusinessDeleteDTO dto) throws EntityNotFoundException {
        Optional<User> user;
        Optional<Business> business;

        try {
            user = userRepo.findUserByEmail(dto.getEmail());
            business = businessRepo.findById(dto.getId());

            if (user.isEmpty()) throw new EntityNotFoundException("User not found");
            if (business.isEmpty()) throw new EntityNotFoundException("Business not found");

            user.get().getBusinesses().remove(business.get());
            userRepo.save(user.get());
            log.info("User: " + user.get().getEmail() + " updated");

            businessRepo.deleteById(dto.getId());
            log.info("Business: " + business.get().getName() + " deleted");

            return business.get();

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    @Override
    public List<Business> getUserBusiness(String email) throws EntityNotFoundException {
        Optional<User> user;
        List<Business> businesses;

        try {
            user = userRepo.findUserByEmail(email);
            if (user.isEmpty()) throw new EntityNotFoundException("User not found");

            businesses = new ArrayList<>(user.get().getBusinesses());
            log.info("Fetched user's businesses.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }

        return businesses;
    }
}
