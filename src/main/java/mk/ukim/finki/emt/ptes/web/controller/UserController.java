package mk.ukim.finki.emt.ptes.web.controller;

import mk.ukim.finki.emt.ptes.model.User;
import mk.ukim.finki.emt.ptes.model.dto.UserDto;
import mk.ukim.finki.emt.ptes.model.dto.maper.UserMapper;
import mk.ukim.finki.emt.ptes.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        List<User> list = userService.findAll();
        return userMapper.toListUserDto(list);
    }

    @GetMapping("/create")
    public User createUser() {
        return userService.createFactory();
    }

    @GetMapping({"/create-users", "/create-users/", "/create-users/{number}"})
    public List<User> createUsers(@PathVariable(required = false) Integer number) {
        int count = (number == null) ? 20 : number;
        return userService.createUsers(count);
    }

    @GetMapping("/buy")
    public ResponseEntity<?> buy() {
        try {
            return ResponseEntity.ok(userService.buy());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
