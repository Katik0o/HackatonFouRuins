package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.repo.MessageRepository;
import com.example.hackaton.Messenger.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private ChatService chatService;

    public Message save(Message message){
        message.setStatus(MessageStatus.RECEIVED);
        messageRepository.save(message);
        return message;
    }

    public Message save(MessageDto messageDto, Long chat_id){
        String date = Integer.toString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) +":"+ Integer.toString(Calendar.getInstance().get(Calendar.MINUTE));


        Message message=MessageDto.toMessage(messageDto);
        message.setUser(userService.findById(messageDto.getUser_id()));
        Chat chat = chatService.findById(chat_id).orElseThrow();
        message.setChat(chat);
        message.setDate(date);
        Message saved=messageRepository.save(message);
        if(!Objects.equals(messageDto.getPath(), "")){
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setPath(messageDto.getPath());
            attachedFile.setChat(chat);
            attachedFile.setMessage(saved);
            saved.setAttachedFile(attachedFileRepository.save(attachedFile));
        }





        return messageRepository.save(saved);


    }

    public List<Message> findAllByChatId(Long chatID){
        var chatID1=chatService.getChatID(chatID);
        var messages=chatID1.map(cID -> messageRepository.findAllByChat_id(cID)).orElse(new ArrayList<>());

        if(messages.size()>0){
            messages.forEach(message -> {
                message.setStatus(MessageStatus.DELIVERED);
                messageRepository.save(message);
            });
        }
        return messages;
    }


}