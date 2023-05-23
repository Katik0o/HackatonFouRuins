package com.example.hackaton.Messenger.model;

import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.entity.Problem;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagerDto {
    private Long id;
    private String nickname;
    private Boolean isAvailable;
    private Problem problem;

    public ManagerDto toModel(Manager manager){
        return new ManagerDto(manager.getId(), manager.getNickname(), manager.getIsAvailable(),manager.getProblem());
    }
}
