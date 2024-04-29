package ban.jery.businessbox.repositories;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameStartingWith(String name);
    List<Product> findAllByBusiness(Business business);

}
