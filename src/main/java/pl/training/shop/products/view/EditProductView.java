package pl.training.shop.products.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import java.util.List;
import java.util.Optional;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductCategory;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.view.CancelEvent;
import pl.training.shop.view.SaveEvent;

@Route("product/edit-product")
public class EditProductView extends VerticalLayout implements HasUrlParameter<Long>, AfterNavigationObserver {

  private final ProductService productService;
  private ProductForm form;

  private Product product;
  private List<ProductCategory> productCategories;
  private Long productId;

  public EditProductView(ProductService productService) {
    this.productService = productService;
    this.productCategories = productService.getAllProductCategories();
  }

  private void initProductForm() {
    form.addSaveListener(this::onProductFormSave);
    form.addCancelListener(this::onProductFormCancel);
    form.setWidth("300px");
    add(form);
  }

  private void onProductFormSave(SaveEvent saveEvent) {
    Product product = form.getProduct();
    productService.updateProduct(product);
    showProducts();
  }

  private void onProductFormCancel(CancelEvent cancelEvent) {
    showProducts();
  }

  private void showProducts() {
    UI.getCurrent().navigate(ProductsView.class);
  }

  @Override
  public void setParameter(BeforeEvent beforeEvent, Long productId) {
    this.productId = productId;
  }

  @Override
  public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
    Optional<Product> product = productService.getProduct(productId);
    if (product.isPresent()) {
      form = new ProductForm(product.get(), productCategories);
      initProductForm();
    } else {
      showProducts();
    }
  }
}
