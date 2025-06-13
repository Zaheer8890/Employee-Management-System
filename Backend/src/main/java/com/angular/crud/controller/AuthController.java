package com.angular.crud.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Ensure session is created and stores authentication
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            System.out.println("✅ Session Created: " + session.getId());

            // Set JSESSIONID manually to ensure frontend gets it
            response.addHeader("Set-Cookie", "JSESSIONID=" + session.getId() + "; Path=/; HttpOnly; SameSite=None; Secure");

            return ResponseEntity.ok().body(Map.of("message", "Login successful", "user", authentication.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }




    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        System.out.println("Logging out.");
        session.invalidate();
        
        // Explicitly return a response body to avoid 204 No Content
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }


    // ✅ Check Session API (returns JSON boolean)
    @GetMapping("/checkSession")
    public ResponseEntity<?> checkSession(HttpSession session) {
        boolean isLoggedIn = session.getAttribute("user") != null;
        return ResponseEntity.ok(Map.of("isAuthenticated", isLoggedIn));
    }
}