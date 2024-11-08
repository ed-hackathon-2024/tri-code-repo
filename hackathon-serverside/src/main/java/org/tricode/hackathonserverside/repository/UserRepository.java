package org.tricode.hackathonserverside.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tricode.hackathonserverside.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(
            attributePaths = {"roles", "conversations", "conversations.messages", "userFeatures"}
    )
    Optional<User> findById(Long id);

    //FOR USER CONTROLLER - RETURNING PROFILE
    @EntityGraph(
            attributePaths = {"roles", "userFeatures"}
    )
    Optional<User> findUserById(Long id);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
