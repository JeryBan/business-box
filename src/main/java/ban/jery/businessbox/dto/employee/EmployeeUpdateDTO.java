package ban.jery.businessbox.dto.employee;

import ban.jery.businessbox.dto.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeUpdateDTO extends BaseDTO {

    private String firstname;

    @NotNull(message = "Lastname is required")
    private String lastname;

    @Size(min = 10, max = 10, message = "Phone Number must be 10 digits.")
    private String phoneNumber;

    @Email(message = "Invalid email address.")
    private String email;

    private String filesPath;

}
