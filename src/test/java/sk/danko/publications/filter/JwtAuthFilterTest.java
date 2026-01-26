package sk.danko.publications.filter;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import sk.danko.publications.service.impl.UserDetailServiceImpl;
import sk.danko.publications.util.JwtUtil;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class JwtAuthFilterTest {

        @Mock
        private AuthenticationManager authenticationManager;

        @Mock
        private JwtUtil jwtUtil;

        @Mock
        private UserDetailServiceImpl userDetailService;

        @Mock
        private FilterChain filterChain;

        @InjectMocks
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtil, userDetailService);
        }

        @Test
        void testValidTokenSetsAuthentication() throws Exception {
            // Mock request/response
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();

            // Simulácia Bearer tokenu
            request.addHeader("Authorization", "Bearer valid-token");

            // Mock správania závislostí
            when(jwtUtil.extractUsername("valid-token")).thenReturn("testuser");

            UserDetails userDetails = new User("testuser", "password", List.of());
            when(userDetailService.loadUserByUsername("testuser")).thenReturn(userDetails);

            // Spusti filter
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            // Over, že sa zavolalo načítanie usera
            verify(userDetailService, times(1)).loadUserByUsername("testuser");
            verify(filterChain, times(1)).doFilter(request, response);
        }

        @Test
        void testInvalidTokenDoesNotSetAuthentication() throws Exception {
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();
            request.addHeader("Authorization", "Bearer invalid-token");

            when(jwtUtil.extractUsername("invalid-token")).thenThrow(new RuntimeException("Invalid token"));

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(userDetailService, never()).loadUserByUsername(anyString());
            verify(filterChain, times(1)).doFilter(request, response);
        }

        @Test
        void testNoAuthorizationHeader() throws Exception {
            MockHttpServletRequest request = new MockHttpServletRequest();
            MockHttpServletResponse response = new MockHttpServletResponse();

            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

            verify(userDetailService, never()).loadUserByUsername(anyString());
            verify(filterChain, times(1)).doFilter(request, response);
        }
    }


