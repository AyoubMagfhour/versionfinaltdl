package com.example.todolist.groupe.repository;

import com.example.todolist.groupe.model.Groupe;
import com.example.todolist.task.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Integer> {
    // Additional custom queries or operations specific to Groupe entity if needed

    @Query("SELECT t FROM Groupe t JOIN t.owner u WHERE u.username = :username")
    List<Groupe> findAllGroupsByUsername(@Param("username") String username);
}
