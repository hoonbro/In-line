package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserEntity registUser(UserDto user) {

        return userRepository.save();
    }

    @Override
    public UserEntity getUserById(String userId) {
        return userRepository.getById(userId);
    }
}