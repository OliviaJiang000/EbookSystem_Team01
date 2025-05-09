package com.example.demo.EntityTest;

import com.example.demo.entity.User; // Import the User class

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserProperties() {
        User obj = new User();
        obj.setId(1L);
        obj.setUsername("testUsername");

        assertThat(obj.getId()).isEqualTo(1L);
        assertThat(obj.getUsername()).isEqualTo("testUsername");
    }
}
