package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.repo.ManagerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;

    public Manager findById(Long ID){return managerRepository.findById(ID).get();}
}
