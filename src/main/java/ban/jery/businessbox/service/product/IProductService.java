package ban.jery.businessbox.service.product;

import ban.jery.businessbox.dto.product.ProductInsertDTO;
import ban.jery.businessbox.dto.product.ProductUpdateDTO;
import ban.jery.businessbox.model.Product;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IProductService {

    Product insertProduct(ProductInsertDTO dto) throws Exception;

    Product updateProduct(ProductUpdateDTO dto) throws EntityNotFoundException;

    Product deleteProduct(Long id) throws EntityNotFoundException;

    List<Product> getProductByName(String name) throws EntityNotFoundException;

    List<Product> getAllProducts() throws Exception;
}
