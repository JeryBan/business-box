package ban.jery.businessbox.dto.product;

import ban.jery.businessbox.dto.BaseDTO;
import ban.jery.businessbox.model.Business;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductUpdateDTO extends BaseDTO {

    @NotBlank(message = "Product name is required.")
    private String name;

    @NotBlank(message = "Product category is required.")
    private String category;

    private String description;
    private Float quantity;
    private Float price;
    private Business business;
}
