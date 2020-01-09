package pl.training.shop.users.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.training.shop.users.model.SecureService;

@Route("login")
public class LoginView extends VerticalLayout {

  private final SecureService secureService;

  public LoginView(SecureService secureService) {
    this.secureService = secureService;
    LoginForm loginForm = new LoginForm(secureService);
    add(loginForm);
  }
}
