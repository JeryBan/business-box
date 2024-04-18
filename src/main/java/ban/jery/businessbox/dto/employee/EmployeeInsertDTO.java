package ban.jery.businessbox.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class EmployeeInsertDTO {

    private String firstname;

    @NotBlank(message = "Lastname is required.")
    private String lastname;

    @Size(min = 10, max = 10, message = "Phone Number must be 10 digits.")
    private String phoneNumber;

    @Email(message = "Invalid email address.")
    private String email;

    private String filesPath;
}
