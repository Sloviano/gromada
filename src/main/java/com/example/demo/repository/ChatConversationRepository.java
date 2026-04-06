package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.ChatConversation;

@Repository
public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {

    @Query("SELECT c FROM ChatConversation c WHERE " +
           "(c.participantOne.id = :userId OR c.participantTwo.id = :userId) " +
           "AND c.active = true ORDER BY c.lastMessageAt DESC")
    List<ChatConversation> findByParticipantId(@Param("userId") Long userId);

    @Query("SELECT c FROM ChatConversation c WHERE " +
           "((c.participantOne.id = :userOneId AND c.participantTwo.id = :userTwoId) " +
           "OR (c.participantOne.id = :userTwoId AND c.participantTwo.id = :userOneId)) " +
           "AND c.active = true")
    Optional<ChatConversation> findByParticipants(
        @Param("userOneId") Long userOneId,
        @Param("userTwoId") Long userTwoId
    );
}
