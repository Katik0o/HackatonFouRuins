package com.example.hackaton.Messenger.repo;

import com.example.hackaton.Messenger.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findAllByChat_id(Long ChatID);
}
