package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.ManagerRepository;
import com.example.hackaton.Messenger.repo.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ManagerRepository managerRepository;
    public Optional<Long> getChatID(Long chatID){
        return chatRepository.findById(chatID).map(Chat::getId);
    }
    public Optional<Chat> findById(Long chatID){return chatRepository.findById(chatID);}


    public Set<Chat> findAll(){ return  new HashSet<>(chatRepository.findAll());}

    public Problem getProblemID(Long ID){return problemRepository.findById(ID).get();}

    public Chat create(Long chat_id,Long problem_id){
        Chat chat = findById(chat_id).orElseThrow();
        Problem problem = problemRepository.findById(problem_id).get();
        chat.setProblem(problem);
        return chatRepository.save(chat);
    }
    public Chat update(Long chat_id,Long manager_id){
        Chat chat = findById(chat_id).orElseThrow();
        Manager manager = managerRepository.findById(manager_id).get();
            chat.setManager(manager);
            manager.setIsAvailable(false);
            return chatRepository.save(chat);
    }


}
