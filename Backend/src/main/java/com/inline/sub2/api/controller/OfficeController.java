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

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping
    @ApiOperation(value = "회사 정보와 관리자 정보를 DB에 저장한다.")
    public ResponseEntity<String> registAdmin(@RequestBody UserRegistDto admin) {
        Map<String, Object> map = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;

        log.info("gdgd");
        String result = userService.registAdmin(admin);
        log.info("gd");
        if (result == "등록 성공") {
            httpStatus = HttpStatus.CREATED;
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }

        return new ResponseEntity<String>(result, httpStatus);
    }
}
