package banquemisr.challenge05.task.management.service;

import banquemisr.challenge05.task.management.entity.User;

import javax.validation.Valid;

public interface UserService {
    void registerUser(@Valid User user);
}
