package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.ManagerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class ManagerService {
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    ChatRepository chatRepository;

    public Manager findById(Long ID){return managerRepository.findById(ID).get();}
    //public Manager findByProblemId(Long id){return}
    public String deleteFromChat(Long chat_id,Long  manager_id){
        if (managerRepository.findById(manager_id).isEmpty()){
            return "нет такого мэнеджера";
        }
        Chat chat = chatRepository.findById(chat_id).get();
        Manager manager = managerRepository.findById(manager_id).get();
        if (Objects.equals(chat.getManager().getId(), manager_id)){
            chat.setManager(null);
            chatRepository.save(chat);
            manager.setIsAvailable(true);
            return "мэнеджер удален";
        }
        return "мэнеджер не состоит в этом чате";
    }

}
