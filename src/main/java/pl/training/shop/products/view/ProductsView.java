package pl.training.shop.products.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.training.shop.products.model.Product;
import pl.training.shop.products.model.ProductService;

import java.util.List;

@Route("products")
public class ProductsView extends VerticalLayout {

    private final ProductService productService;
    private final ProductsGrid productsGrid = new ProductsGrid();

    private HorizontalLayout topPanel = new HorizontalLayout();

    @Autowired
    public ProductsView(ProductService productService) {
        this.productService = productService;

        initButtons();
        initProductsGrid();
    }

    private void initButtons() {
        Button add = new Button("Dodaj");
        Button edit = new Button("Edytuj");
        Button remove = new Button("Usuń");

        topPanel.add(add, edit, remove);
        add(topPanel);
    }

    private void initProductsGrid() {
        refreshProducts();
        add(productsGrid);
    }

    private void refreshProducts() {
        List<Product> products = productService.getAllProducts();
        productsGrid.setProducts(products);
    }
}
