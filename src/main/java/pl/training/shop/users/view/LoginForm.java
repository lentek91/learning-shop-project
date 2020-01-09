package pl.training.shop.users.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import pl.training.shop.MainView;
import pl.training.shop.users.model.SecureService;

public class LoginForm extends VerticalLayout {

  private final SecureService secureService;

  private TextField login = new TextField();
  private PasswordField password = new PasswordField();
  private HorizontalLayout buttonsLayout = new HorizontalLayout();
  private Button logIntoApplication = new Button();
  private Button clear = new Button();
  private Label loginError = new Label();

  public LoginForm(SecureService secureService) {
    this.secureService = secureService;

    initFields();
    initButtons();
    add(loginError);
  }

  private void initFields() {
    login.setLabel(getTranslation("user.login"));
    password.setLabel(getTranslation("user.password"));
    add(login, password);
  }

  private void initButtons() {
    logIntoApplication.setText(getTranslation("login"));
    clear.setText(getTranslation("clear"));

    logIntoApplication.addClickListener(event -> logInto());
    clear.addClickListener(event -> {
      login.clear();
      password.clear();
    });

    buttonsLayout.add(logIntoApplication, clear);
    add(buttonsLayout);
  }

  private void logInto() {
    String login = this.login.getValue();
    String password = this.password.getValue();
    if (secureService.authenticate(login, password)) {
      UI.getCurrent().navigate(MainView.class);
    } else {
      loginError.setText(getTranslation("invalidLoginOrPassword"));
    }
  }
}
