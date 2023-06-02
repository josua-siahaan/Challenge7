package com.binar.challenge4.controller;

import com.binar.challenge4.JWT.AuthenticationResponse;
import com.binar.challenge4.JWT.JwtUtils;
import com.binar.challenge4.DTO.LoginRequest;
import com.binar.challenge4.DTO.SignupRequest;
import com.binar.challenge4.Response.MessageResponse;
import com.binar.challenge4.Response.UserInfoResponse;
import com.binar.challenge4.repository.RoleRepository;
import com.binar.challenge4.repository.UserRepository;
import com.binar.challenge4.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticationResponse authenticationResponse = authService.authenticateUser(loginRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authenticationResponse.getJwt())
                .body(new UserInfoResponse(authenticationResponse.getId(),
                        authenticationResponse.getUsername(),
                        authenticationResponse.getEmail(),
                        authenticationResponse.getRoles(),
                        authenticationResponse.getJwt()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User Registered Successful"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}