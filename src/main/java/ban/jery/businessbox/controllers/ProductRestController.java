package ban.jery.businessbox.controllers;

import ban.jery.businessbox.dto.product.ProductInsertDTO;
import ban.jery.businessbox.dto.product.ProductMapper;
import ban.jery.businessbox.dto.product.ProductRoDTO;
import ban.jery.businessbox.dto.product.ProductUpdateDTO;
import ban.jery.businessbox.model.Product;
import ban.jery.businessbox.service.product.IProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductRestController {

    private final IProductService service;

    @GetMapping("/{businessId}")
    public ResponseEntity<List<ProductRoDTO>> getAllProducts(@PathVariable("businessId") Long businessId){
        List<Product> products;
        List<ProductRoDTO> roProducts = new ArrayList<>();

        try {
            products = service.getAllProducts(businessId);
            for (Product product : products) {
                roProducts.add(ProductMapper.mapToRoProduct(product));
            }

            return ResponseEntity.ok(roProducts);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductRoDTO>> getProductByName(@PathVariable("name") String name) {
        List<Product> products;
        List<ProductRoDTO> roProducts = new ArrayList<>();

        try {
            products = service.getProductByName(name);
            for (Product product : products) {
                roProducts.add(ProductMapper.mapToRoProduct(product));
            }

            return ResponseEntity.ok(roProducts);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ProductRoDTO> insertProductToBusiness(@Valid @RequestBody ProductInsertDTO dto) {

        try {
            Product product = service.insertProductToBusiness(dto);
            ProductRoDTO roProduct = ProductMapper.mapToRoProduct(product);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(roProduct.getId())
                    .toUri();

            return ResponseEntity.created(location).body(roProduct);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRoDTO> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductUpdateDTO dto) {
        if (!id.equals(dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Product product = service.updateProduct(dto);
            ProductRoDTO roProduct = ProductMapper.mapToRoProduct(product);

            return ResponseEntity.ok(roProduct);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductRoDTO> deleteProduct(@PathVariable("id") Long id) {

        try {
            Product product = service.deleteProduct(id);
            ProductRoDTO roProduct = ProductMapper.mapToRoProduct(product);

            return ResponseEntity.ok(roProduct);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
