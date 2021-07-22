package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    DeptRepository deptRepository;

    @Override
    public DeptEntity getDeptId(String detpName) {
        return deptRepository.findByDeptName(detpName);
    }
}
