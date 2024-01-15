package com.example.todolist.task.repository;

import com.example.todolist.task.model.Task;
import com.example.todolist.token.Token;
import com.example.todolist.token.TokenType;
import com.example.todolist.user.model.Role;
import com.example.todolist.user.model.User;
import com.example.todolist.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    public void setUp(){
        user = User.builder()
                .username("Ivancho")
                .password("wasd")
                .email("asd@abv.bg")
                .role(Role.USER)
                .createdDate(LocalDateTime.now())
                .build();

        userRepository.save(user);

    }
    @AfterEach
    public void tearDown(){
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }






}