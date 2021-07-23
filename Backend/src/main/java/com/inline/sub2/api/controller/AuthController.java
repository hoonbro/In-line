package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserDto;
import com.inline.sub2.util.JwtUtil;
import com.inline.sub2.api.service.UserService;
import com.inline.sub2.db.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Autowired
    UserService userService;

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

    @GetMapping("/valiable")
    public ResponseEntity<Map<String, Object>> valiable(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        if (jwtUtil.validateToken(request.getHeader("Authorization"))) {
            logger.info("사용 가능한 토큰!!!");
        } else {
            status = HttpStatus.UNAUTHORIZED;
            logger.error("사용 불가능 토큰!!!");
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}