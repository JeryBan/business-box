package ban.jery.businessbox.service.product;

import ban.jery.businessbox.dto.product.ProductInsertDTO;
import ban.jery.businessbox.dto.product.ProductMapper;
import ban.jery.businessbox.dto.product.ProductUpdateDTO;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.Product;
import ban.jery.businessbox.repositories.BusinessRepository;
import ban.jery.businessbox.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final BusinessRepository businessRepo;
    private final ProductRepository productRepo;

    @Override
    @Transactional
    public Product insertProductToBusiness(ProductInsertDTO dto) throws EntityNotFoundException {

        try {
            Business business = businessRepo.findById(dto.getBusiness().getId())
                    .orElseThrow( () -> new EntityNotFoundException("Business not found"));

            Product product = ProductMapper.mapToProduct(dto, business);
            product = productRepo.save(product);
            log.info("Product with id: " + product.getId() + " inserted successfully.");

            business.getProducts().add(product);
            log.info("Product: " + product.getName() + " added to Business: " + business.getName());

            return product;

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Product updateProduct(ProductUpdateDTO dto) throws EntityNotFoundException {
        Product newProduct;

        try {
            Business business = businessRepo.findById(dto.getBusiness().getId())
                    .orElseThrow( () -> new EntityNotFoundException("Business for product not found or mismatch"));

            Product oldProduct = productRepo.findById(dto.getId())
                    .orElseThrow( () -> new EntityNotFoundException("Product not found - id: " + dto.getId()));

            if (!oldProduct.getBusiness().getId().equals(business.getId())) {
                throw new EntityNotFoundException("Business mismatch for employee - id: " + dto.getId());
            }

            business.getProducts().remove(oldProduct);
            newProduct = productRepo.save(ProductMapper.mapToProduct(dto));
            business.getProducts().add(newProduct);

            log.info("Product with id: " + newProduct.getId() + " updated successfully.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return newProduct;
    }

    @Override
    @Transactional
    public Product deleteProduct(Long id) throws EntityNotFoundException {
        Business business;

        try {
            Product product = productRepo.findById(id)
                    .orElseThrow( () -> new EntityNotFoundException("Product not found - id: " + id));

            business = product.getBusiness();
            business.getProducts().remove(product);

            productRepo.deleteById(id);
            log.info("Product with id: " + id + " deleted successfully.");

            return product;

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Product> getProductByName(String name) throws EntityNotFoundException {
        List<Product> products;

        try {
            products = productRepo.findByNameStartingWith(name);
            if (products.isEmpty()) throw new EntityNotFoundException("No products found.");

            log.info("Product(s) found.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return products;
    }

    @Override
    public List<Product> getAllProducts(Long businessId) throws EntityNotFoundException {
        List<Product> products;

        try {
            Business business = businessRepo.findById(businessId)
                    .orElseThrow( () -> new EntityNotFoundException("Business not found"));

            products = new ArrayList<>(business.getProducts());
            log.info("Products fetched successfully.");

        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        }
        return products;
    }
}
