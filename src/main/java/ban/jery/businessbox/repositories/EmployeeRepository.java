package ban.jery.businessbox.repositories;

import ban.jery.businessbox.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByLastnameStartingWith(String lastname);
}
