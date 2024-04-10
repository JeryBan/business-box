package ban.jery.businessbox.dto.product;

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
public class ProductUpdateDTO extends BaseDTO {

    @NotNull(message = "Name of the product is required.")
    private String name;

    private String category;
    private String description;
    private Float quantity;
    private Float price;
}
