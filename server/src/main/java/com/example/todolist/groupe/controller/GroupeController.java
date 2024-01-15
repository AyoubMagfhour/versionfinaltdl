package com.example.todolist.groupe.controller;

import com.example.todolist.groupe.model.Groupe;
import com.example.todolist.groupe.service.GroupeService;
import com.example.todolist.task.model.Task;
import com.example.todolist.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "http://localhost:5173")
public class GroupeController {

    private final GroupeService groupeService;

    @Autowired
    public GroupeController(GroupeService groupeService) {
        this.groupeService = groupeService;
    }

    @GetMapping
    public ResponseEntity<List<Groupe>> getAllGroups() {
        List<Groupe> groups = groupeService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Groupe> addGroup(@RequestParam String groupName,
                                           @RequestParam int ownerId
                                           ) {
        Groupe addedGroup = groupeService.addGroup(groupName, ownerId);
        return ResponseEntity.ok(addedGroup);
    }

    @PostMapping("/invite-user")
    public ResponseEntity<Groupe> inviteUserToGroup(@RequestParam int groupId,
                                                    @RequestParam int userId) {
        Groupe updatedGroup = groupeService.inviteUserToGroup(groupId, userId);
        return ResponseEntity.ok(updatedGroup);
    }
    @GetMapping("/getid")
    public ResponseEntity<Optional<Groupe>> addGroup(@RequestParam int groupId  ) {
        Optional<Groupe> addedGroup = groupeService.getGroupeByid(groupId);
        return ResponseEntity.ok(addedGroup);
    }

    @GetMapping("/get-groups-by-id/{username}")
    public ResponseEntity<List<Groupe>> getTaskByUsername(@PathVariable("username") String username){
        List<Groupe> GroupsById = groupeService.getTaskByUsername(username);
        return new ResponseEntity<>(GroupsById,HttpStatus.OK);
    }

    @GetMapping("/users/{groupId}")
    public ResponseEntity<List<User>> getUsersInGroup(@PathVariable("groupId") int groupId) {
        List<User> usersInGroup = groupeService.getUsersInGroup(groupId);
        return new ResponseEntity<>(usersInGroup, HttpStatus.OK);
    }

    // Other endpoints for updating/deleting groups, adding/removing users, etc.
}