package com.example.hackaton.Messenger.web.controller;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.entity.Message;
import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.model.ChatDto;
import com.example.hackaton.Messenger.model.MessageDto;
import com.example.hackaton.Messenger.model.MessageRequest;
import com.example.hackaton.Messenger.model.ProblemRequest;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.ManagerRepository;
import com.example.hackaton.Messenger.repo.UserRepository;
import com.example.hackaton.Messenger.service.*;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/ms")
public class ChatController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ProblemService problemService;
    @Autowired
    ManagerService managerService;



    @GetMapping("/table")
    public ResponseEntity<?> findProblem(){
        List<ProblemRequest> problemRequests = ProblemRequest.buildList(problemService.getList());
        return ResponseEntity.ok(problemRequests);
    }

    @MessageMapping("/chat/create")
    @JsonProperty
    @SendTo("/send/chats")
    public Chat createChat(@Payload Long user_id, @Payload Long problem_id){

//        if(userRepository.findById(user_id).isEmpty()){
//            System.out.println("нет такого пользователя");
//        }
        return chatService.create(user_id,problem_id);
    }
    @MessageMapping("/chat/update")
    @SendTo("/send/chats")
    public Chat updateChat (@Payload Long chat_id,  @Payload Long problem_id){
        return chatService.update(chat_id,problem_id);
    }

    @MessageMapping("/message")
    @SendTo("/send/message")
    public MessageDto sendingMessage (@Valid @Payload MessageDto messageDto, @Payload Long chat_id) {
            return MessageDto.build(messageService.save(messageDto, chat_id));
    }

    @MessageMapping("/message/find")
    @SendTo("/send/message")
    public List<MessageRequest> finChatMessages (@Payload Long chat_id){
        List<MessageRequest> messageRequests = MessageRequest.buildList(messageService.findAllByChatId(chat_id));
        return messageRequests;
    }
    @MessageMapping("/manager/chats")
    @SendTo("/send/chats")
    public List<Chat> findManagerChats (@Payload Long manager_id){
        return managerService.findManagerChats(manager_id);
    }
    @MessageMapping("/user/chats")
    @SendTo("/send/chats")
    public List<Chat> findUserChats (@Payload Long user_id){
        return userService.findUserChats(user_id);
    }

    @MessageMapping("/chat/stop") //???????????????????????????????????
    @SendTo("/send/chats")
    public void chatStop (@Payload Long chat_id){
        chatService.disableChat(chat_id);
    }






//    @MessageMapping("/changeMessage")
//    @SendTo("/topic/activity")
//    public Message change(Message message){
//        System.out.println(message.toString());
//        return messageService.save(message);
//    }





}
