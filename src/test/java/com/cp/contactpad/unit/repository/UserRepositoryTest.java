package com.cp.contactpad.unit.repository;

import com.cp.contactpad.model.User;
import com.cp.contactpad.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.Assertions;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    private User signUp;

    @BeforeAll
    void setup() {
        User signUp = new User();
        signUp.setFirstName("Femi");
        signUp.setLastName("Ogunlana");
        signUp.setEmail("femi.ogun24@gmail.com");
        signUp.setPassword("Password!");
        signUp.setGender("male");
        signUp.setMobile_phone("09011855015");
        this.signUp = signUp;
    }

    @Test
    public void save() {
        User user = userRepository.save(this.signUp);
        Assertions.assertEquals("Femi", user.getFirstName());
        Assertions.assertEquals("Ogunlana", user.getLastName());
        Assertions.assertEquals("femi.ogun24@gmail.com", user.getEmail());
        Assertions.assertEquals("09011855015", user.getMobile_phone());
    }

    @Test
    public void findByEmail () {
        userRepository.save(this.signUp);
        Optional<User> user = userRepository.findByEmail("femi.ogun24@gmail.com");
        Assertions.assertEquals("Femi", user.get().getFirstName());
        Assertions.assertEquals("Ogunlana", user.get().getLastName());
        Assertions.assertEquals("femi.ogun24@gmail.com", user.get().getEmail());
        Assertions.assertEquals("09011855015", user.get().getMobile_phone());
    }

    @Test
    public void existsByEmail() {
        userRepository.save(this.signUp);
        Boolean exist = userRepository.existsByEmail("femi.ogun24@gmail.com");
        Assertions.assertTrue(exist);
    }
}
