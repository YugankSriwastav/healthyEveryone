package shiva_care.healthify.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import shiva_care.healthify.dto.PatientDto;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 5;

    //Refresh Token Validate
    private static final long JWT_REFRESH_TOKEN = TimeUnit.DAYS.toMillis(15);
    // 🔑 Secret key (minimum 32 characters)
    private static final String SECRET_KEY =
            "your-256-bit-secret-your-256-bit-secret";

     public String generateToken(PatientDto patientDto){

         Map<String, Object> claims = new HashMap<>();
         claims.put("role", patientDto.getRole());
         claims.put("gmai", patientDto.getGmail());
         return createToken(claims, patientDto.getName());
     }

      String createToken(Map<String, Object> claims, String subject){

         return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(subject)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                 .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                 .compact();

     }

     private Key getSigningKey(){
         return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
     }

    public String extractUserName(String token) {
         return extractAllClaims(token).getSubject();

    }
    public Claims extractAllClaims(String token){
         return Jwts.parserBuilder()
                 .setSigningKey(getSigningKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }

    public boolean validateToken(String token, String userName) {
         final String extractedUsername = extractUserName(token);
         return ((extractedUsername.equals(userName)) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
         return extractedExpiration(token).before(new Date());
    }

    private Date extractedExpiration(String token){
         return extractAllClaims(token).getExpiration();
    }
}
