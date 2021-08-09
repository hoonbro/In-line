package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.CommuteEntity;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Map<String, List<CommuteEntity>> getCommutesByDate(Long officeId);

}
