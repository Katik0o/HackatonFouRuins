package com.example.hackaton.Messenger.repo;


import com.example.hackaton.Messenger.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    Optional<User> findById(Long ID);
    List<User> findByInn(String inn);


    Optional<User> findByEmail(String email);
}
