package pl.training.shop.users.model;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final static String DEFAULT_ROLE_NAME = "ROLE_USER";

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public User addNewUser(User user) {
    encodePassword(user);
    assignDefaultRole(user);
    return userRepository.saveAndFlush(user);
  }

  private void assignDefaultRole(User user) {
    Optional<Role> role = roleRepository.findByAuthority(DEFAULT_ROLE_NAME);
    if (!role.isPresent()) {
      Role defaultRole = new Role(DEFAULT_ROLE_NAME);
      roleRepository.saveAndFlush(defaultRole);
      user.addRole(defaultRole);
    } else {
      user.addRole(role.get());
    }
  }

  private void encodePassword(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
