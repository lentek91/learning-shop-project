package pl.training.shop.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import pl.training.shop.view.ShopBottomMenu.ShopBottomMenuModel;

@Tag("shop-bottom-menu")
@JsModule("./src/shop-bottom-menu.js")
@Slf4j
public class ShopBottomMenu extends PolymerTemplate<ShopBottomMenuModel> {

  public interface ShopBottomMenuModel extends TemplateModel {

  }

  @Id("save-btn")
  Button save;

  @Id("cancel-btn")
  Button cancel;

  public void addSaveListener(ComponentEventListener<ClickEvent<Button>> saveListener) {
    save.addClickListener(saveListener);
  }

  public void addCancelListener(ComponentEventListener<ClickEvent<Button>> cancelListener) {
    cancel.addClickListener(cancelListener);
  }
}
