package lk.ijse.cmjd.researchtracker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(String username, String password, String fullName, UserRole role) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User signup(String username, String email, String password, UserRole role) {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
       // user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
