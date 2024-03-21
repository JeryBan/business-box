package ban.jery.businessbox.service.product;

import ban.jery.businessbox.dto.product.ProductInsertDTO;
import ban.jery.businessbox.dto.product.ProductMapper;
import ban.jery.businessbox.dto.product.ProductUpdateDTO;
import ban.jery.businessbox.model.Product;
import ban.jery.businessbox.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repo;

    @Autowired
    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public Product insertProduct(ProductInsertDTO dto) throws Exception {
        Product product;

        try {
            product = repo.save(ProductMapper.mapToProduct(dto));

            if (product == null) throw new Exception("error - insert product");
            log.info("product with id: " + product.getId() + " inserted successfully.");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return product;
    }

    @Override
    @Transactional
    public Product updateProduct(ProductUpdateDTO dto) throws EntityNotFoundException {
        Product product;

        try {
            product = repo.save(ProductMapper.mapToProduct(dto));

            if (product == null) throw new EntityNotFoundException("Product not found - id: " + dto.getId());
            log.info("product with id: " + product.getId() + " updated successfully.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return product;
    }

    @Override
    @Transactional
    public Product deleteProduct(Long id) throws EntityNotFoundException {
        Optional<Product> product;

        try {
            product = repo.findById(id);

            if (product.isEmpty()) throw new EntityNotFoundException("Product not found - id: " + id);
            log.info("product with id: " + id + " deleted successfully.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return product.orElseThrow();
    }

    @Override
    public List<Product> getProductByNameStartingWith(String name) throws EntityNotFoundException {
        List<Product> products = new ArrayList<>();

        try {
            products = repo.findProductByNameStartingWith(name);

            if (products.isEmpty()) throw new EntityNotFoundException("No products found");
            log.info("Product(s) found.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return products;
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        List<Product> products;

        try {
            products = repo.findAll();
            log.info("Products fetched successfully.");

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return products;
    }
}
