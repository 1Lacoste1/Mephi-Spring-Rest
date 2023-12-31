package ru.hpclab.bd.module1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.hpclab.bd.module1.Module1Application;
import ru.hpclab.bd.module1.model.User;
import ru.hpclab.bd.module1.repository.UserRepository;


import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Module1Application.class)
@AutoConfigureMockMvc
public final class UserControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.clear();
    }

    @Test
    public void get_should_returnUser_when_userExists() throws Exception {
        Random random = new Random();
        User user = userRepository.save(new User(UUID.randomUUID(), "new super user", "new super year of birthday" , random.nextInt(1000)));
        String expectedJson = objectMapper.writeValueAsString(user);

        mvc.perform(get("/users/" + user.getIdentifier()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
