package org.tricode.hackathonserverside.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tricode.hackathonserverside.model.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @EntityGraph(attributePaths = "messages")
    Optional<Conversation> findById(Long id);

    @EntityGraph(attributePaths = "messages")
    Optional<Conversation> findByTitleAndUser_Id(String title, Long userId);

    List<Conversation> findByUserId(Long userId);
}
