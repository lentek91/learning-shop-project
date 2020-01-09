package pl.training.shop.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role implements GrantedAuthority {

  @GeneratedValue
  @Id
  private Long id;
  @NonNull
  @Column(unique = true)
  private String authority;
}
