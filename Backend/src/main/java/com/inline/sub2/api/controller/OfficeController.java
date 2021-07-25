package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.AdminRegistDto;
import com.inline.sub2.api.service.DeptService;
import com.inline.sub2.api.service.JobService;
import com.inline.sub2.api.service.OfficeService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.JobEntity;
import com.inline.sub2.db.entity.OfficeEntity;
import com.inline.sub2.db.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/office")
@CrossOrigin("*")
public class OfficeController {

    @Autowired
    UserService userService;

    @Autowired
    OfficeService officeService;

    @Autowired
    DeptService deptService;

    @Autowired
    JobService jobService;

    @PostMapping()
    public ResponseEntity<Void> registUser(@RequestBody AdminRegistDto admin) {
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            OfficeEntity officeEntity = officeService.registOffice(admin.getOfficeName()); //회사 등록
            log.info("회사 등록 완료");
            DeptEntity deptEntity = deptService.getDeptId(admin.getDeptName()); //부서 번호 조회
            JobEntity jobEntity = jobService.getJobId(admin.getJobName()); //직책 번호 조회

            admin.setOfficeId(officeEntity.getOfficeId());
            admin.setDeptId(deptEntity.getDeptId());
            admin.setJobId(jobEntity.getJobId());

            //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
            UserEntity userEntity = userService.registAdmin(admin);
            if (userEntity != null) {
                httpStatus = HttpStatus.CREATED;
            }else{
                log.error("회원 등록 실패");
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            log.error("회사 등록 실패 : {}", e);
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Void>(httpStatus);
    }
}
