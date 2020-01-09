package pl.training.shop.view;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import pl.training.shop.view.AppHello.AppHelloModel;

@Tag("app-hello")
@JsModule("./src/app-hello.js")
@Slf4j
public class AppHello extends PolymerTemplate<AppHelloModel> {

  public interface AppHelloModel extends TemplateModel {

    void setName(String name);

    String getName();
  }

  @Id("hello-btn")
  private Button helloButton;

  @EventHandler
  public void sayHello() {
    log.info("Hello Dear User :)");
    helloButton.setVisible(false);
    new Thread(() -> {
      try {
        Thread.sleep(3000);
        getUI().ifPresent(ui -> ui.access(() -> getModel().setName("Dear User :)")));
      } catch (InterruptedException e) {
        log.error(e.getMessage());
      }
    }).start();
  }
}
