package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.DeptEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeptService {
    @Transactional(rollbackFor = Exception.class)
    DeptEntity getDeptId(String deptName,Long officeId);

    DeptEntity getDeptName(Long deptId);

    public List<DeptEntity> getDeptList(Long officeId);
}
