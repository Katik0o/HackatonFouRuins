package com.example.hackaton.Messenger.repo;

import com.example.hackaton.Messenger.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
}
