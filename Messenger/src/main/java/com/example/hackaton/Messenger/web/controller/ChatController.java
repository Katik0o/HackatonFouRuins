package com.example.hackaton.Messenger.web.controller;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.model.ChatDto;
import com.example.hackaton.Messenger.model.MessageDto;
import com.example.hackaton.Messenger.model.MessageRequest;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.UserRepository;
import com.example.hackaton.Messenger.service.ChatService;
import com.example.hackaton.Messenger.service.ManagerService;
import com.example.hackaton.Messenger.service.MessageService;
import com.example.hackaton.Messenger.service.UserService;
//import jakarta.validation.Valid;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
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
    private ManagerService managerService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;


    @PostMapping("/chat/{chat_id}") //sending message
    public ResponseEntity<?> sendingMessage(@Valid @RequestBody MessageDto messageDto, @PathVariable Long chat_id ){

        if( chatService.findById(chat_id).isPresent()){
            MessageDto saved = MessageDto.build(messageService.save(messageDto, chat_id));
                    //return ResponseEntity.ok().messageDto.build();
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

    @PostMapping("{user_id}/{problem_id}/chat")
    public ResponseEntity<?> createChat (@PathVariable  Long user_id, @PathVariable Long problem_id){
        if(userRepository.findById(user_id).isEmpty()){
            return ResponseEntity.ok("нет такого пользователя");
        }
        Chat chat= new Chat();
        return ResponseEntity.ok(ChatDto.build(chatService.create(chat.getId(),problem_id)));

    }
    @PutMapping("/chat/{chat_id}/{problem_id}") //назначение менеджера
    public ResponseEntity<?> updateChat (@PathVariable Long chat_id, @PathVariable Long problem_id){
        return ResponseEntity.ok(ChatDto.build(chatService.create(chat_id,problem_id)));
    }

    @PostMapping("/dltmng/{chat_id}/{manager_id}")
    public ResponseEntity<?> deleteManagerFromChat (@PathVariable Long chat_id, @PathVariable Long manager_id){
        return ResponseEntity.ok(managerService.deleteFromChat(chat_id,manager_id));
    }




}
