package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.AdminRegistDto;
import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public UserEntity registAdmin(AdminRegistDto admin) {
        Date now = new Date();
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(admin.getEmail());
        userEntity.setDeptId(admin.getDeptId());
        userEntity.setJobId(admin.getJobId());
        userEntity.setName(admin.getName());
        userEntity.setPhone(admin.getPhone());
        userEntity.setPassword(passwordEncoder.encode(admin.getPassword()));
        userEntity.setAuth("ADMIN");
        userEntity.setJoinDate(now);
        userEntity.setOfficeId(admin.getOfficeId());
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity registUser(UserDto user) {
        Date now = new Date();
        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail(user.getEmail());
//        userEntity.setDeptId(user.getDeptId());
//        userEntity.setJobId(user.getJobId());
//        userEntity.setName(user.getName());
//        userEntity.setPhone(user.getPhone());
//        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
//        userEntity.setJoinDate(now);
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}