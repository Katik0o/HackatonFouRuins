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
    public Chat createChat(@Payload Long user_id, @Payload Long problem_id){
        if(userRepository.findById(user_id).isEmpty()){
            System.out.println("нет такого пользователя");
        }
        Chat chat= new Chat();
        return chatService.create(chat.getId(),problem_id);
    }
    @MessageMapping("/chat/update")
    public Chat updateChat (@Payload Long chat_id, @Payload Long manager_id, @Payload Long problem_id){
        return chatService.update(chat_id,manager_id,problem_id);
    }

    @MessageMapping("/message")
    public MessageDto sendingMessage (@Valid @Payload MessageDto messageDto, @Payload Long chat_id) {
            return MessageDto.build(messageService.save(messageDto, chat_id));
    }

    @MessageMapping("/message/find")
    public List<MessageRequest> finChatMessages (@Payload Long chat_id){
        List<MessageRequest> messageRequests = MessageRequest.buildList(messageService.findAllByChatId(chat_id));
        return messageRequests;
    }
    @MessageMapping("/manager/chats")
    public List<Chat> findManagerChats (@Payload Long manager_id){
        return managerService.findManagerChats(manager_id);
    }

    @MessageMapping("/chat/stop") //???????????????????????????????????
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
