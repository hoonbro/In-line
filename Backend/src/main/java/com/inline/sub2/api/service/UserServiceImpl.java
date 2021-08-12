package com.inline.sub2.api.service;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.dto.UserUpdateDto;
import com.inline.sub2.db.entity.*;
import com.inline.sub2.db.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

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

    @Autowired
    roomService roomService;

    @Autowired
    OnBoardService onBoardService;

    @Override
    @Transactional
    public UserEntity registAdmin(UserRegistDto admin) {
        Date now = new Date();
        UserEntity userEntity = new UserEntity();
        try {
            OfficeEntity officeEntity = officeService.registOffice(admin.getOfficeName()); //회사 등록
            log.info("회사 등록 완료");

            RoomEntity roomEntity = roomService.createtRoom("로비", officeEntity.getOfficeId());
            roomService.createtRoom("기본회의실-1", officeEntity.getOfficeId()); //기본 회의실 생성
            roomService.createtRoom("기본회의실-2", officeEntity.getOfficeId());
            log.info("기본 회의실 생성 완료");

            DeptEntity deptEntity = deptService.getDeptId(admin.getDeptName(), 1l); //부서 번호 조회
            JobEntity jobEntity = jobService.getJobId(admin.getJobName(), 1l); //직책 번호 조회

            //유저 정보 기입\
            userEntity.setEmail(admin.getEmail());
            userEntity.setDeptId(deptEntity.getDeptId());
            userEntity.setJobId(jobEntity.getJobId());
            userEntity.setName(admin.getName());
            userEntity.setRoomId(roomEntity.getRoomId());
            userEntity.setPhone(admin.getPhone());
            userEntity.setPassword(passwordEncoder.encode(admin.getPassword()));
            userEntity.setAuth("ROLE_ADMIN");
            userEntity.setJoinDate(now);
            userEntity.setOfficeId(officeEntity.getOfficeId());
            try {
                userEntity = userRepository.save(userEntity);
            }catch(Exception e){
                log.error("이메일 중복 : {}", e);
            }
        } catch (Exception e) {
            log.error("회사명 중복 : {}", e);
        }
        return userEntity;
    }

    @Override
    @Transactional
    public UserEntity registUser(UserRegistDto user) {
        Date now = new Date();
        UserEntity userEntity = new UserEntity();

        //부서 번호 조회
        DeptEntity deptEntity = deptService.getDeptId(user.getDeptName(), 1l);
        //직책 번호 조회
        JobEntity jobEntity = jobService.getJobId(user.getJobName(), 1l);

        RoomEntity roomEntity = roomService.getLobby(user.getOfficeId());

        //유저정보 기입
        userEntity.setEmail(user.getEmail());
        userEntity.setOfficeId(user.getOfficeId());
        userEntity.setDeptId(deptEntity.getDeptId());
        userEntity.setJobId(jobEntity.getJobId());
        userEntity.setRoomId(roomEntity.getRoomId());
        userEntity.setName(user.getName());
        userEntity.setPhone(user.getPhone());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setAuth("ROLE_USER");
        userEntity.setJoinDate(now);
        try {
            onBoardService.deleteUserOnboard(user.getEmail());
            log.info("onboard 테이블내 유저 삭제 성공");
        }catch(Exception e){
            log.error("onboard 테이블내 유저 삭제 실패:{}",e);
        }

        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmailAndRetireDateIsNull(email);
    }

    @Override
    public UserEntity getUserId(Long userId) {
        return userRepository.findByUserIdAndRetireDateIsNull(userId);
    }

    @Override
    public List<UserEntity> getUserList(Long officeId){
        return userRepository.findAllByOfficeIdAndRetireDateIsNull(officeId);
    }


    @Override
    @Transactional
    public UserEntity updateUser(UserUpdateDto userUpdateDto) {
        UserEntity userEntity = getUserId(userUpdateDto.getUserId());

        DeptEntity deptEntity = deptService.getDeptId(userUpdateDto.getDeptName(),1l);
        JobEntity jobEntity = jobService.getJobId(userUpdateDto.getJobName(), 1l);

        userEntity.setDeptId(deptEntity.getDeptId());
        userEntity.setJobId(jobEntity.getJobId());

        userEntity.setUserId(userUpdateDto.getUserId());
        userEntity.setName(userUpdateDto.getName());
        userEntity.setNickName(userUpdateDto.getNickName());
        userEntity.setPhone(userUpdateDto.getPhone());
        userEntity.setJobEntity(jobEntity);
        userEntity.setDeptEntity(deptEntity);
        return userRepository.save(userEntity);
    }

    @Override
    public void updatePassword(UserEntity userEntity,String password) {
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity updateProfile(Long userId, String filePath) {
        UserEntity userEntity = getUserId(userId);
        userEntity.setProfileImage(filePath);
        return userRepository.save(userEntity);
    }

    @Override
    public Boolean duplicateEmail(String Email){
        UserEntity userEntity = userRepository.findByEmailAndRetireDateIsNull(Email);
        boolean isDuplicate = false;
        if(userEntity != null)
            isDuplicate = true;

        return isDuplicate;
    }
}