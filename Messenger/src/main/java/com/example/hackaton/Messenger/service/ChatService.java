package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.repo.ChatRepository;
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
    public Optional<Long> getChatID(Long chatID){
        return chatRepository.findById(chatID).map(Chat::getId);
    }
    public Optional<Chat> findById(Long chatID){return chatRepository.findById(chatID);}


    public Set<Chat> findAll(){ return  new HashSet<>(chatRepository.findAll());}

    public Problem getProblemID(Long ID){return problemRepository.findById(ID).get();}


}