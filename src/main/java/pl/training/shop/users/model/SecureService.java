package pl.training.shop.users.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecureService {

  private final AuthenticationManager authenticationManager;

  public boolean authenticate(String login, String password) {
    Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
    try {
      logout();
      Authentication authenticationResult = authenticationManager.authenticate(authentication);
      SecurityContextHolder.clearContext();
      SecurityContextHolder.getContext().setAuthentication(authenticationResult);
      return true;
    } catch (Exception ex) {
      log.warn(ex.getMessage());
      return false;
    }
  }

  public void logout() {
    SecurityContextHolder.clearContext();
  }
}
