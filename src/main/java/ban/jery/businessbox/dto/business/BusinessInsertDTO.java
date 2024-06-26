package ban.jery.businessbox.dto.business;

import ban.jery.businessbox.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessInsertDTO {

    @NotBlank(message = "Business name is required.")
    private String name;

    @NotBlank(message = "User email is required.")
    @Email
    private String email;


}
