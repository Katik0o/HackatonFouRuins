package com.example.hackaton.Messenger.model;
import com.example.hackaton.Messenger.entity.Chat;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatDto {
    private Long id;
    private Long user_id;
    private Long manager_id;
    private Long problem_id;
    public static ChatDto build(Chat chat){
        return new ChatDto(chat.getId(), chat.getUser().getId(),chat.getManager().getId(),chat.getProblem().getId());
    }

//    public static Chat toChat(ChatDTO chatDTO){
//        Chat chat = new Chat();
//        chat.set
//    }

    public static Set<ChatDto> buildList(Set<Chat> chats){

        return new HashSet<>(chats.stream().map(ChatDto::build).toList());
    }
}
