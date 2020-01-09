package pl.training.shop.products.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
