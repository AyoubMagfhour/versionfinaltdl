package com.example.todolist.groupe.service;

import com.example.todolist.groupe.model.Groupe;
import com.example.todolist.groupe.repository.GroupeRepository;
import com.example.todolist.task.model.Task;
import com.example.todolist.user.model.User;
import com.example.todolist.user.repository.UserRepository;
import com.example.todolist.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupeService {

    private final GroupeRepository groupeRepository;

    private final UserRepository userRepository;
    private final UserService userService;



    @Autowired
    public GroupeService(GroupeRepository groupeRepository, UserRepository userRepository , UserService userService) {
        this.groupeRepository = groupeRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<Groupe> getAllGroups() {
        return groupeRepository.findAll();
    }

    public Groupe addGroup(String groupName, int ownerId) {
        // Retrieve the owner from the database
        User owner = userService.getUserById(ownerId);

        // Create a new group
        Groupe group = Groupe.builder()
                .name(groupName)
                .owner(owner)
                .build();


        // Save the group to the database
        return groupeRepository.save(group);
    }

    public Groupe inviteUserToGroup(int groupId, int userId) {
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User userToInvite = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Add the user to the group
        group.getMembers().add(userToInvite);
        userToInvite.getGroups().add(group); // Add the group to the user

        // Save the updated group to the database
        return groupeRepository.save(group);
    }

    public List<Groupe> getTaskByUsername(String username) {

        List<Groupe> groupsByUsername = groupeRepository.findAllGroupsByUsername(username);
        return groupsByUsername;
    }
    public Optional<Groupe> getGroupeByid(int groupeId) {
    	Optional<Groupe> p1 = groupeRepository.findById(groupeId);
    	return p1 ;
    	
    }

    public List<User> getUsersInGroup(int groupId) {
        // Fetch the group
        Groupe group = groupeRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));

        // Return the list of users in the group
        return group.getMembers();
    }

    // Other methods for updating/deleting groups, adding/removing users, etc.
}