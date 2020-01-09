package pl.training.shop.products.model;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public List<ProductCategory> getAllProductCategories() {
    return productCategoryRepository.findAll();
  }

  public void addProduct(Product product) {
    productRepository.save(product);
  }

  public void removeProduct(Product product) {
    productRepository.delete(product);
  }

  public void updateProduct(Product product) {
    productRepository.saveAndFlush(product);
  }

  public Optional<Product> getProduct(Long productId) {
    return productRepository.findById(productId);
  }
  public void decreaseQuantity(Long productId, int amount) {
    productRepository.decreaseQuantity(productId, amount);
  }

  public void increaseQuantity(Long productId, int amount) {
    productRepository.increaseQuantity(productId, amount);
  }

  public Long getProductsCount() {
    return productRepository.count();
  }
}
