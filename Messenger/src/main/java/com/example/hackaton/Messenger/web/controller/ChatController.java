package com.example.hackaton.Messenger.web.controller;

import com.example.hackaton.Messenger.model.MessageDTO;
import com.example.hackaton.Messenger.model.MessageRequest;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.UserRepository;
import com.example.hackaton.Messenger.service.ChatService;
import com.example.hackaton.Messenger.service.MessageService;
import com.example.hackaton.Messenger.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/ms")
public class ChatController {

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

    @PostMapping("/chat/{chat_id}") //sending message
    public ResponseEntity<?> sendingMessage(@Valid @RequestBody MessageDTO messageDto, @PathVariable Long chat_id ){

        if( chatService.findById(chat_id).isPresent()){
            MessageDTO saved = MessageDTO.build(messageService.save(messageDto, chat_id));

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
        return ResponseEntity.ok(messageRequests);
    }

}
