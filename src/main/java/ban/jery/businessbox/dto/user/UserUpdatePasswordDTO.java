package ban.jery.businessbox.dto.user;

import ban.jery.businessbox.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdatePasswordDTO extends BaseDTO {

    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;
}
