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

    @Query(value = "Select u from User u where 1=1 and (concat_ws(' ', lower(u.firstName),lower(u.lastName)) like %:inputString%) or" +
            "(concat_ws(' ', upper(u.firstName), upper(u.lastName)) like %:inputString%) or" +
            "(concat_ws(' ', u.firstName, u.lastName) like %:inputString%) or" +
            "(concat(lower(u.firstName), lower(u.lastName)) like %:inputString%) or" +
            "(concat(upper(u.firstName), upper(u.lastName)) like %:inputString%) or" +
            "(concat(u.firstName, u.lastName) like %:inputString%)"
    )
    List<User> findByInputString(@RequestParam String inputString);

    Optional<User> findById(int id);
    User findUserById(int id);

}
