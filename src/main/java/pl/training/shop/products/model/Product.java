package pl.training.shop.products.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

  @GeneratedValue
  @Id
  private Long id;
  @Pattern(regexp = "[a-zA-Z]+")
  @NonNull
  private String name;
  @NotEmpty
  private String description;
  private long price;
  private Date availableSince;
  private int quantity;

  @JoinColumn(name = "category_id")
  @ManyToOne
  private ProductCategory category;

}
