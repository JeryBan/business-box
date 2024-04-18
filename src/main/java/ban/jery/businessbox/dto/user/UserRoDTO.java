package ban.jery.businessbox.dto.user;

import ban.jery.businessbox.dto.BaseDTO;
import ban.jery.businessbox.model.Business;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoDTO extends BaseDTO {

    @NotBlank(message = "username is required.")
    @Email(message = "Invalid email address.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;

    private Set<Business> businesses;

    public UserRoDTO(Long id, String email, String password, Set<Business> businesses) {
        this.email = email;
        this.password = password;
        this.businesses = businesses;
        setId(id);
    }
}
