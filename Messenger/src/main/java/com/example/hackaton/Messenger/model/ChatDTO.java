package com.example.hackaton.Messenger.model;
import com.example.hackaton.Messenger.entity.Chat;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatDTO {
    private Long id;
    private Long user_id;
    private Long manager_id;
    public static ChatDTO build(Chat chat){
        return new ChatDTO(chat.getId(), chat.getUser().getId(),chat.getProblem().getId());
    }

    public static Set<ChatDTO> buildList(Set<Chat> chats){

        return new HashSet<>(chats.stream().map(ChatDTO::build).toList());
    }
}
