package com.example.hackaton.Messenger.repo;

import com.example.hackaton.Messenger.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
