package com.example.hackaton.Messenger.spring;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import com.example.hackaton.Messenger.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails { //      JWT user
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String surname;
    private String patronim;
    private String inn;
    private String accNumber;
    private String passport;
    private String snils;
    private String email;
    @JsonIgnore
    private String password;
    //private boolean enable;
    private Collection<? extends GrantedAuthority> authorities;



    public UserDetailsImpl(Long id,
                           String name,
                           String surname,
                           String patronim,
                           String inn,
                           String accNumber,
                           String passport,
                           String snils,
                           String email,
                           String password
                           )   {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronim = patronim;
        this.inn = inn;
        this.accNumber = accNumber;
        this.passport = passport;
        this.snils = snils;
        this.email = email;
        this.password = password;
       // this.enable = enable;

    }



    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getPatronim(),
                user.getInn(),
                user.getAccNumber(),
                user.getPassport(),
                user.getSnils(),
                user.getEmail(),
                user.getPassword())
                ;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    public String getPatronim() {
        return patronim;
    }


    public String getInn() {
        return inn;
    }


    public String getAccNumber() {
        return accNumber;
    }


    public String getPassport() {
        return passport;
    }

    public String getSnils() {
        return snils;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}