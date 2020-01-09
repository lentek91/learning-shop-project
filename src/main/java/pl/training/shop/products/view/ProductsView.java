package pl.training.shop.products.view;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.MainView;
import pl.training.shop.orders.model.Order;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.view.DisposableEventBus;

@Route("products")
public class ProductsView extends VerticalLayout {

  private static final int DEFAULT_QUANTITY = 1;

  private final ProductService productService;
  private final Order order;
  private final DisposableEventBus disposableEventBus;

  private ProductsGrid productsGrid = new ProductsGrid();
  private HorizontalLayout topPanel = new HorizontalLayout();
  private Button add = new Button("Dodaj");
  private Button edit = new Button("Edytuj");
  private Button remove = new Button("Usuń");
  private Button addToShopCart = new Button("Dodaj do koszyka");
  private Button returnToMain = new Button("Strona główna");

  private Product selectedProduct;

  @Autowired
  public ProductsView(ProductService productService, Order order,
      DisposableEventBus disposableEventBus) {
    this.productService = productService;
    this.order = order;
    this.disposableEventBus = disposableEventBus;

    initButtons();
    initProductsGrid();
  }

  private void initButtons() {
    UI ui = UI.getCurrent();
    add.addClickListener(event -> ui.navigate(AddProductView.class));
    edit.addClickListener(event -> onEditProduct());
    remove.addClickListener(event -> onRemoveProduct());
    addToShopCart.addClickListener(event -> addProductToShopCart());
    returnToMain.addClickListener(event -> ui.navigate(MainView.class));

    setVisibility();

    topPanel.add(add, edit, remove, addToShopCart, returnToMain);
    add(topPanel);
  }

  private void addProductToShopCart() {
    productService.decreaseQuantity(selectedProduct.getId(), DEFAULT_QUANTITY);
    order.add(selectedProduct.getId(), DEFAULT_QUANTITY);
    disposableEventBus.fireEvent(new ProductQuantityChangeEvent());
  }

  private void onEditProduct() {
    UI ui = UI.getCurrent();
    String productUrl = RouteConfiguration.forApplicationScope().getUrl(EditProductView.class, selectedProduct.getId());
    ui.navigate(productUrl);
  }

  private void onRemoveProduct() {
    ConfirmDialog confirmDialog = new ConfirmDialog("Warning", "Are you sure?", "Yes",
        event -> removeSelectedProduct(), "No", event -> {
    });
    confirmDialog.open();
  }

  private void removeSelectedProduct() {
    productService.removeProduct(selectedProduct);
    refreshProducts();
  }

  private void initProductsGrid() {
    productsGrid.addProductSelectedListener(event -> onProductSelected(event.getProduct()));
    refreshProducts();
    add(productsGrid);
  }

  private void onProductSelected(Product product) {
    this.selectedProduct = product;

    setVisibility();
  }

  private void refreshProducts() {
    List<Product> products = productService.getAllProducts();
    productsGrid.setProducts(products);
  }

  private void setVisibility() {
    edit.setVisible(selectedProduct != null);
    remove.setVisible(selectedProduct != null);
    addToShopCart.setVisible(selectedProduct != null);
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    UI ui = attachEvent.getUI();
    disposableEventBus.on(ProductQuantityChangeEvent.class, event -> {
      ui.access(this::refreshProducts);
    });
  }
}
