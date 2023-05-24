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
@Table(name ="problem")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "problem_manager",
//    joinColumns = {
//            @JoinColumn(name = "problem_id", referencedColumnName = "id",
//                    nullable = false, updatable = false)},
//            inverseJoinColumns = {
//            @JoinColumn(name = "chat_id", referencedColumnName = "id",
//                    nullable = false, updatable = false)}
//    )
//    private Set<Manager> managers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "prblm_answ",
            joinColumns = {
                    @JoinColumn(name = "problem_id", referencedColumnName = "id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "answe_id", referencedColumnName = "id",
                            nullable = false, updatable = false)}
    )
    private Set<Answer> answers = new HashSet<>();

    @OneToOne(mappedBy = "problem")
    private Chat chat;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    private Set<Manager> managers = new HashSet<>();
}
