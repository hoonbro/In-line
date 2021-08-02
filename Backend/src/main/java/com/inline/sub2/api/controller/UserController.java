package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.PasswordDto;
import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.dto.UserUpdateDto;
import com.inline.sub2.api.service.DeptService;
import com.inline.sub2.api.service.JobService;
import com.inline.sub2.api.service.OfficeService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
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

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping
    @ApiOperation(value = "회원가입 동작(onBoard에 있는 정보와 사용자가 입력한 password를 User 테이블에 저장한다.)")
    public ResponseEntity<Void> registUser(@RequestBody UserRegistDto user) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            UserEntity userEntity = userService.registUser(user);
            log.info("사용자 회원가입 성공");
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            log.error("사용자 회원가입 실패 : {}", e);
        }
        return new ResponseEntity<Void>(httpStatus);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인 성공 여부에 따라 토큰과 사용자의 데이터를 반환한다.", response = Map.class)
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDto userDto) {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.NOT_FOUND;

        try {
            UserEntity loginUser = userService.getUserByEmail(userDto.getEmail());
            if (loginUser != null) {
                if (passwordEncoder.matches(userDto.getPassword(), loginUser.getPassword())) {
                    String token = jwtUtil.createToken(loginUser.getEmail(), loginUser.getEmail());
                    log.debug("로그인 토큰 정보 : {}", token);
                    map.put("accessToken", token);
                    map.put("userDto", loginUser);
                    status = HttpStatus.OK;
                }else{
                    log.error("비밀번호가 일치하지 않습니다.");
                    status = HttpStatus.UNAUTHORIZED;
                }
            }else{
                log.error("일치하는 이메일이 없습니다.");
            }
        } catch (Exception e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(map, status);
        }
        return new ResponseEntity<Map<String, Object>>(map, status);
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "유저 아이티(userId)로 부터 회원 정보를 조회(반환)한다", response = UserEntity.class)
    public ResponseEntity<UserEntity> getUserInfo(@PathVariable("userId") Long userId) {
        HttpStatus httpStatus = HttpStatus.OK;
        UserEntity userEntity = null;
        try {
            userEntity = userService.getUserInfo(userId);
            log.info("user정보 조회 성공");
        }
        catch(Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("user정보 조회 실패 : {}" , e);
        }
        return new ResponseEntity<UserEntity>(userEntity,httpStatus);
    }

    @PutMapping("/{userId}")
    @ApiOperation(value = "유저 정보(비밀번호,프로필제외)를 수정한다", response = Map.class)
    public ResponseEntity<UserEntity> updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateDto userUpdateDto) {
        HttpStatus httpStatus = HttpStatus.OK;
        userUpdateDto.setUserId(userId);
        UserEntity userEntity = null;
        try {
            userEntity = userService.updateUser(userUpdateDto);
            httpStatus = HttpStatus.CREATED;
            log.info("user정보 변경 성공");
        }
        catch(Exception e) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            log.error("user정보 변경 실패 : {}", e);
        }
        return new ResponseEntity<UserEntity>(userEntity,httpStatus);
    }

    @PutMapping("/password")
    @ApiOperation(value = "유저 비밀번호를 변경한다", response = Map.class)
    public ResponseEntity<Void> updateUserPassword(@RequestHeader Map<String,String> requestHeader, @RequestBody PasswordDto passwordDto) {
        String currentPassword = passwordDto.getCurrentPassword();
        String newPassword = passwordDto.getNewPassword();
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            String token = requestHeader.get("accesstoken");
            String email = jwtUtil.getUserNameFromJwt(token); //복호화 하여 email 얻기
            UserEntity userEntity = userService.getUserByEmail(email);
            if(passwordEncoder.matches(currentPassword,userEntity.getPassword())) {
                userService.updatePassword(userEntity,newPassword);
                log.info("비밀번호 변경 성공");
            }
            else {
                httpStatus = HttpStatus.UNAUTHORIZED;
            }

        }
        catch(Exception e) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            log.error("비밀번호 변경 실패 : {}", e);
        }

        return new ResponseEntity<Void>(httpStatus);
    }



}
