package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.db.entity.UserEntity;

public interface UserService {
    UserEntity registUser(UserDto user);

    UserEntity getUserById(String userId) ;
}

