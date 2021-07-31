package com.inline.sub2.api.controller;

import com.inline.sub2.api.dto.UserRegistDto;
import com.inline.sub2.api.service.OnBoardService;
import com.inline.sub2.db.entity.OnBoardEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/on-board")
public class OnBoardController {

    @Autowired
    OnBoardService onBoardService;

    @PostMapping("/user")
    @ApiOperation(value = "관리자가 구성원을 추가했을 때 onBoard 테이블에 추가한다.")
    public ResponseEntity<Void> registUserOnboard(@RequestBody UserRegistDto user) {
        OnBoardEntity onBoardEntity = onBoardService.registUserOnboard(user);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{email}")
    @ApiOperation(value = "관리자가 onBoard 테이블에서 구성원을 삭제한다.")
    public ResponseEntity<Void> deleteUserOnboard(@PathVariable("email") String email) {
        System.out.println(email);
       HttpStatus httpStatus = HttpStatus.OK;
        try{
            onBoardService.deleteUserOnboard(email);
            System.out.println("여기1");
        }
        catch(Exception e){
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Void>(httpStatus);
    }


}
