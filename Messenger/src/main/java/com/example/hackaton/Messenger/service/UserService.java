package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.Chat;
import com.example.hackaton.Messenger.entity.Manager;
import com.example.hackaton.Messenger.entity.Problem;
import com.example.hackaton.Messenger.entity.User;
import com.example.hackaton.Messenger.exception.UserExistsException;
import com.example.hackaton.Messenger.model.LoginRequest;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.UserRepository;
import com.example.hackaton.Messenger.web.form.UserForm;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatRepository chatRepository;
    public User login(LoginRequest loginRequest) throws Exception {
        User user;
        List<User> users = userRepository.findByInn(loginRequest.getUsername());
        System.out.println(users.size());
        if (users.size() > 0){
            user = users.get(0);
            System.out.println(user.getName()+" "+user.getPassword());
            if(user.getPassword().equals(loginRequest.getPassword())){
                user.setEnabled(true);
                return user;
            } else {
                throw new Exception("wrong password");
            }
        } else {
            throw new UserExistsException("User not exists");
        }
    }

    public List<User> getList(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
    public User findById(Long ID){return userRepository.findById(ID).get();}
    public List<Chat> findUserChats(Long user_id){
        return chatRepository.findByUser(user_id);
    }
//
//    public boolean isUserExist(String login){return userRepository.countByInn(login) != 0;}
//
//    public User save(@Valid UserForm form) { //changing chat params
//        if (isUserEmailExist(form.getEmail())){
//            throw new UserExistsException("User with email" + form.getEmail() + "already exists");
//        }
//        User u = new User();
//        BeanUtils.copyProperties(form, u);
//        u.setPassword(passwordEncoder.encode(form.getPassword()));
//        return userRepository.save(u);
//    }

}
