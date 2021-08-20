package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.DeptUserDto;
import com.inline.sub2.db.entity.CommuteEntity;
import com.inline.sub2.db.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Map<String, List<CommuteEntity>> getCommutes(Long officeId);

    UserEntity retireUser(Long userId);

    Double getRetireRate(Long officeId);

    Long getJobYear(Long officeId);
}
