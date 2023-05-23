package com.example.hackaton.Messenger.model;

import com.example.hackaton.Messenger.entity.Message;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {
    private Long id;
    private String text;
    private Long chat_id;
    private Long user_id;
    private Long manager_id;
    private String  date;

    public static MessageDTO build(Message message){
        return new MessageDTO(message.getId(), message.getContent(),message.getChat().getId(),message.getUser().getId(),
                message.getManager().getId(), message.getDate());
    }

    public static Message toMessage(MessageDTO messageDTO){
        Message message = new Message();
        message.setContent(messageDTO.getText());
        return message;
    }

    public static List<MessageDTO> buildList(List<Message> messages){
        return messages.stream().map(MessageDTO::build).toList();
    }

}
