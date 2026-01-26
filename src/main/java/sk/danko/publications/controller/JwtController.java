package sk.danko.publications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sk.danko.publications.entity.UserEntity;
import sk.danko.publications.model.JwtRequest;
import sk.danko.publications.model.JwtResponse;
import sk.danko.publications.model.UserModel;
import sk.danko.publications.service.impl.UserDetailServiceImpl;
import sk.danko.publications.util.JwtUtil;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class JwtController {

    public static final String REGISTER_URI = "/register";
    public static final String LOGIN_URI = "/login";
    public static final String USER_URI = "/currentUser";
    public static final String USER_LIST = "/users";



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(REGISTER_URI)
    public ResponseEntity<UserModel> register(@RequestBody UserModel userModel){
        UserModel userModel1 = userDetailService.register(userModel);
        ResponseEntity<UserModel> responseEntity = new ResponseEntity<>(userModel1, HttpStatus.CREATED);
        return responseEntity;
    }

    @PostMapping(LOGIN_URI)
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest){

        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
        authenticationManager.authenticate(upat);

        UserDetails userDetails = userDetailService.loadUserByUsername(jwtRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping(USER_URI)
    public UserModel getCurrentUser(Principal principal) {
        UserDetails userDetails =  this.userDetailService.loadUserByUsername(principal.getName());
        return (UserModel) userDetails;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(USER_LIST)
    @CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowedHeaders = {"Authorization", "x-requested-with", "x-xsrf-token"})
    public ResponseEntity getAll(){
        Iterable<UserEntity> list = userDetailService.getList();
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);

    }
}
