//package com.kezibot.service;
//
//import com.kezibot.model.User;
//import com.kezibot.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    public User registerUser(String username, String email, String password) {
//        // 检查用户名和邮箱是否已存在
//        if (userRepository.findByUsername(username) != null) {
//            throw new RuntimeException("Username already exists");
//        }
//        if (userRepository.findByEmail(email) != null) {
//            throw new RuntimeException("Email already exists");
//        }
//
//        User user = new User();
//        user.setUsername(username);
//        user.setEmail(email);
//        // 密码加密
//        user.setPassword(passwordEncoder.encode(password));
//
//        return userRepository.save(user);
//    }
//
//    public User authenticateUser(String username, String password) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        // 密码验证
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return user;
//    }
//
//    public User updateUserProfile(Long userId, String email, String newPassword) {
//        User user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (email != null && !email.isEmpty()) {
//            user.setEmail(email);
//        }
//
//        if (newPassword != null && !newPassword.isEmpty()) {
//            user.setPassword(passwordEncoder.encode(newPassword));
//        }
//
//        return userRepository.save(user);
//    }
//}
