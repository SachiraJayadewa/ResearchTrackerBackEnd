package lk.ijse.cmjd.researchtracker.user;

import lk.ijse.cmjd.researchtracker.user.SignUpRequest;
import lk.ijse.cmjd.researchtracker.user.UserService;
import lk.ijse.cmjd.researchtracker.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signupUser(@RequestBody SignUpRequest request) {
        return userService.saveUser(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getRole()
        );
    }
}
