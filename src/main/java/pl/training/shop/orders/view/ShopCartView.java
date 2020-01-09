package pl.training.shop.orders.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pl.training.shop.MainView;
import pl.training.shop.orders.model.Order;
import pl.training.shop.orders.model.OrderEntry;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;

@Route("products/shopCart")
public class ShopCartView extends VerticalLayout {

  private final Order order;
  private final ProductService productService;
  private final OrderEntryGrid orderEntryGrid = new OrderEntryGrid();

  private Label summary = new Label();
  private HorizontalLayout buttonsLayout = new HorizontalLayout();
  private Button resignButton = new Button();
  private Button acceptButton = new Button();
  private Button returnToMain = new Button();

  public ShopCartView(Order order, ProductService productService) {
    this.order = order;
    this.productService = productService;

    initProductsGrid();
    initButtons();
  }

  private void initProductsGrid() {
    add(orderEntryGrid, summary);
    initGridData();
  }

  public void initGridData() {
    List<OrderEntry> orderEntries = new ArrayList<>(Collections.emptyList());
    order.getEntries().forEach((k, v) -> {
      Product product = productService.getProduct(k).orElse(null);
      orderEntries.add(new OrderEntry(product, v));
    });
    long totalValue = orderEntries.stream()
        .mapToLong(OrderEntry::getValue)
        .sum();

    orderEntryGrid.setItems(orderEntries);
    summary.setText("Total value: " + totalValue);
  }

  private void initButtons() {
    resignButton.setText("Resign");
    resignButton.addClickListener(event -> resign());
    acceptButton.setText("Accept");
    acceptButton.addClickListener(event -> acceptOrder());
    returnToMain.setText("Return to main");
    returnToMain.addClickListener(event -> UI.getCurrent().navigate(MainView.class));
    buttonsLayout.add(resignButton, acceptButton, returnToMain);
    add(buttonsLayout);
  }

  private void resign() {
    order.getEntries().forEach(productService::increaseQuantity);
    order.clear();
    UI.getCurrent().navigate(MainView.class);
  }

  private void acceptOrder() {
    order.clear();
    UI.getCurrent().navigate(MainView.class);
  }
}
