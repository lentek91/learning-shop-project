package pl.training.shop.products.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCategory {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;
    private String description;
}
