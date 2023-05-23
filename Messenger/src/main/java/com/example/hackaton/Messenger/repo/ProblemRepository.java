package com.example.hackaton.Messenger.repo;

import com.example.hackaton.Messenger.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
}
