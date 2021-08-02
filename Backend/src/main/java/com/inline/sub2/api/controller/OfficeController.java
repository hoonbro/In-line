package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.service.DeptService;
import com.inline.sub2.api.service.JobService;
import com.inline.sub2.api.service.OfficeService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/office")
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
    @ApiOperation(value = "회사 정보와 관리자 정보를 DB에 저장한다.")
    public ResponseEntity<Void> registAdmin(@RequestBody UserRegistDto admin) {
        HttpStatus httpStatus = HttpStatus.OK;

            //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
            UserEntity userEntity = userService.registAdmin(admin);
            if (userEntity != null) {
                httpStatus = HttpStatus.CREATED;
            }else{
                log.error("회원 등록 실패");
                httpStatus = HttpStatus.BAD_REQUEST;
            }

        return new ResponseEntity<Void>(httpStatus);
    }
}
