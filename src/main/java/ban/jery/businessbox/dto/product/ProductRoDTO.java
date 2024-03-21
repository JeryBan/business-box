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
    private String description;
    private Double quantity;
    private Double price;

    public ProductRoDTO(Long id, String name, String description, Double quantity, Double price) {
        setId(id);
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
