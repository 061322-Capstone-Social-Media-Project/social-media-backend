package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.revature.models.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailAndPassword(String email, String password);

    @Query(value = "SELECT u FROM User u where (lower(u.lastName) like %:inputString% ) or" +
            "(lower(u.firstName) like %:inputString%) or" +
            "(upper(u.firstName) like %:inputString%) or" +
            "(upper(u.lastName) like %:inputString%) or" +
            "((u.firstName) like %:inputString%) or" +
            "((u.firstName) like %:inputString%)")
    List<User> findByInputString(@RequestParam String inputString);

    Optional<User> findById(int id);

}
