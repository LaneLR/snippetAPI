package com.verizon.springtesting.rest;

import com.verizon.springtesting.models.DBUser;
import com.verizon.springtesting.repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping
    public DBUser createUser(@RequestBody DBUser user) {
        String hashPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashPass);

        return userRepo.save(user);
    }
}
