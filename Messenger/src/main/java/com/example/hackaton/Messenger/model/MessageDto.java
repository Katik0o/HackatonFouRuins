package com.example.hackaton.Messenger.model;

import com.example.hackaton.Messenger.entity.Message;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDto {
    private Long id;
    private String text;
    private Long chat_id;
    private Long user_id;
    private Long manager_id;
    private String  date;

    public static MessageDto build(Message message){
        return new MessageDto(message.getId(), message.getContent(),message.getChat().getId(),message.getUser().getId(),
                message.getManager().getId(), message.getDate());
    }

    public static Message toMessage(MessageDto messageDTO){
        Message message = new Message();
        message.setContent(messageDTO.getText());
        return message;
    }

    public static List<MessageDto> buildList(List<Message> messages){
        return messages.stream().map(MessageDto::build).toList();
    }

}
