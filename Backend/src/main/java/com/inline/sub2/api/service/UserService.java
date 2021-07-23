package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.AdminRegistDto;
import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.db.entity.UserEntity;

public interface UserService {
    UserEntity registAdmin(AdminRegistDto admin);

    UserEntity registUser(UserDto user);

    UserEntity getUserByEmail(String email) ;
}

