package sk.danko.publications.config;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sk.danko.publications.filter.JwtAuthenticationFilter;
import sk.danko.publications.repository.RoleRepository;
import sk.danko.publications.repository.UserRepository;
import sk.danko.publications.service.impl.UserDetailServiceImpl;
import sk.danko.publications.util.JwtUtil;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtConfig {


    private JwtAuthenticationFilter jwtFilter;


    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    private CustomAuthenticationProvider authProvider;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authManager, JwtUtil jwtUtil,
                                                           UserDetailServiceImpl userDetailService) {
        return new JwtAuthenticationFilter(authManager, jwtUtil, userDetailService);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {

        http
                .cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/role/roles", "/api/user/login", "/api/user/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/role/roleList").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    public UserDetailServiceImpl userDetailService() {
        return new UserDetailServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //
//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder =
//                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
//        return authenticationManagerBuilder.build();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");  // povoli Angular port
        configuration.addAllowedMethod("*");  // povoli všetky HTTP metódy (GET, POST, ...)
        configuration.addAllowedHeader("*");  // povoli všetky hlavičky
        configuration.setAllowCredentials(true);  // ak potrebuješ cookies alebo autorizáciu
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
