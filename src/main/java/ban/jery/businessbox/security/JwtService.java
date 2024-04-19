package ban.jery.businessbox.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "sd87f6s8d7fc53f9dfw9sdf645n68sdf68sd7f6s87df6d8s7df6";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private Set<String> blacklist = new HashSet<>();

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
//                .claim("roles", populateAuthorities(user.getAuthorities()))
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public void invalidateToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenInvalid(String token) {
        return blacklist.contains(token);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token) // checks for expiration too
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities ) {
        Set<String> authoritiesSet = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            authoritiesSet.add(authority.getAuthority());
        }

        return String.join(",", authoritiesSet);
    }


}
