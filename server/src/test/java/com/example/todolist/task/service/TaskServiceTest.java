package com.example.todolist.task.service;

import com.example.todolist.task.model.Task;
import com.example.todolist.task.repository.TaskRepository;
import com.example.todolist.token.Token;
import com.example.todolist.token.TokenType;
import com.example.todolist.user.model.Role;
import com.example.todolist.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @MockBean
    private TaskRepository taskRepository;

   private Task task;

    @Test

    @DisplayName("Get a Data Based on Valida Task Name ")
    public void whenValidTaskByID_thenTaskShouldFound(){
        int id = 4;
        Task task = taskService.getTask(id);

        assertEquals(id, task.getId());
    }

    @Test

    public void whenValidTask_thenGetAllTasks(){
        int id = 4;
        int id2 = 5;

        Task task1 = taskService.getTask(id);
        Task task2 = taskService.getTask(id2);

        List<Task> expected = Arrays.asList(task1,task2);

        List<Task> actual = taskService.getAllTasks();

        assertEquals(expected,actual);

    }







}