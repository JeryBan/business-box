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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class BusinessServiceImpl implements IBusinessService {

    private final BusinessRepository businessRepo;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public Business insertBusinessToUser(BusinessInsertDTO dto) throws EntityNotFoundException {

        try {
            User user = userRepo.findUserByEmail(dto.getEmail())
                    .orElseThrow( () -> new EntityNotFoundException("User not found"));

            Business business = BusinessMapper.mapToBusiness(dto, user);
            business = businessRepo.save(business);
            log.info("Business: " + business + " created.");

            user.getBusinesses().add(business);
            log.info("Business: " + business.getName() + " added to User: " + user.getEmail());

            return business;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Business removeBusinessFromUser(BusinessDeleteDTO dto) throws EntityNotFoundException {

        try {
            User user = userRepo.findUserByEmail(dto.getEmail())
                    .orElseThrow( () -> new EntityNotFoundException("User not found"));
            Business business = businessRepo.findById(dto.getId())
                    .orElseThrow( () -> new EntityNotFoundException("Business not found"));

            user.getBusinesses().remove(business);
            userRepo.save(user);
            log.info("User: " + user.getEmail() + " updated");

            businessRepo.deleteById(dto.getId());
            log.info("Business: " + business.getName() + " deleted");

            return business;

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }


    @Override
    public List<Business> getUserBusiness(String email) throws EntityNotFoundException {
        List<Business> businesses;

        try {
            User user = userRepo.findUserByEmail(email)
                    .orElseThrow( () -> new EntityNotFoundException("User not found"));

            businesses = new ArrayList<>(user.getBusinesses());
            log.info("Fetched user's businesses.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }

        return businesses;
    }
}
