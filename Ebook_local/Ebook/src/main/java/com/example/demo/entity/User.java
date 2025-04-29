package com.example.demo.entity;

import com.example.demo.service.UserService;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "name")
    private String username;
    @Column(name = "password_hash")
    private String password;
    @Column(unique = true)
    private String email;

    // 权限标识，ROLE_ADMIN 或 ROLE_USER
    private String role;

    // 权限列表，适应Spring Security的方式
    private List<GrantedAuthority> authorities;

    // 构造函数，根据需要添加
    public User(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities=authorities;
    }

    // 默认构造函数
    public User() {
    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

// 实现 UserDetails 接口的方法


    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将权限字符串转换为 SimpleGrantedAuthority
        return authorities;
    }

    public boolean isEnabled() {
        return true; // 设置账户是否启用
    }

}
