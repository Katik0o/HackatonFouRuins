package com.example.hackaton.Messenger.model;

import com.example.hackaton.Messenger.entity.Message;
import com.example.hackaton.Messenger.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemRequest {
   private Long id;
    private String name;


    public static ProblemRequest build(Problem problem){
        return new ProblemRequest(problem.getId(), problem.getName());
    }
    public static List<ProblemRequest> buildList(List<Problem> problems){
        return problems.stream().map(ProblemRequest::build).toList();
    }
}
