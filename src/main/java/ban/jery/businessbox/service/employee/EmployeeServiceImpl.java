package ban.jery.businessbox.service.employee;

import ban.jery.businessbox.dto.employee.EmployeeInsertDTO;
import ban.jery.businessbox.dto.employee.EmployeeMapper;
import ban.jery.businessbox.dto.employee.EmployeeUpdateDTO;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.Employee;
import ban.jery.businessbox.repositories.BusinessRepository;
import ban.jery.businessbox.repositories.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepo;
    private final BusinessRepository businessRepo;

    @Override
    @Transactional
    public Employee insertEmployeeToBusiness(EmployeeInsertDTO dto) throws EntityNotFoundException {

        try {
            Business business = businessRepo.findById(dto.getBusiness().getId())
                    .orElseThrow( () -> new EntityNotFoundException("Business not found"));

            Employee employee = EmployeeMapper.mapToEmployee(dto, business);
            employee = employeeRepo.save(employee);
            log.info("Employee with id: " + employee.getId() + " inserted successfully.");

            business.getEmployees().add(employee);
            log.info("Employee: " + employee.getLastname() + " added to Business: " + business.getName());

            return employee;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Employee updateEmployee(EmployeeUpdateDTO dto) throws EntityNotFoundException {
        Employee newEmployee;

        try {
            Business business = businessRepo.findById(dto.getBusiness().getId())
                    .orElseThrow( () -> new EntityNotFoundException("Business for employee not found or mismatch"));

            Employee oldEmployee = employeeRepo.findById(dto.getId())
                    .orElseThrow( () -> new EntityNotFoundException("Employee not found - id: " + dto.getId()));

            if (!oldEmployee.getBusiness().getId().equals(business.getId())) {
                throw new EntityNotFoundException("Business mismatch for employee - id: " + dto.getId());
            }

            business.getEmployees().remove(oldEmployee);
            newEmployee = employeeRepo.save(EmployeeMapper.mapToEmployee(dto));
            business.getEmployees().add(newEmployee);

            log.info("Employee with id: " + newEmployee.getId() + " updated successfully.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return newEmployee;
    }

    @Override
    @Transactional
    public Employee deleteEmployee(Long id) throws EntityNotFoundException {
        Business business;

        try {
            Employee employee = employeeRepo.findById(id)
                    .orElseThrow( () -> new EntityNotFoundException("Employee not found - id: " + id));

            business = employee.getBusiness();
            business.getEmployees().remove(employee);

            employeeRepo.deleteById(id);
            log.info("Employ with id: " + id + " deleted successfully.");

            return employee;

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Employee> getEmployeeByLastname(String lastname) throws EntityNotFoundException {
        List<Employee> employees = new ArrayList<>();

        try {
            employees = employeeRepo.findByLastnameStartingWith(lastname);
            if (employees.isEmpty()) throw new EntityNotFoundException("No employees found.");

            log.info("Employee(s) found");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return employees;
    }

    @Override
    public List<Employee> getAllEmployees(Long businessId) throws EntityNotFoundException {
        List<Employee> employees;

        try {
            Business business = businessRepo.findById(businessId)
                    .orElseThrow( () -> new EntityNotFoundException("Business not found"));

            employees = new ArrayList<>(business.getEmployees());
            log.info("Employees fetched successfully.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }

        return employees;
    }
}
