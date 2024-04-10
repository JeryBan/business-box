package ban.jery.businessbox.dto.product;

import ban.jery.businessbox.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRoDTO extends BaseDTO {

    private String name;
    private String category;
    private String description;
    private Float quantity;
    private Float price;

    public ProductRoDTO(Long id, String name, String category, String description, Float quantity, Float price) {
        setId(id);
        this.name = name;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
