package sk.danko.publications.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = "jwt.token.expirationInMS=100")
class JwtUtilTest {

    private JwtUtil jwtUtil;

    private final String secretKey = "YXNkZmFzZGZhc2RmYXNkZmFzZGZhc2RmYXNkZmFhc2Rm"; // base64-encoded random key
    private final int expirationInMs = 1000 * 60 * 60;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();


        Field secretKeyField = JwtUtil.class.getDeclaredField("SECRET_KEY");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtUtil, secretKey);

        Field expirationField = JwtUtil.class.getDeclaredField("tokenExpirationInMS");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtil, expirationInMs);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateTokenAndExtractUsername() {
        UserDetails userDetails = User.withUsername("tomas").password("pwd").roles("USER").build();

        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);

        String username = jwtUtil.extractUsername(token);
        assertEquals("tomas", username);
    }

    @Test
    void testIsTokenExpired() throws InterruptedException {
        UserDetails userDetails = User.withUsername("jana").password("pwd").roles("USER").build();
        String token = jwtUtil.generateToken(userDetails);

        assertTrue(jwtUtil.validateToken(token, userDetails));

        Thread.sleep(expirationInMs + 100);

        assertTrue(jwtUtil.extractExpiration(token).before(new Date()));
    }

    @Test
    void testValidateToken_ValidAndInvalidUser() {
        UserDetails userDetails = User.withUsername("tomas").password("pwd").roles("USER").build();
        String token = jwtUtil.generateToken(userDetails);

        assertTrue(jwtUtil.validateToken(token, userDetails));

        UserDetails otherUser = User.withUsername("jana").password("pwd").roles("USER").build();
        assertFalse(jwtUtil.validateToken(token, otherUser));
    }

    @Test
    void testExtractAllClaims_InvalidToken() {
        assertThrows(Exception.class, () -> {
            jwtUtil.extractAllClaims("invalid.token.value");
        });
    }
}
