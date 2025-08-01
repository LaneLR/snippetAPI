package com.verizon.springtesting.rest;


import com.verizon.springtesting.models.DBUser;
import com.verizon.springtesting.repository.UserRepo;
import com.verizon.springtesting.utils.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userrepo;

    @Autowired
    private JwtUtil jwtUtil;

    //create /auth/login route
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        DBUser user = userrepo.findByEmail(login.email());
        //check the input password against the hashed password in db

        if (user == null || !BCrypt.checkpw(login.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token) {}

}
