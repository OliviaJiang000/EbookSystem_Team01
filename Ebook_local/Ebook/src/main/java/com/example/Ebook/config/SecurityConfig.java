package com.example.Ebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 关闭CSRF（开发阶段）
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/users/register", "/users/login","/books/**").permitAll()  // 允许注册和登录
//                                .requestMatchers().authenticated()  // 需要登录权限才能访问书籍管理
                                .requestMatchers("/users/**","/admin/**").hasRole("ADMIN")  // 只有管理员可以管理用户
                                .anyRequest().authenticated()  // 其他请求都需要认证
                )
                .formLogin(form -> form
                        .loginPage("/login")  // 自定义登录页面
                        .defaultSuccessUrl("/books", true) // 登录成功后跳转到目录
                        .permitAll()  // 允许所有用户访问登录页
                )
           ;

        return http.build();
    }
}
