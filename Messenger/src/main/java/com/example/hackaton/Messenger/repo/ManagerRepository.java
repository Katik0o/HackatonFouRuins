package com.example.hackaton.Messenger.repo;

import com.example.hackaton.Messenger.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    @Override
    Optional<Manager> findById(Long ID);
}
