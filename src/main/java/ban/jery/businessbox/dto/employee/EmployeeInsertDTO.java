package ban.jery.businessbox.dto.employee;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class EmployeeInsertDTO {

    private String firstname;

    @NotNull
    private String lastname;

    @Length(min = 10, max = 10)
    private String phoneNumber;

    @Email
    private String email;

    private String filesPath;
}
