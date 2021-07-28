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
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/users")
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
    @ApiOperation(value = "로그인 성공 여부에 따라 토큰과 사용자의 데이터를 반환한다.", response = Map.class)
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        try {
            UserEntity loginUser = userService.getUserByEmail(userDto.getEmail());
            if (loginUser != null) {
                if (passwordEncoder.matches(userDto.getPassword(), loginUser.getPassword())) {
                    String token = jwtUtil.createToken(loginUser.getEmail(), loginUser.getEmail());
                    logger.debug("로그인 토큰 정보 : {}", token);
                    map.put("accessToken", token);
                    map.put("userDto", loginUser);
                    status = HttpStatus.CREATED;
                }
            }
        } catch (Exception e) {
            logger.error("로그인 실패 : {}", e);
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Map<String, Object>>(map, status);
    }

    @GetMapping("/test")
    @ApiOperation(value = "관리자와 사용자 권한에 따라 입력이 되는지 확인 위해 만들었습니다(신경쓰지 않으셔도 돼요)", response = Map.class)
    public ResponseEntity<Void> test() {
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @PostMapping("/account")
//    public ResponseEntity<Void> registUser(@RequestBody UserDto user) {
//        HttpStatus httpStatus = HttpStatus.OK;
//        //회사 등록
//        try {
//            OfficeEntity officeEntity = officeService.registOffice(user.getOfficeName());
//
//            //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
//            UserEntity userEntity = userService.registUser(user);
//            if (userEntity != null)
//                httpStatus = HttpStatus.CREATED;
//        } catch (Exception e) {
//            httpStatus = HttpStatus.BAD_REQUEST;
//        }
//        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
//    }

}
