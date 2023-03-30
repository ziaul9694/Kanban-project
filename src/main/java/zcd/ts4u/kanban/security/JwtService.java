package zcd.ts4u.kanban.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//This is a Java class named JwtService, which is a service that provides methods for generating and validating JSON Web Tokens (JWTs).
@Service
public class JwtService {

//    The class has a constant SECRET_KEY which is used to sign the JWTs. The getSignInKey() method decodes the secret key using Base64 decoding and returns a Key object that is used to sign and validate the JWTs.
    private static final String SECRET_KEY = "28482B4D6251655368566D597133743677397A24432646294A404E635266556A";

//    The extractUsername() method takes a token as input and returns the subject (i.e., username) of the token. This is done by calling extractClaim() with a Claims resolver function that gets the subject from the token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

//    The extractClaim() method takes a token and a Claims resolver function as input, and returns the value of the specified claim in the token. The resolver function is applied to the Claims object obtained from extractAllClaims() method.

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    The generateToken() method creates a new JWT with the specified extra claims (if any), subject (i.e., username), issued and expiration dates, and signs the token using the Key object obtained from getSignInKey(). The generated token is returned as a string.
    // if we don't have extra claims
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //The isTokenValid() method takes a token and a UserDetails object as input and checks if the token is valid by checking if the subject of the token matches the username of the UserDetails object, and if the token is not expired.
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

//    The extractAllClaims() method takes a token and returns the Claims object that contains all the claims in the token.
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
