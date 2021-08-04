package com.inline.sub2.api.service;

import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeptServiceImpl implements DeptService{

    @Autowired
    DeptRepository deptRepository;

    @Override
    public DeptEntity getDeptId(String deptName, Long officeId) {
        return deptRepository.findByDeptNameAndOfficeId(deptName,officeId);
    }

    @Override
    public DeptEntity getDeptName(Long deptId) {
        return deptRepository.findByDeptId(deptId);
    }

    @Override
    public List<DeptEntity> getDeptList(Long officeId){
        return deptRepository.findByOfficeId(officeId);
    }
}
