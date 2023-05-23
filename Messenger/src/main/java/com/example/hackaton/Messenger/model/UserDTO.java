package com.example.hackaton.Messenger.model;

import com.example.hackaton.Messenger.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String nickname;


    public static UserDTO toModel(User user){
        return new UserDTO(user.getId(), user.getNickname());
    }
}
