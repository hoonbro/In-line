package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.EmailDto;
import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.JobEntity;
import com.inline.sub2.db.entity.OnBoardEntity;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.db.repository.JobRepository;
import com.inline.sub2.db.repository.OnBoardRepository;
import com.inline.sub2.db.repository.UserRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class OnBoardServiceImpl implements OnBoardService{

    @Autowired
    OnBoardRepository onBoardRepository;

    @Autowired
    DeptService deptService;

    @Autowired
    JobService jobService;

    @Autowired
    UserService userService;

    @Autowired
    OnBoardService onBoardService;

    @Autowired
    EmailService emailService;


    @Override
    @Transactional
    public OnBoardEntity registUserOnboard(UserRegistDto user) {

        DeptEntity deptEntity = deptService.getDeptId(user.getDeptName(), 1l); //부서 번호 조회
        JobEntity jobEntity = jobService.getJobId(user.getJobName(), 1l); //직책 번호 조회

        user.setDeptId(deptEntity.getDeptId());
        user.setJobId(jobEntity.getJobId());

        //onboard에 올릴 정보 기입
        OnBoardEntity onBoardEntity = new OnBoardEntity();
        onBoardEntity.setEmail(user.getEmail());
        onBoardEntity.setJobId(user.getJobId());
        onBoardEntity.setName(user.getName());
        onBoardEntity.setOfficeId(user.getOfficeId());
        onBoardEntity.setDeptId(user.getDeptId());

        //구성원에게 이메일 발송
        emailService.sendEmail(user.getEmail());
        log.info("구성원에게 이메일 발송 성공");

        return onBoardRepository.save(onBoardEntity);
    }

    @Override
    public UserRegistDto clickEmail(String email) {
        OnBoardEntity onBoardEntity = onBoardRepository.findByEmail(email);

        String jobName = jobService.getJobName(onBoardEntity.getJobId()).getJobName();
        String deptName = deptService.getDeptName(onBoardEntity.getDeptId()).getDeptName();

        // 사용자가 이메일 클릭시 onBoard테이블의 데이터를 UserRegistDto 형식으로 변환해 반환해준다.
        UserRegistDto userRegistDto = new UserRegistDto();
        userRegistDto.setEmail(email);
        userRegistDto.setName(onBoardEntity.getName());
        userRegistDto.setDeptId(onBoardEntity.getDeptId());
        userRegistDto.setJobId(onBoardEntity.getJobId());
        userRegistDto.setOfficeId(onBoardEntity.getOfficeId());
        userRegistDto.setJobName(jobName);
        userRegistDto.setDeptName(deptName);
        return userRegistDto;
    }

    @Override
    @Transactional
    public void deleteUserOnboard(String email) {
//        UserEntity userEntity = userService.getUserByEmail(email);
        System.out.println("여기1");
        OnBoardEntity onBoardEntity = onBoardRepository.findByEmail(email);
        System.out.println(onBoardEntity.getName());
        onBoardRepository.delete(onBoardEntity);


    }
}
