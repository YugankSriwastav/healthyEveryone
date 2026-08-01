package shiva_care.healthify.service.token;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    Set<String> blockedToken = ConcurrentHashMap.newKeySet();

  public void blockToken(String token){
      blockedToken.add(token);
  }
  public boolean isBlacklist(String token){
      return blockedToken.contains(token);
  }
}
