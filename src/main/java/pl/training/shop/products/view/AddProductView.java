package pl.training.shop.products.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.util.Date;
import java.util.List;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductCategory;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.view.CancelEvent;
import pl.training.shop.view.SaveEvent;

@Route("product/add-product")
public class AddProductView extends VerticalLayout {

  private final ProductService productService;
  private final ProductForm form;

  private Product product;
  private List<ProductCategory> productCategories;

  public AddProductView(ProductService productService) {
    this.productService = productService;

    product = new Product();
    product.setAvailableSince(new Date());

    productCategories = productService.getAllProductCategories();

    form = new ProductForm(product, productCategories);
    initProductForm();
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
}
