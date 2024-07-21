package com.dag.auth;

import com.dag.productservice.dao.SessionRepository;
import com.dag.productservice.dao.UserRepository;
import com.dag.productservice.dto.UserDTO;
import com.dag.productservice.models.Session;
import com.dag.productservice.models.SessionStatus;
import com.dag.productservice.models.User;
import com.dag.productservice.services.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogInSuccessful() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(sessionRepository.save(any(Session.class))).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<UserDTO> response = authService.logIn(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders().get("Set-Cookie"));
    }

    @Test
    void testLogInInvalidPassword() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode("wrongpassword"));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(bCryptPasswordEncoder.matches(password, user.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.logIn(email, password));
        assertEquals("Invalid password", exception.getMessage());
    }

    @Test
    void testLogInUserNotFound() {
        String email = "test@example.com";
        String password = "password";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = authService.logIn(email, password);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSignUpSuccessful() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<UserDTO> response = authService.signUp(email, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testSignUpUserAlreadyExists() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException("User already exists"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> authService.signUp(email, password));
        assertEquals("User already exists", exception.getReason());
    }

    @Test
    void testLogOutSuccessful() {
        String token = "dummyToken";
        Long userId = 1L;
        Session session = new Session();
        session.setStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(new User());

        when(sessionRepository.findByTokenAndUser_Id(token, userId)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<Void> response = authService.logOut(token, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SessionStatus.ENDED, session.getStatus());
    }

    @Test
    void testLogOutSessionNotFound() {
        String token = "dummyToken";
        Long userId = 1L;

        when(sessionRepository.findByTokenAndUser_Id(token, userId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = authService.logOut(token, userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testValidateTokenSessionNotFound() {
        String token = "dummyToken";
        Long userId = 1L;

        when(sessionRepository.findByTokenAndUser_Id(token, userId)).thenReturn(Optional.empty());

        SessionStatus status = authService.validateToken(token, userId);

        assertEquals(SessionStatus.ENDED, status);
    }

}
