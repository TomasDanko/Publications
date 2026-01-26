package sk.danko.publications.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sk.danko.publications.service.impl.PublicationServiceImpl;
import sk.danko.publications.service.impl.UserDetailServiceImpl;
import sk.danko.publications.util.JwtUtil;

import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private PublicationServiceImpl publicationService;

    @Autowired
    private final AuthenticationManager authManager;



    @Autowired
    private UserDetailServiceImpl userDetailService;



    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authManager, JwtUtil jwtUtil, UserDetailServiceImpl userDetailService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //get the jwt token from request header
        //validate that jwt token
        String bearerToken = request.getHeader("Authorization");
        String username = null;
        String token = null;

        //check if token exist or has Bearer text
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){

            //extract jwt token from bearerToken
            token = bearerToken.substring(7);

            try{
                //extract username from the token
                username = jwtUtil.extractUsername(token);

                //get userdetails for this user
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                //security checks
                if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null){

                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(upat);

                }else {
                    System.out.println("Invalid Token!!");
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }else {
            System.out.println("Invalid Bearer Token Format!!");
        }

        //if all is well forward the filter request to the request endpoint

        HttpServletRequest requests = (HttpServletRequest) request;
        HttpServletResponse responses = (HttpServletResponse) response;

        responses.setHeader("Access-Control-Allow-Origin", requests.getHeader("Origin"));
        responses.setHeader("Access-Control-Allow-Credentials", "true");
        responses.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responses.setHeader("Access-Control-Max-Age", "3600");
        responses.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        filterChain.doFilter(request, response);
    }
}
