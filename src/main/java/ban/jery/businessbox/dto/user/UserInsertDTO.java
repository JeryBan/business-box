package ban.jery.businessbox.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @NotBlank(message = "username is required.")
    @Email(message = "Invalid email address.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;
}
