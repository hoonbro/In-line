package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.api.service.DeptService;
import com.inline.sub2.api.service.JobService;
import com.inline.sub2.api.service.OfficeService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.OfficeEntity;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Autowired
    UserService userService;

    @Autowired
    OfficeService officeService;

    @Autowired
    DeptService deptService;

    @Autowired
    JobService jobService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto){
        Map<String, Object> map = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        try {
            UserEntity loginUser = userService.getUserByEmail(userDto.getEmail());
            if(loginUser != null) {
                if(passwordEncoder.matches(userDto.getPassword(), loginUser.getPassword())) {
                    String token = jwtUtil.createToken(loginUser.getEmail(), loginUser.getEmail(), "access-token");
                    logger.debug("로그인 토큰 정보 : {}", token);
                    map.put("accessToken", token);
                    map.put("userDto",loginUser);
                    status = HttpStatus.CREATED;
                    System.out.println(map.get("userDto"));
                }
            }
        }catch (Exception e) {
            logger.error("로그인 실패 : {}", e);
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Map<String, Object>>(map, status);
    }










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
