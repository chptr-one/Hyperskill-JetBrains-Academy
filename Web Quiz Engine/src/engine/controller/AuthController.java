package engine.controller;

import engine.WebQuizEngine;
import engine.model.User;
import engine.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    private final Logger logger = WebQuizEngine.logger;

    @Autowired
    private UserService service;

    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        logger.info("Try to register user {}", user.getUsername());
        service.registerNewUser(user.getUsername(), user.getPassword());
    }
}
