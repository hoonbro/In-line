package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.db.entity.OnBoardEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping
public class RoomController {
    @PostMapping("/user")
    @ApiOperation(value = "관리자가 구성원을 추가했을 때 onBoard 테이블에 추가하고 이메일을 보낸다.")
    public ResponseEntity<Void> createRoom(@RequestBody UserRegistDto user) {
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
