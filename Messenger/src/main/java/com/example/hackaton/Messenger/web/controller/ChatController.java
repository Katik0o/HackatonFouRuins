package com.example.hackaton.Messenger.web.controller;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Message;
import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.model.ChatDto;
import com.example.hackaton.Messenger.model.MessageDto;
import com.example.hackaton.Messenger.model.MessageRequest;
import com.example.hackaton.Messenger.model.ProblemRequest;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.UserRepository;
import com.example.hackaton.Messenger.service.ChatService;
import com.example.hackaton.Messenger.service.MessageService;
import com.example.hackaton.Messenger.service.ProblemService;
import com.example.hackaton.Messenger.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ProblemService problemService;


    @GetMapping("/table")
    public ResponseEntity<?> findProblem(){
        List<ProblemRequest> problemRequests = ProblemRequest.buildList(problemService.getList());
        return ResponseEntity.ok(problemRequests);
    }
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        System.out.println(message.toString());
        return message;
    }
    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getChat().getId().toString(),"/private",message);
        System.out.println(message.toString());
        return message;
    }



    @PostMapping("/chat/{chat_id}") //sending message
    public ResponseEntity<?> sendingMessage(@Valid @RequestBody MessageDto messageDto, @PathVariable Long chat_id ){

        if( chatService.findById(chat_id).isPresent()){
            MessageDto saved = MessageDto.build(messageService.save(messageDto, chat_id));

            //        return ResponseEntity.ok().build(messageDto);
            return ResponseEntity.ok(saved);
        }else{
            System.err.println("Chat not found with this id");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/chat/{chat_id}")
    public ResponseEntity<?> findChatMessages (@PathVariable Long chat_id){
        List<MessageRequest> messageRequests = MessageRequest.buildList(messageService.findAllByChatId(chat_id));
//        for(MessageRequest ms: messageRequests){
//            ms.setUsername(userService.findById(ms.getUser_id()).getName()); //для подписи имени над сообщением?
//        }
        return ResponseEntity.ok(messageRequests);
    }
    @PutMapping("/chat/{chat_id}/{manager_id}") //назначение менеджера
    public ResponseEntity<?> updateChat (@PathVariable Long chat_id, @PathVariable Long manager_id){
        return ResponseEntity.ok(ChatDto.build(chatService.save(chat_id,manager_id)));
    }
//    @MessageMapping("/changeMessage")
//    @SendTo("/topic/activity")
//    public Message change(Message message){
//        System.out.println(message.toString());
//        return messageService.save(message);
//    }





}
