package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.employee.*;
import ban.jery.businessbox.model.Employee;
import ban.jery.businessbox.service.employee.IEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeRestController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<List<EmployeeRoDTO>> getAllEmployees(@PathVariable("businessId") Long businessId) {
        List<Employee>  employees;
        List<EmployeeRoDTO> roEmployees = new ArrayList<>();

        try {
            employees = employeeService.getAllEmployees(businessId);
            for (Employee employee : employees) {
                roEmployees.add(EmployeeMapper.mapToRoEmployee(employee));
            }

            return ResponseEntity.ok(roEmployees);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/search/{lastname}")
    public ResponseEntity<List<EmployeeRoDTO>> getEmployeeByLastname(@PathVariable("lastname") String lastname) {
        List<Employee> employees;
        List<EmployeeRoDTO> roEmployees = new ArrayList<>();

        try {
            employees = employeeService.getEmployeeByLastname(lastname);
            for (Employee employee : employees) {
                roEmployees.add(EmployeeMapper.mapToRoEmployee(employee));
            }

            return ResponseEntity.ok(roEmployees);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<EmployeeRoDTO> addEmployeeToBusiness(@Valid @RequestBody EmployeeInsertDTO dto) {

        try {
            Employee employee = employeeService.insertEmployeeToBusiness(dto);
            EmployeeRoDTO roEmployee = EmployeeMapper.mapToRoEmployee(employee);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(roEmployee.getId())
                    .toUri();

            return ResponseEntity.created(location).body(roEmployee);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRoDTO> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeUpdateDTO dto) {
        if (!id.equals(dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Employee employee = employeeService.updateEmployee(dto);
            EmployeeRoDTO roEmployee = EmployeeMapper.mapToRoEmployee(employee);

            return ResponseEntity.ok(roEmployee);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeRoDTO> deleteEmployee(@PathVariable("id") Long id) {

        try {
            Employee employee = employeeService.deleteEmployee(id);
            EmployeeRoDTO roEmployee = EmployeeMapper.mapToRoEmployee(employee);

            return ResponseEntity.ok(roEmployee);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
