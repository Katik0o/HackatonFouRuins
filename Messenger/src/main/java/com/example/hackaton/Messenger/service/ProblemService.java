package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.entity.User;
import com.example.hackaton.Messenger.repo.ProblemRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Service
public class ProblemService {
    @Autowired
    ProblemRepository problemRepository;

    public List<Problem> getList(){
        List<Problem> problems = new ArrayList<>();
        problemRepository.findAll().forEach(problems::add);
        return problems;
    }
}
