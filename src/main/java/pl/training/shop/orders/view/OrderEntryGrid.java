package pl.training.shop.orders.view;

import com.vaadin.flow.component.grid.Grid;
import pl.training.shop.orders.model.OrderEntry;

public class OrderEntryGrid extends Grid<OrderEntry> {

  public OrderEntryGrid() {
    initializeGrid();
  }

  private void initializeGrid() {
    addColumn(s -> s.getProduct().getName()).setHeader("Nazwa produktu");
    addColumn(OrderEntry::getQuantity).setHeader("Ilośc produktów");
    addColumn(s -> s.getProduct().getPrice()).setHeader("Cena produktu");
  }
}
