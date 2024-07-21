package com.dag.productservice.services.auth;

import com.dag.productservice.dao.SessionRepository;
import com.dag.productservice.dao.UserRepository;
import com.dag.productservice.dto.UserDTO;
import com.dag.productservice.models.Role;
import com.dag.productservice.models.Session;
import com.dag.productservice.models.SessionStatus;
import com.dag.productservice.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {

    private final SessionRepository sessionRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static String encodedSecret="Ats/z2iQhSLQpZD/BwYQVbAkLx9ir7I2BWJZ8yH4km0=";

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sessionRepository = sessionRepository;
    }

    public ResponseEntity<UserDTO> logIn(String eMail, String password) {
        Optional<User> userOptional = userRepository.findByEmail(eMail);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        byte[] decodedKey = Base64.getDecoder().decode(encodedSecret);
        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = Keys.hmacShaKeyFor(decodedKey);

        Map<String, Object> jsonForJwt  = new HashMap<>();
        jsonForJwt .put("email", user.getEmail());
        jsonForJwt .put("createdAt", System.currentTimeMillis());
        jsonForJwt .put("expiresAt", System.currentTimeMillis() + 3600000);

        String token= Jwts.builder()
                .claims(jsonForJwt)
                .signWith(key, alg)
                .compact();


        Session session = new Session();
        session.setStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDTO userDTO = UserDTO.from(user);

        MultiValueMapAdapter<String,String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        ResponseEntity<UserDTO> response= new ResponseEntity<>(userDTO, headers, HttpStatus.OK);
        return response;
    }


    public ResponseEntity<UserDTO> signUp(String eMail, String password) {
        User user = new User();
        user.setEmail(eMail);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        try{
            User saveduser = userRepository.save(user);
            UserDTO userDTO = UserDTO.from(saveduser);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    public ResponseEntity<Void> logOut(String token, Long userId) {
        Optional<Session> sessionOptional= sessionRepository.findByTokenAndUser_Id(token, userId);
        if(sessionOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Session session = sessionOptional.get();
        session.setStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }

    public SessionStatus validateToken(String token, Long userId) {
        Optional<Session> sessionOptional= sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty()){
            return SessionStatus.ENDED;
        }

        Session session = sessionOptional.get();
        if (!session.getStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.ENDED;
        }

        byte[] decodedKey = Base64.getDecoder().decode(encodedSecret);
        SecretKey key = Keys.hmacShaKeyFor(decodedKey);
        Jws<Claims> claimsJWS=Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseSignedClaims(token);

        String email = claimsJWS.getPayload().get("email", String.class);
        List<Role> roles = claimsJWS.getPayload().get("roles", List.class);
        Date createdAt=claimsJWS.getPayload().get("createdAt", Date.class);

        if(createdAt.before(new Date(System.currentTimeMillis()-3600000))){
            return SessionStatus.ENDED;
        }

        return SessionStatus.ACTIVE;
    }
}