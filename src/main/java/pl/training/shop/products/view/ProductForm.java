package pl.training.shop.products.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.shared.Registration;
import java.util.List;
import lombok.Getter;
import lombok.extern.java.Log;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductCategory;
import pl.training.shop.view.CancelEvent;
import pl.training.shop.view.DoubleToIntegerConverter;
import pl.training.shop.view.MinLengthValidator;
import pl.training.shop.view.SaveEvent;

@Log
public class ProductForm extends FormLayout {

  private final Binder<Product> productBinder = new Binder<>(Product.class);
  private final BeanValidationBinder<Product> validationBinder = new BeanValidationBinder<>(Product.class);
  @Getter
  private final Product product;
  private final List<ProductCategory> productCategories;

  private TextField name = new TextField();
  private TextArea description = new TextArea();
  private TextField price = new TextField();
  private DatePicker availableDate = new DatePicker();
  private NumberField quantity = new NumberField();
  private ComboBox<ProductCategory> category = new ComboBox<>();

  private HorizontalLayout buttonLayout = new HorizontalLayout();
  private Button save = new Button();
  private Button cancel = new Button();

  public ProductForm(Product product, List<ProductCategory> productCategories) {
    this.product = product;
    this.productCategories = productCategories;

    initializeFields();
    initButtons();

    bindProducts();
    productBinder.readBean(product);
  }

  private void bindProducts() {
    productBinder.forField(name).asRequired("Name is required").bind(Product::getName, Product::setName);
    productBinder.forField(description).asRequired("Name is required").withValidator(new MinLengthValidator(3))
        .bind(Product::getDescription, Product::setDescription);
    productBinder.forField(price).asRequired("Name is required")
        .withConverter(new StringToLongConverter("Invalid price"))
        .withValidator(p -> p > 0, "Price is too low").bind(Product::getPrice, Product::setPrice);
    productBinder.forField(availableDate).withConverter(new LocalDateToDateConverter())
        .bind(Product::getAvailableSince, Product::setAvailableSince);
    productBinder.forField(quantity).withConverter(new DoubleToIntegerConverter())
        .bind(Product::getQuantity, Product::setQuantity);
    productBinder.forField(category).bind(Product::getCategory, Product::setCategory);

    validationBinder.forField(name).bind("name");
  }

  private void initializeFields() {
    name.setLabel("Name");
    description.setLabel("Description");
    price.setLabel("Price");
    availableDate.setLabel("Date");
    quantity.setLabel("Quantity");
    category.setLabel("Category");
    category.setItemLabelGenerator(ProductCategory::getName);
    category.setItems(productCategories);
    add(name, description, price, availableDate, quantity, category);

  }

  private void initButtons() {
    save.setText("Save");
    cancel.setText("Cancel");

    buttonLayout.add(save, cancel);

    save.addClickListener(this::onSave);
    cancel.addClickListener(this::onCancel);

    add(buttonLayout);
  }

  private void onSave(ClickEvent<Button> event) {
    validationBinder.validate();
    log.info("Status: " + validationBinder.isValid());
    try {
      validationBinder.writeBean(product);
    } catch (ValidationException e) {
      log.warning(e.getMessage());
      return;
    }
    productBinder.writeBeanIfValid(product);
    if (productBinder.isValid()) {
      fireEvent(new SaveEvent(this));
    }
  }

  private void onCancel(ClickEvent<Button> event) {
    fireEvent(new CancelEvent(this));
  }

  public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
    return addListener(SaveEvent.class, listener);
  }

  public Registration addCancelListener(ComponentEventListener<CancelEvent> listener) {
    return addListener(CancelEvent.class, listener);
  }
}
