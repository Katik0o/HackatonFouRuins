package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Message;
import com.example.hackaton.Messenger.model.MessageDto;
import com.example.hackaton.Messenger.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private ChatService chatService;

    public Message save(Message message){
        messageRepository.save(message);
        return message;
    }

    public Message save(MessageDto messageDto, Long chat_id){
        String date = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) +":"+ Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));


        Message message= MessageDto.toMessage(messageDto);
        message.setUser(userService.findById(messageDto.getUser_id()));
        Chat chat = chatService.findById(chat_id).orElseThrow();
        message.setChat(chat);
        message.setDate(date);
        Message saved=messageRepository.save(message);
        return messageRepository.save(saved);
    }

    public List<Message> findAllByChatId(Long chatID){
        var chatID1=chatService.getChatID(chatID);
        var messages=chatID1.map(cID -> messageRepository.findAllByChat_id(cID)).orElse(new ArrayList<>());

        if(messages.size()>0){
            messages.forEach(message -> {
                messageRepository.save(message);
            });
        }
        return messages;
    }


}

