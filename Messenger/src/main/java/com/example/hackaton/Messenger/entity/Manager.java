package com.example.hackaton.Messenger.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import lombok.*;

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
@Table(name ="manager")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nickname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean isAvailable;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private Set<Chat> chats = new HashSet<>();


    @OneToMany(mappedBy = "manager",cascade = CascadeType.REMOVE)
    private List<Message> messages;

    @ManyToOne(cascade = CascadeType.ALL)
    private Problem problem;



}
