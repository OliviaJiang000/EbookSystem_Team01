package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User user = userService.registerUser(username, password, email);
        return ResponseEntity.ok(user);
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        return ResponseEntity.ok(user);
    }

    // 更新个人信息
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUserInfo(@PathVariable Long userId, @RequestParam String email) {
        User user = userService.updateUserInfo(userId, email);
        return ResponseEntity.ok(user);
    }

    // 设置用户角色（管理员或普通用户）
    @PutMapping("/{userId}/role")
    public ResponseEntity<User> setRole(@PathVariable Long userId, @RequestParam String role) {
        User user = userService.setRole(userId, role);
        return ResponseEntity.ok(user);
    }

    // 删除用户
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
