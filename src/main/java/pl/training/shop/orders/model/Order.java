package pl.training.shop.orders.model;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@VaadinSessionScope
@Component
@Slf4j
public class Order {

  @Getter
  private Map<Long, Integer> entries = new HashMap<>();

  public void add(Long productId, Integer quantity) {
    int currentQuantity = entries.getOrDefault(productId, 0);
    entries.put(productId, currentQuantity + quantity);
  }

  public void clear() {
    entries.clear();
  }

  @PostConstruct
  public void init() {
    log.info("Order initialized");
  }

  @PreDestroy
  public void destroy() {
    log.info("Order destroyed");
  }
}
