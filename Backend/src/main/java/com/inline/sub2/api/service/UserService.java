package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.dto.UserUpdateDto;
import com.inline.sub2.db.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    UserEntity registAdmin(UserRegistDto admin);

    UserEntity registUser(UserRegistDto user);

    UserEntity getUserByEmail(String email);

    UserEntity getUserInfo(Long userId);

    List<UserEntity> getUserList(Long officeId);

    UserEntity updateUser(UserUpdateDto userUpdateDto);

    void updatePassword(UserEntity userEntity, String password);


}
