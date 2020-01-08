package pl.training.shop.products.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
