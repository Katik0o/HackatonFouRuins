package com.example.hackaton.Messenger.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="user")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String surname;
    private String name;
    private String patronim;
    private String password;
    private String inn;
    private String accNumber;
    private String passport;
    private String snils;
    private boolean enabled;

    @Column(nullable = false, unique = true)
    private String email;



    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Chat> chats = new HashSet<>();



}
