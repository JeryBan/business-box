package ban.jery.businessbox.dto.employee;

import ban.jery.businessbox.dto.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String lastname;

    @Length(min = 10, max = 10)
    private String phoneNumber;

    @Email
    private String email;

    private String filesPath;

}
