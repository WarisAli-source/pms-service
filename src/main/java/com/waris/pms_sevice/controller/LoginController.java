package com.waris.pms_sevice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waris.pms_sevice.auth.AuthResponse;
import com.waris.pms_sevice.auth.ErrorResponse;
import com.waris.pms_sevice.dto.LoginRequest;
import com.waris.pms_sevice.entity.Role;
import com.waris.pms_sevice.entity.User;
import com.waris.pms_sevice.auth.AuthRequest;
import com.waris.pms_sevice.service.UserService;
import com.waris.pms_sevice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/pms")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody String requestBody){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(requestBody);
            User user = new User();
            user.setUsername(jsonNode.get("username").asText());
            user.setEmail(jsonNode.get("email").asText());
            user.setPassword(jsonNode.get("password").asText());

            Set<Role> roles = new HashSet<>();
            JsonNode rolesNode = jsonNode.get("roles");
            if (rolesNode.isArray()) {
                for (JsonNode roleNode : rolesNode) {
                    Role role = new Role();
                    role.setName(roleNode.asText());
                    roles.add(role);
                }
            }
            user.setRoles(roles);
            if (userService.userExists(user.getUsername())) {
                return new ResponseEntity<>("User Already Exists", HttpStatus.BAD_REQUEST);
            }
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);

        } catch (Exception e) {
            System.out.println("Received request body: " + requestBody);
            e.printStackTrace();
            return new ResponseEntity<>("Error processing request: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());
        boolean isPasswordMatch = passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());
        if (!isPasswordMatch) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid username or password"));
        }
        boolean hasRole = userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(loginRequest.getRole()));
        if (!hasRole) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Access denied. Role mismatch."));
        }
        String jwtToken = jwtUtil.generateToken(userDetails);
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
