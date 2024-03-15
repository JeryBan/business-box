package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.employee.EmployeeInsertDTO;
import ban.jery.businessbox.dto.employee.EmployeeMapper;
import ban.jery.businessbox.dto.employee.EmployeeRoDTO;
import ban.jery.businessbox.dto.employee.EmployeeUpdateDTO;
import ban.jery.businessbox.model.Employee;
import ban.jery.businessbox.service.employee.IEmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private final IEmployeeService service;

    @Autowired
    public EmployeeRestController(IEmployeeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<List<EmployeeRoDTO>> getAllEmployees() {
        List<Employee>  employees;
        List<EmployeeRoDTO> roEmployees = new ArrayList<>();

        try {
            employees = service.getAllEmployees();
            for (Employee employee : employees) {
                roEmployees.add(EmployeeMapper.mapToRoEmployee(employee));
            }

            return ResponseEntity.ok(roEmployees);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/{lastname}")
    public ResponseEntity<List<EmployeeRoDTO>> getEmployeeByLastname(@PathVariable("lastname") String lastname) {
        List<Employee> employees;
        List<EmployeeRoDTO> roEmployees = new ArrayList<>();

        try {
            employees = service.getEmployeeByLastname(lastname);
            for (Employee employee : employees) {
                roEmployees.add(EmployeeMapper.mapToRoEmployee(employee));
            }

            return ResponseEntity.ok(roEmployees);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> insertEmployee(@Valid @RequestBody EmployeeInsertDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError er : bindingResult.getFieldErrors()) {
                errors.add(er.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Employee employee = service.insertEmployee(dto);
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
    public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeUpdateDTO dto, BindingResult bindingResult) {
        if (!Objects.equals(id, dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();

            for (FieldError er : bindingResult.getFieldErrors()) {
                errors.add(er.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Employee employee = service.updateEmployee(dto);
            EmployeeRoDTO roEmployee = EmployeeMapper.mapToRoEmployee(employee);

            return ResponseEntity.ok(roEmployee);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeRoDTO> deleteEmployee(@PathVariable("id") Long id) {

        try {
            Employee employee = service.deleteEmployee(id);
            EmployeeRoDTO roEmployee = EmployeeMapper.mapToRoEmployee(employee);

            return ResponseEntity.ok(roEmployee);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
