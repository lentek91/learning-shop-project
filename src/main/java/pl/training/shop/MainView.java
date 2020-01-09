package pl.training.shop;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import pl.training.shop.orders.view.ShopCartView;
import pl.training.shop.products.model.ProductService;
import pl.training.shop.products.view.ProductsView;
import pl.training.shop.view.AppHello;

@Push
@Route
@Slf4j
public class MainView extends VerticalLayout implements BeforeLeaveObserver, AfterNavigationObserver {

  private final ProductService productService;

  private final HorizontalLayout buttonsLayout = new HorizontalLayout();
  private final Label productsCount = new Label();
  private Thread backgroundThread;

  public MainView(ProductService productService) {
    this.productService = productService;
    initButtons();
    add(productsCount);
    add(new AppHello());
  }

  private void initButtons() {
    Button products = new Button(getTranslation("products"));
    Button shopCart = new Button(getTranslation("shopCart"));
    products.addClickListener(event -> UI.getCurrent().navigate(ProductsView.class));
    shopCart.addClickListener(event -> UI.getCurrent().navigate(ShopCartView.class));

    buttonsLayout.add(products, shopCart);
    add(buttonsLayout);
  }

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    backgroundThread = new Thread(() -> {
      while (!backgroundThread.isInterrupted()) {
        attachEvent.getUI().access(this::updateProductsCounter);
        try {
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          log.error("Unregistered listeners");
          break;
        }
      }
    });
    backgroundThread.start();
  }

  private void updateProductsCounter() {
    String productsInfo = String.format("Products count: %d", productService.getProductsCount());
    productsCount.setText(productsInfo);
  }

  @Override
  protected void onDetach(DetachEvent detachEvent) {
    super.onDetach(detachEvent);
  }

  @Override
  public void beforeLeave(BeforeLeaveEvent beforeLeaveEvent) {
    backgroundThread.interrupt();
  }

  @Override
  public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
    updateProductsCounter();
  }
}
