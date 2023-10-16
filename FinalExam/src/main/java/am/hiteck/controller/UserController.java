package am.hiteck.controller;

import am.hiteck.model.User;
import am.hiteck.model.dto.request.UserRequestDto;
import am.hiteck.service.UserService;
import am.hiteck.util.exceptions.DuplicateException;
import am.hiteck.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<User> getById(@RequestParam int id) throws NotFoundException {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getOnlyActiveUsers() {
        List<User> list = userService.getOnlyActiveUsers();
        return ResponseEntity.ok(list);
    }

    @PreAuthorize("hasAuthority('HR') OR hasAuthority('PM')")
    @GetMapping("/users/hr-pm")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getAll();
        return ResponseEntity.ok(list);
    }


    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody @Valid UserRequestDto requestDto) throws DuplicateException {

        userService.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROOT_ADMIN')")
    @PatchMapping("/change")
    public ResponseEntity<Void> change(@RequestParam int id) {
        userService.change(id);
        return ResponseEntity.ok().build();
    }

}
