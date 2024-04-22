package ban.jery.businessbox.dto.employee;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.Employee;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeMapper {

    public static Employee mapToEmployee(EmployeeInsertDTO dto, Business business) {
        return new Employee(null, dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber(), dto.getEmail(), dto.getFilesPath(), business);
    }

    public static Employee mapToEmployee(EmployeeUpdateDTO dto) {
        return new Employee(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getPhoneNumber(), dto.getEmail(), dto.getFilesPath(), dto.getBusiness());
    }

    public static EmployeeRoDTO mapToRoEmployee(Employee employee) {
        return new EmployeeRoDTO(employee.getId(), employee.getFirstname(), employee.getLastname(), employee.getPhoneNumber(), employee.getEmail(), employee.getFilesPath());
    }
}
