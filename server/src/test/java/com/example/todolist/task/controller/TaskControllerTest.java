package com.example.todolist.task.controller;

import com.example.todolist.config.JwtService;
import com.example.todolist.task.model.Task;
import com.example.todolist.task.service.TaskService;
import com.example.todolist.token.Token;
import com.example.todolist.token.TokenType;
import com.example.todolist.user.model.Role;
import com.example.todolist.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;
    @MockBean
    private JwtService jwtService;

    private Task task;
    private Task task1;






    @Test
    void getTask() throws Exception {
        Mockito.when(taskService.getTask(4)).thenReturn(task);

        mockMvc.perform(get("/TO-DO-LIST-DEMO/task/v1.0.0/get-task/4")
                        .with(SecurityMockMvcRequestPostProcessors.user(task.getUser().getUsername()).roles(Role.USER.toString(),Role.ADMIN.toString()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.topic")
                .value(task.getTopic()));

    }

    @Test
    void getAllTasks() throws Exception {
        Mockito.when(taskService.getAllTasks()).thenReturn(Arrays.asList(task,task1));
        mockMvc.perform(get("/TO-DO-LIST-DEMO/task/v1.0.0/get-all-tasks")
                        .with(SecurityMockMvcRequestPostProcessors.user(task.getUser().getUsername()).roles(Role.USER.toString(),Role.ADMIN.toString()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));



    }

    @Test
    void deleteTask() throws Exception {
        mockMvc.perform(delete("/TO-DO-LIST-DEMO/task/v1.0.0/delete-task/4")
                        .with(SecurityMockMvcRequestPostProcessors.user(task.getUser().getUsername()).roles(Role.USER.toString(),Role.ADMIN.toString()))
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }


}