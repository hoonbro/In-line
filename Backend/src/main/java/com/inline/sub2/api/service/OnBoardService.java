package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.db.entity.OnBoardEntity;

public interface OnBoardService {
    OnBoardEntity registUserOnboard(UserRegistDto user);
}
