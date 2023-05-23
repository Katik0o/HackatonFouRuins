package com.example.hackaton.Messenger.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserForm { //for reg/auth

    @NotBlank
//    @Email(message = "Email is not correct")
    private String email;

    @NotBlank
    @Size(min = 3)
    private String password;


    @NotBlank
    private String surname;
    private String name;
    private String patronim;
    private String inn;
    private String accNumber;
    private String passport;
    private String snils;



}