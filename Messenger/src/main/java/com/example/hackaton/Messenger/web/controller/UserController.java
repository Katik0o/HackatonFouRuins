package com.example.hackaton.Messenger.web.controller;
import com.example.hackaton.Messenger.model.LoginRequest;
//import com.example.hackaton.Messenger.model.UserDTO;
import com.example.hackaton.Messenger.service.UserService;
import com.example.hackaton.Messenger.spring.JwtResponse;
import com.example.hackaton.Messenger.spring.JwtUtils;
import com.example.hackaton.Messenger.spring.UserDetailsImpl;
import com.example.hackaton.Messenger.web.form.UserForm;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController

@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserService userService;

//    @Autowired
//    MailClient mailClient;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    private static UserForm userFormSt;
    private static String confirmCodeSt;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        ));
    }

    @PostMapping("/accept")
    public ResponseEntity<?> userRegistrationAccept(){

        return ResponseEntity.ok(UserDTO.toModel(userService.save(userFormSt)));
    }

    @GetMapping("/accept")
    public ResponseEntity<?> userRegistrationGetCode(){

        return ResponseEntity.ok(confirmCodeSt);

    }


}
