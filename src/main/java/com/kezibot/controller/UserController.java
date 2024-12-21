package com.kezibot.controller;

import com.kezibot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    //@Autowired
    //private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
        @RequestParam String username, 
        @RequestParam String email, 
        @RequestParam String password
    ) {
        try {
            //User user = userService.registerUser(username, email, password);
            User user = new User();
            user.setUsername("yy");
            user.setEmail("eami");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(
        @RequestParam String username, 
        @RequestParam String password
    ) {
        try {
            User user = new User();
            user.setUsername("yy");
            user.setEmail("eami");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(
        @RequestParam Long userId, 
        @RequestParam(required = false) String email, 
        @RequestParam(required = false) String newPassword
    ) {
        try {
            User user = new User();
            user.setUsername("yy");
            user.setEmail("eami");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
