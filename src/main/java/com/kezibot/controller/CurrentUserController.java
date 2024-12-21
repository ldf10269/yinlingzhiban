package com.kezibot.controller;

import com.kezibot.model.User;
import com.kezibot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class CurrentUserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/current")
    public Map<String, Object> getCurrentUser() {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "YY";

        // 查找或创建用户
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setEmail(username + "@kezibot.com");
            user = userRepository.save(user);
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getEmail());

        return userInfo;
    }
}
