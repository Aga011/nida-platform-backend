package com.az.nida.platform.messaging.repository;

import com.az.nida.platform.messaging.entity.Message;
import com.az.nida.platform.messaging.entity.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByToIdOrderByCreatedAtDesc(Long toId);

    List<Message> findByFromIdOrderByCreatedAtDesc(Long fromId);

    List<Message> findByGroupIdOrderByCreatedAtDesc(Long groupId);

    List<Message> findByToIdAndTypeOrderByCreatedAtDesc(Long toId, MessageType type);

    List<Message> findByFromIdAndToIdOrderByCreatedAtDesc(Long fromId, Long toId);

    long countByToIdAndReadAtIsNull(Long toId);
}