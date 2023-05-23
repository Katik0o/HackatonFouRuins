package com.example.hackaton.Messenger.service;

import com.example.hackaton.Messenger.entity.User;
import com.example.hackaton.Messenger.exception.UserExistsException;
import com.example.hackaton.Messenger.repo.ChatRepository;
import com.example.hackaton.Messenger.repo.UserRepository;
import com.example.hackaton.Messenger.web.form.UserForm;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    ChatRepository chatRepository;

    public List<User> getList(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
    public User findById(Long ID){return userRepository.findById(ID).get();}

    public boolean isUserEmailExist(String email){return userRepository.countByEmail(email) != 0;}

    public User save(@Valid UserForm form) { //changing chat params
        if (isUserEmailExist(form.getEmail())){
            throw new UserExistsException("User with email" + form.getEmail() + "already exists");
        }
        User u = new User();
        BeanUtils.copyProperties(form, u);
        u.setPassword(passwordEncoder.encode(form.getPassword()));
        return userRepository.save(u);
    }

}
