package pl.training.shop.view;

import static com.vaadin.flow.data.binder.ValidationResult.error;
import static com.vaadin.flow.data.binder.ValidationResult.ok;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MinLengthValidator implements Validator<String> {

  private final int minLength;

  @Override
  public ValidationResult apply(String value, ValueContext valueContext) {
    return value.length() < minLength ? error("Value is too short") : ok();
  }
}
