package com.waris.pms_sevice.controller;

import com.waris.pms_sevice.auth.AuthResponse;
import com.waris.pms_sevice.entity.User;
import com.waris.pms_sevice.auth.AuthRequest;
import com.waris.pms_sevice.service.UserService;
import com.waris.pms_sevice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pms")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.userExists(user.getUsername())) {
            return new ResponseEntity<>("User Already Exists", HttpStatus.BAD_REQUEST);
        }
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.status(201).body(registeredUser);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        final String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
    @GetMapping("/getAllRegisteredUsers")
    public ResponseEntity<?> getAllRegisteredUsers() {
        List<User> registeredUser = userService.getAllUsers();
        return ResponseEntity.status(201).body(registeredUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out successfully. Please clear your token.");
    }
}
