package banquemisr.challenge05.task.management.service.impl;

import banquemisr.challenge05.task.management.entity.User;
import banquemisr.challenge05.task.management.exception.CustomException;
import banquemisr.challenge05.task.management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import banquemisr.challenge05.task.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import static banquemisr.challenge05.task.management.constant.ErrorMessages.USER_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.existsByUsername(user.getUsername()))
            throw new CustomException(USER_ALREADY_EXISTS, HttpStatus.CONFLICT);
        userRepository.save(user);
    }
}
