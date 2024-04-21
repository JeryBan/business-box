package ban.jery.businessbox.dto.business;

import ban.jery.businessbox.dto.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessDeleteDTO extends BaseDTO {

    @NotBlank(message = "Business name is required.")
    private String name;

    @NotBlank(message = "User email is required.")
    @Email
    private String email;
}
