package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.entity.User;
import com.example.hackaton.Messenger.model.ChatDto;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.ManagerRepository;
import com.example.hackaton.Messenger.repo.ProblemRepository;
import com.example.hackaton.Messenger.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private UserRepository userRepository;
    public Optional<Long> getChatID(Long chatID){
        return chatRepository.findById(chatID).map(Chat::getId);
    }
    public Optional<Chat> findById(Long chatID){return chatRepository.findById(chatID);}


    public List<Chat> findAll(){ return  new ArrayList<>(chatRepository.findAll());}

    public Problem getProblemID(Long ID){return problemRepository.findById(ID).get();}

    public Chat save(Long chat_id, Long manager_id,Long problem_id){
        Chat chat = findById(chat_id).orElseThrow();
        chat.setManager(managerRepository.findById(manager_id).orElseThrow());
        chat.setProblem(problemRepository.findById(problem_id).orElseThrow());
        return chatRepository.save(chat);
    }
    public Chat create(Long user_id,Long problem_id){

        Chat chat = new Chat();
        User user = userRepository.findById(user_id).get();
        Problem problem = problemRepository.findById(problem_id).get();
        chat.setProblem(problem);
        chat.setUser(user);
        return chatRepository.save(chat);
    }

    public Chat update(Long chat_id,Long problem_id){
        Chat chat = findById(chat_id).orElseThrow();
        Manager manager = managerRepository.findById(chat.getManager().getId()).get();
        Problem problem = problemRepository.findById(problem_id).get();
        chat.setManager(null);
        chat.setProblem(problem);
        manager.setIsAvailable(false);
        return chatRepository.save(chat);
    }

    public boolean disableChat (Long chat_id){
        Chat chat = chatRepository.findById(chat_id).get();
        chat.setSolved(true);
        return true;
    }



}
