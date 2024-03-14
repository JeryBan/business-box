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
public class EmployeeRoDTO extends BaseDTO {

    private String firstname;

    @NotNull
    private String lastname;

    @Length(min = 10, max = 10)
    private String phoneNumber;

    @Email
    private String email;

    private String filesPath;

    public EmployeeRoDTO(Long id, String firstname, String lastname, String phoneNumber, String email, String filesPath) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.filesPath = filesPath;
        setId(id);
    }
}
