package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.db.entity.OnBoardEntity;
import org.springframework.transaction.annotation.Transactional;

public interface OnBoardService {
    @Transactional(rollbackFor = Exception.class)
    OnBoardEntity registUserOnboard(UserRegistDto user);
    void deleteUserOnboard(String email);
}
