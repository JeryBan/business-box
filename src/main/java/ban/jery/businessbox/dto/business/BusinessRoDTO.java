package ban.jery.businessbox.dto.business;

import ban.jery.businessbox.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessRoDTO extends BaseDTO {

    private Long id;

    @NotNull(message = "Business name is required")
    private String name;

}
