package com.example.hackaton.Messenger.repo;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    public Optional<Chat> findById(Long chatID);
    public List<Chat> findByProblem(Long problem_id);

}
