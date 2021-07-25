package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.AdminRegistDto;
import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.JobEntity;
import com.inline.sub2.db.entity.OfficeEntity;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DeptService deptService;

    @Autowired
    JobService jobService;

    @Autowired
    OfficeService officeService;

    @Override
    public UserEntity registAdmin(AdminRegistDto admin) {
        Date now = new Date();
        UserEntity userEntity = new UserEntity();
        try {
            OfficeEntity officeEntity = officeService.registOffice(admin.getOfficeName()); //회사 등록
            log.info("회사 등록 완료");
            DeptEntity deptEntity = deptService.getDeptId(admin.getDeptName()); //부서 번호 조회
            JobEntity jobEntity = jobService.getJobId(admin.getJobName()); //직책 번호 조회

            admin.setOfficeId(officeEntity.getOfficeId());
            admin.setDeptId(deptEntity.getDeptId());
            admin.setJobId(jobEntity.getJobId());

            //유저 정보 기입\
            userEntity.setEmail(admin.getEmail());
            userEntity.setDeptId(admin.getDeptId());
            userEntity.setJobId(admin.getJobId());
            userEntity.setName(admin.getName());
            userEntity.setPhone(admin.getPhone());
            userEntity.setPassword(passwordEncoder.encode(admin.getPassword()));
            userEntity.setAuth("ROLE_ADMIN");
            userEntity.setJoinDate(now);
            userEntity.setOfficeId(admin.getOfficeId());
        } catch (Exception e) {
            log.error("회사 등록 실패 : {}", e);
        }

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity registUser(UserDto user) {
        Date now = new Date();
        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail(user.getEmail());
//        userEntity.setDeptId(user.getDeptId());
//        userEntity.setJobId(user.getJobId());
//        userEntity.setName(user.getName());
//        userEntity.setPhone(user.getPhone());
//        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
//        userEntity.setJoinDate(now);
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}