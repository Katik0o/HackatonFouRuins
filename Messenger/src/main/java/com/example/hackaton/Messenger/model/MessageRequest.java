package com.example.hackaton.Messenger.model;

import com.example.hackaton.Messenger.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {


    private Long id;
    private String text;
    private Long chat_id;
    private Long user_id;
    private Long manager_id;
    private String date;


    public static MessageRequest build(Message message) {
        return new MessageRequest(message.getId(), message.getContent(), message.getChat().getId(),
                message.getUser().getId(), message.getManager().getId(), message.getDate());
    }


    public static List<MessageRequest> buildList(List<Message> messages) {
        return messages.stream().map(MessageRequest::build).toList();
    }
}