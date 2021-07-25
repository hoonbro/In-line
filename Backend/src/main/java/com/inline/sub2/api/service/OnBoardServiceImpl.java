package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.JobEntity;
import com.inline.sub2.db.entity.OnBoardEntity;
import com.inline.sub2.db.repository.OnBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OnBoardServiceImpl implements OnBoardService{

    @Autowired
    OnBoardRepository onBoardRepository;

    @Autowired
    DeptService deptService;

    @Autowired
    JobService jobService;

    @Override
    @Transactional
    public OnBoardEntity registUserOnboard(UserRegistDto user) {

        DeptEntity deptEntity = deptService.getDeptId(user.getDeptName()); //부서 번호 조회
        JobEntity jobEntity = jobService.getJobId(user.getJobName()); //직책 번호 조회

        user.setDeptId(deptEntity.getDeptId());
        user.setJobId(jobEntity.getJobId());

        //onboard에 올릴 정보 기입
        OnBoardEntity onBoardEntity = new OnBoardEntity();
        onBoardEntity.setEmail(user.getEmail());
        onBoardEntity.setJobId(user.getJobId());
        onBoardEntity.setName(user.getName());
        onBoardEntity.setOfficeId(user.getOfficeId());
        onBoardEntity.setDeptId(user.getDeptId());
        return onBoardRepository.save(onBoardEntity);
    }
}
