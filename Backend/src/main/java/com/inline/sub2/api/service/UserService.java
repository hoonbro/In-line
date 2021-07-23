package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.AdminRegistDto;
import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.db.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    UserEntity registAdmin(AdminRegistDto admin);

    UserEntity registUser(UserDto user);

    UserEntity getUserById(String userId) ;
}

