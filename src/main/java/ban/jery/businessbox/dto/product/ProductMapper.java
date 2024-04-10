package ban.jery.businessbox.dto.product;

import ban.jery.businessbox.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {

    public static Product mapToProduct(ProductInsertDTO dto) {
        return new Product(null, dto.getName(), dto.getCategory(), dto.getDescription(), dto.getQuantity(), dto.getPrice());
    }

    public static Product mapToProduct(ProductUpdateDTO dto) {
        return new Product(dto.getId(), dto.getName(), dto.getCategory(), dto.getDescription(), dto.getQuantity(), dto.getPrice());
    }

    public static ProductRoDTO mapToRoProduct(Product product) {
        return new ProductRoDTO(product.getId(), product.getName(), product.getCategory(), product.getDescription(), product.getQuantity(), product.getPrice());
    }
}
