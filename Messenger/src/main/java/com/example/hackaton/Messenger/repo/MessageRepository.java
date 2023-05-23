package com.example.hackaton.Messenger.repo;

import com.example.hackaton.Messenger.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
}
