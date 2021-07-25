package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.api.service.DeptService;
import com.inline.sub2.api.service.JobService;
import com.inline.sub2.api.service.OfficeService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.OfficeEntity;
import com.inline.sub2.db.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OfficeService officeService;

    @Autowired
    DeptService deptService;

    @Autowired
    JobService jobService;

    @PostMapping("/account")
    public ResponseEntity<Void> registUser(@RequestBody UserDto user){
        HttpStatus httpStatus = HttpStatus.OK;
        //회사 등록
        try {
            OfficeEntity officeEntity = officeService.registOffice(user.getOfficeName());

            //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
            UserEntity userEntity = userService.registUser(user);
            if(userEntity != null)
                httpStatus = HttpStatus.CREATED;
        }catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }


            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    }

}
