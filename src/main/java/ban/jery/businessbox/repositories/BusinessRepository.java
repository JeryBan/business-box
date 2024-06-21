package ban.jery.businessbox.repositories;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    List<Business> findAllByUser(User user);

    Business findBusinessByNameAndAndUserId(String name, Long id);
}
