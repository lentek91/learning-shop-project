package pl.training.shop.products.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    @GeneratedValue
    @Id
    private Long id;
    @NotNull
    private String name;
    private String description;
    private long price;
    private Date availableSince;
    private int quantity;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private ProductCategory category;

}
