package com.example.demo.ServiceTest;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthenticationManager authManager;
    private EmailService emailService;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);
        authManager = mock(AuthenticationManager.class);
        emailService = mock(EmailService.class);

        userService = new UserService();
        userService.getClass().getDeclaredFields();

        // 使用反射设置 @Autowired 字段
        inject(userService, "userRepository", userRepository);
        inject(userService, "encoder", passwordEncoder);
        inject(userService, "jwtUtil", jwtUtil);
        inject(userService, "authManager", authManager);
        inject(userService, "emailService", emailService);
    }

    private void inject(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    public void testRegisterDuplicateUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("exist");
        when(userRepository.existsByUsername("exist")).thenReturn(true);

        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("用户名已存在");
    }

    @Test
    public void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setUsername("user1");
        request.setPassword("123");

        User user = new User();
        user.setUsername("user1");
        user.setPassword("hashedPwd");
        user.setRole(Role.USER);

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any())).thenReturn("jwt-token");

        AuthResponse response = userService.login(request);

        assertThat(response.getToken()).isEqualTo("jwt-token");
    }

    @Test
    public void testLoginUserNotExist() {
        LoginRequest request = new LoginRequest();
        request.setUsername("nouser");
        request.setPassword("123");

        when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("用户名不存在");
    }

    
    @Test
    public void testGetCurrentUserSuccess() {
        User user = new User();
        user.setUsername("u1");
        user.setRole(Role.USER);

        when(jwtUtil.extractUsername("token")).thenReturn("u1");
        when(userRepository.findByUsername("u1")).thenReturn(Optional.of(user));

        User result = userService.getCurrentUser("token");

        assertThat(result.getUsername()).isEqualTo("u1");
    }

    @Test
    public void testUpdateEmailSuccess() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("old@example.com");

        when(jwtUtil.extractUsername("token")).thenReturn("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        userService.updateEmail("token", "new@example.com");

        assertThat(user.getEmail()).isEqualTo("new@example.com");
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateUserInfoSuccess() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("a@a.com");
        user.setPassword("encodedCurrent");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("new@a.com");
        request.setCurrentPassword("current");
        request.setNewPassword("new");

        when(jwtUtil.extractUsername("token")).thenReturn("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("current", "encodedCurrent")).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("encodedNew");

        userService.updateUserInfo("token", request);

        assertThat(user.getEmail()).isEqualTo("new@a.com");
        assertThat(user.getPassword()).isEqualTo("encodedNew");
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateUserInfoWrongPassword() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("encoded");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setCurrentPassword("wrong");
        request.setNewPassword("new");

        when(jwtUtil.extractUsername("token")).thenReturn("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThatThrownBy(() -> userService.updateUserInfo("token", request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("当前密码错误");
    }
}
