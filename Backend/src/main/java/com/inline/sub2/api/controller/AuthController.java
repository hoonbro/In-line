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
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    public static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);


    @Autowired
    JwtUtil jwtUtil;





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