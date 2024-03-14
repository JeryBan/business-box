package ban.jery.businessbox.service.employee;

import ban.jery.businessbox.dto.employee.EmployeeInsertDTO;
import ban.jery.businessbox.dto.employee.EmployeeMapper;
import ban.jery.businessbox.dto.employee.EmployeeUpdateDTO;
import ban.jery.businessbox.model.Employee;
import ban.jery.businessbox.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository repo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }


    @Override
    @Transactional
    public Employee insertEmployee(EmployeeInsertDTO dto) throws Exception {
        Employee employee;

        try {
            employee = repo.save(EmployeeMapper.mapToEmployee(dto));

            if (employee == null) throw new Exception("Error - Insert employee");
            log.info("insert success - id: " + employee.getId());

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return employee;
    }

    @Override
    @Transactional
    public Employee updateEmployee(EmployeeUpdateDTO dto) throws EntityNotFoundException {
        Optional<Employee> employee;
        Employee newEmployee;

        try {
            employee = repo.findById(dto.getId());
            if (employee.isEmpty()) throw new EntityNotFoundException("Employee not found - id: " + dto.getId());

            newEmployee = repo.save(EmployeeMapper.mapToEmployee(dto));
            log.info("Update success - id: " + dto.getId());

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return newEmployee;
    }

    @Override
    @Transactional
    public Employee deleteEmployee(Long id) throws EntityNotFoundException {
        Optional<Employee> employee;

        try {
            employee = repo.findById(id);
            if (employee.isEmpty()) throw new EntityNotFoundException("Employee not found - id: " + id);

            repo.deleteById(id);
            log.info("Employ, id: " + id + " deleted");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return employee.orElseThrow();
    }

    @Override
    public List<Employee> getEmployeeByLastname(String lastname) throws EntityNotFoundException {
        List<Employee> employees = new ArrayList<>();

        try {
            employees = repo.findByLastnameStartingWith(lastname);
            if (employees.isEmpty()) throw new EntityNotFoundException("Employee not found - lastname: " + lastname);

            log.info("Employee(s) found");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return employees;
    }
}
