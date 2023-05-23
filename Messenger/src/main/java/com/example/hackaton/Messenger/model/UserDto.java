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
public class UserDto {
    private Long id;
    private String surname;
    private String name;
    private String patronim;
    private String inn;
    private String accNumber;
    private String passport;
    private String snils;


    public static UserDto toModel(User user){
        return new UserDto(user.getId(), user.getSurname(), user.getName(),user.getPatronim(),
                user.getPassport(), user.getAccNumber(),user.getInn(),user.getSnils());
    }
}
