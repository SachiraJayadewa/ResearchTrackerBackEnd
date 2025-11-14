package lk.ijse.cmjd.researchtracker.auth;

import lk.ijse.cmjd.researchtracker.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/signup")
    public String register(@RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.MEMBER);
        user.setCreatedAt(LocalDateTime.now());
        userRepo.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Object login(@RequestBody User request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userRepo.findByUsername(request.getUsername()).orElseThrow();

            // Now include userId + role + username inside JWT
            return jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().name());

        } catch (BadCredentialsException e) {
            return "Invalid username or password!";
        } catch (AuthenticationException e) {
            return "Authentication failed: " + e.getMessage();
        }
    }
}
