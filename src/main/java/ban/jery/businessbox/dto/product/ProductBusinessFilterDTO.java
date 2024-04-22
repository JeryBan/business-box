package ban.jery.businessbox.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductBusinessFilterDTO {

    @NotBlank(message = "Business id is required.")
    private Long businessId;
    @NotBlank(message = "Business name is required.")
    private String businessName;
}
