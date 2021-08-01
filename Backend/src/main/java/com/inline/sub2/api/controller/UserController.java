package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.PasswordDto;
import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.api.dto.UserUpdateDto;
import com.inline.sub2.api.service.DeptService;
import com.inline.sub2.api.service.JobService;
import com.inline.sub2.api.service.OfficeService;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.auth.UserAuthDetail;
import com.inline.sub2.db.entity.DeptEntity;
import com.inline.sub2.db.entity.OfficeEntity;
import com.inline.sub2.db.entity.UserEntity;
import com.inline.sub2.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.xml.ws.Response;
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
                    status = HttpStatus.OK;
                }
            }
        } catch (Exception e) {
            logger.error("로그인 실패 : {}", e);
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<Map<String, Object>>(map, status);
    }

    @GetMapping("/test")
    @ApiOperation(value = "관리자와 사용자 권한에 따라 입력이 되는지 확인 위해 만들었습니다(신경쓰지 않으셔도 돼요)")
    public ResponseEntity<Void> test() {
        System.out.println("권한 있는지여부 확인중");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @GetMapping("/user/{userId}")
    @ApiOperation(value = "유저 아이티(userId)로 부터 회원 정보를 조회(반환)한다", response = Map.class)

    public ResponseEntity<Map<String,Object>> getUserInfo(@PathVariable("userId") Long userId) {
        HttpStatus httpStatus = HttpStatus.OK;
        Map<String,Object> map = new HashMap<>();
        try {
            UserEntity userEntity = userService.getUserInfo(userId);
            map.put("userDto",userEntity);
        }
        catch(Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String,Object>>(map,httpStatus);

    }





    @PutMapping("/user")
    @ApiOperation(value = "유저 정보(비밀번호,프로필제외)를 변경한다", response = Map.class)
    public ResponseEntity<Map<String,Object>> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        Map<String,Object> map = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.OK;
        UserEntity userEntity = null;
        try {
            userEntity = userService.updateUser(userUpdateDto);
            httpStatus = HttpStatus.CREATED;
            map.put("userDto",userEntity);
        }
        catch(Exception e) {
            httpStatus = HttpStatus.UNAUTHORIZED;

        }
        return new ResponseEntity<Map<String,Object>>(map,httpStatus);
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
            }
            else {
                httpStatus = HttpStatus.UNAUTHORIZED;
            }

        }
        catch(Exception e) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<Void>(httpStatus);
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
