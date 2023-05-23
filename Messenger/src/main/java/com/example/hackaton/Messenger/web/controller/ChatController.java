package com.example.hackaton.Messenger.web.controller;


import com.example.hackaton.Messenger.repo.UserRepository;
import com.example.hackaton.Messenger.service.MessageService;
import com.example.hackaton.Messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/ms")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AttachedFileService attachedFileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

    @PostMapping("/chat/{chat_id}") //sending message
    public ResponseEntity<?> sendingMessage(@Valid @RequestBody MessageDto messageDto, @PathVariable Long chat_id ){

        if( chatService.findById(chat_id).isPresent()){
           // MessageDto saved = MessageDto.build(messageService.save(messageDto, chat_id));

            //        return ResponseEntity.ok().build(messageDto);
            return ResponseEntity.ok(saved);
        }else{
            System.err.println("Chat not found with this id");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{nickname}")
    public ResponseEntity<?> findUserByNickName (@PathVariable String nickname){
        return ResponseEntity.ok(userService.findByNickname(nickname));
    }


    @PostMapping("/profile/update")
    public ResponseEntity<?> profileUpdate(@Valid @RequestBody UserForm userForm){
        return ResponseEntity.ok(UserModel.toModel(userService.update(userForm)));
    }


    @GetMapping("/chat/{chat_id}")
    public ResponseEntity<?> findChatMessages (@PathVariable Long chat_id){

        List<MessageRequest> messageRequests = MessageRequest.buildList(messageService.findAllByChatId(chat_id));
        for(MessageRequest ms: messageRequests){
            ms.setUsername(userService.findById(ms.getUser_id()).getNickname());
        }
        return ResponseEntity.ok(messageRequests);
    }
    @GetMapping("/chat/{chat_id}/info")
    public ResponseEntity<?> findChatInfo(@PathVariable Long chat_id){
        return ResponseEntity.ok(chatService.findById(chat_id).get());
    }

    @GetMapping("/chatusers/{chat_id}")
    public ResponseEntity<?> findChatUsers(@PathVariable Long chat_id){
        Chat chat = chatService.findById(chat_id).get();
        List<String > names=new ArrayList<>();
        for(User user: chat.getUsers()){
            names.add(user.getNickname());
        }
        return ResponseEntity.ok(names);
    }

    @PostMapping("/userchat/{chat_id}")
    public ResponseEntity<?> deleteUserFromChat(@PathVariable Long chat_id, @Valid @RequestBody UserModel userModel){

        return ResponseEntity.ok(userService.deleteFromChat(chat_id, userModel.getNickname()));
    }

    @GetMapping("/deletechat/{chat_id}")
    public ResponseEntity<?> deleteChat(@PathVariable Long chat_id){
        chatService.deleteById(chat_id);
        return ResponseEntity.ok("Чат удален");
    }

    @PutMapping("/chat/{chat_id}/info")
    public ResponseEntity<?> updateChat (@RequestBody ChatForm chatForm){
        return ResponseEntity.ok(ChatDto.build(chatService.update(chatForm)));
    }



    @GetMapping("/{user_id}/chats")
    public ResponseEntity<?> findChats (@PathVariable Long user_id){
        Set<Chat> chats = chatService.findByUserId(user_id);
        return ResponseEntity.ok(ChatDto.buildList(chats));
    }

    @PostMapping("/addfriend/{user_id}")
    public ResponseEntity<?> addFriend(@PathVariable Long user_id, @Valid @RequestBody UserModel userModel){


        return ResponseEntity.ok(userService.addFriend(user_id, userModel.getNickname()));


    }

    @GetMapping("/friends/{user_id}")
    public ResponseEntity<?> findFriends(@PathVariable Long user_id){
        List<UserModel> userModels =userService.findFriends(user_id).stream().map(UserModel::toModel).toList();
        return ResponseEntity.ok(userModels);


    }

    @PostMapping("/{user_id}/chat")
    public ResponseEntity<?> createChat (@Valid @RequestBody ChatForm chatForm, @PathVariable Long user_id ){
        if(userService.findByNickname(chatForm.getFriendname()).isEmpty()){
            return ResponseEntity.ok("Нет такого пользователя");
        }

        User friend = userService.findByNickname(chatForm.getFriendname()).get();
        User user = userService.findById(user_id);
        chatForm.setIsAdmin(user_id);
        Chat ch=chatService.save(chatForm);
        user.getChats().add(ch);
        userRepository.save(user);
        friend.getChats().add(ch);
        userRepository.save(friend);


        return ResponseEntity.ok("Чат создан");
    }


    @PostMapping("/join/{user_id}")
    public ResponseEntity<?> JoinChat(@Valid @RequestBody ChatForm chatForm, @PathVariable Long user_id){
        String name=chatForm.getName();

        if(chatService.isUserInChat(name,user_id)){
            return ResponseEntity.ok("Вы уже состоите в этом чате");
        }

        if(chatService.findByName(name).isEmpty()){
            return ResponseEntity.ok("Нет такого чата");
        }
        if(Objects.equals(chatService.findByName(name).get().getType(), "Closed")){
            return ResponseEntity.ok("Чат закрытый");
        }
        Chat chat = chatService.findByName(name).get();
        userService.addUserInChat(chat, user_id);
        return ResponseEntity.ok("Вы вошли");
    }

    @GetMapping(value = "/{user_id}/photo/{path}", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> getImage (@PathVariable("path") String path) throws IOException {
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
                .body(attachedFileService.getImage(path));
    }

    @PostMapping("/{user_id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestBody MultipartFile file)
    {
        AttachedFile attachedFile = attachedFileService.saveImage(file);
        attachedFileService.saveImage(file);
        User user = userService.findById(id);
        user.setPhoto(attachedFile.getPath());
        return ResponseEntity.ok(attachedFile);
    }





}