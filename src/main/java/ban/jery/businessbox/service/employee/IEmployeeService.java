package ban.jery.businessbox.service.employee;

import ban.jery.businessbox.dto.employee.EmployeeInsertDTO;
import ban.jery.businessbox.dto.employee.EmployeeUpdateDTO;
import ban.jery.businessbox.model.Employee;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IEmployeeService {

    Employee insertEmployee(EmployeeInsertDTO dto) throws Exception;

    Employee updateEmployee(EmployeeUpdateDTO dto) throws EntityNotFoundException;

    Employee deleteEmployee(Long id) throws EntityNotFoundException;

    List<Employee> getEmployeeByLastname(String lastname) throws EntityNotFoundException;
}
