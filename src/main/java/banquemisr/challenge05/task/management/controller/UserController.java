package banquemisr.challenge05.task.management.controller;

import banquemisr.challenge05.task.management.entity.User;
import banquemisr.challenge05.task.management.model.ConfirmationResponse;
import banquemisr.challenge05.task.management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static banquemisr.challenge05.task.management.constant.ErrorMessages.USER_CREATED_SUCCESSFULLY;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ConfirmationResponse> registerUser(@Valid @RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok(new ConfirmationResponse(USER_CREATED_SUCCESSFULLY));
    }
}
