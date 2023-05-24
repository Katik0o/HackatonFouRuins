package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.entity.User;
import com.example.hackaton.Messenger.exception.UserExistsException;
import com.example.hackaton.Messenger.model.LoginRequest;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.ManagerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ChatRepository chatRepository;

    public Manager login(LoginRequest loginRequest) throws Exception {
       Manager manager;
        List<Manager> managers = managerRepository.findByNickname(loginRequest.getUsername());
        System.out.println(managers.size());
        if (managers.size() > 0){
            manager = managers.get(0);
            System.out.println(manager.getNickname()+" "+manager.getPassword());
            if(manager.getPassword().equals(loginRequest.getPassword())){
                return manager;
            } else {
                throw new Exception("wrong password");
            }
        } else {
            throw new UserExistsException("Manager not exists");
        }
    }

    public List<Chat> findManagerChats(Long manager_id){
        Manager manager = managerRepository.findById(manager_id).get();
        Problem problem = manager.getProblem();
        if (problem.getId() == 0){
            return chatRepository.findAll();
        }
        return chatRepository.findByProblem(problem.getId());
    }

}
